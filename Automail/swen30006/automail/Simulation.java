package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.NoRobotTypeException;
import exceptions.FragileItemBrokenException;
import exceptions.FragileItemNoCarefulRobotException;
import strategies.Automail;
import strategies.IMailPool;
import java.util.stream.Stream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class simulates the behavior of AutoMail system
 */
public class Simulation {

	public enum RobotType {
		Big, Careful, Standard, Weak
	};

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		// reportDelivery
		ReportDelivery reportDelivery = new ReportDelivery();

		// propertyHelper
		PropertiesHelper propertyHelper = new PropertiesHelper("automail.properties");

		// MailPool
		String mailPoolName = propertyHelper.getProperty("MailPool");
		IMailPool mailPool = (IMailPool) Class.forName(mailPoolName).newInstance();

		// Floors
		Building.FLOORS = Integer.parseInt(propertyHelper.getProperty("Floors"));
		System.out.printf("Floors: %5d%n", Building.FLOORS);

		// Fragile
		boolean fragile = Boolean.parseBoolean(propertyHelper.getProperty("Fragile"));
		System.out.printf("Fragile: %5b%n", fragile);

		// Mail_to_Create
		int mailToCreate = Integer.parseInt(propertyHelper.getProperty("Mail_to_Create"));
		System.out.printf("Mail_to_Create: %5d%n", mailToCreate);

		// Last_Delivery_Time
		Clock.getInstance().setLastDeliveryTime(Integer.parseInt(propertyHelper.getProperty("Last_Delivery_Time")));
		System.out.printf("Last_Delivery_Time: %5d%n", Clock.getInstance().getLastDeliveryTime());

		// Robots
		String robotsProp = propertyHelper.getProperty("Robots");
		List<RobotType> robotTypes = Stream.of(robotsProp.split(",")).map(RobotType::valueOf)
				.collect(Collectors.toList());
		System.out.print("Robots: ");
		System.out.println(robotTypes);

		// Seed
		String seedProp = propertyHelper.getProperty("Seed");
		Integer seed = null;
		if (args.length != 0) {
			seed = Integer.parseInt(args[0]);
		} else if (seedProp != null) {
			seed = Integer.parseInt(seedProp);
		}
		System.out.printf("Seed: %s%n", seed == null ? "null" : seed.toString());

		// If there is no careful robot while mail pool has fragile item
		int carefulRobotCount = 0;
		for (RobotType rt : robotTypes) {
			if (rt == RobotType.Careful)
				carefulRobotCount++;
		}
		if ((carefulRobotCount == 0) && (fragile == true)) {
			try {
				throw new FragileItemNoCarefulRobotException();
			} catch (FragileItemNoCarefulRobotException e1) {
				e1.printStackTrace();
			} finally {
				System.exit(0);
			}
		}

		// Automail
		Automail automail = null;
		try {
			automail = new Automail(mailPool, reportDelivery, robotTypes);
		} catch (NoRobotTypeException e1) {
			e1.printStackTrace();
		}

		// MailGenerator
		MailGenerator mailGenerator = new MailGenerator(mailToCreate, automail.mailPool, seed, fragile);
		mailGenerator.generateAllMail();

		// Delivery simulation
		while (reportDelivery.getMailDelivered().size() != mailGenerator.getMailToCreate()) {
			mailGenerator.step();
			try {
				automail.mailPool.step();
				for (int i = 0; i < robotTypes.size(); i++)
					automail.robot[i].step();
			} catch (ExcessiveDeliveryException | ItemTooHeavyException | FragileItemBrokenException e) {
				e.printStackTrace();
				System.out.println("Simulation unable to complete.");
				System.exit(0);
			}
			Clock.getInstance().doTick();
		}
		reportDelivery.printResults();
	}

}
