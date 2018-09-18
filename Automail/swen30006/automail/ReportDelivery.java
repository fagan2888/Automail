package automail;

import java.util.ArrayList;
import exceptions.MailAlreadyDeliveredException;

/**
 * This class is used to help report delivery process
 */
public class ReportDelivery implements IMailDelivery {

	private ArrayList<MailItem> mailDelivered;
	private double totalScore;

	/**
	 * Constructor
	 */
	public ReportDelivery() {
		totalScore = 0;
		mailDelivered = new ArrayList<MailItem>();
	}
	
	/**
	 * @return mail items that has been delivered
	 */
	public ArrayList<MailItem> getMailDelivered() {
		return mailDelivered;
	}

	/**
	 * Confirm the delivery and calculate the total score
	 */
	public void deliver(MailItem deliveryItem) {
		if (!mailDelivered.contains(deliveryItem)) {
			System.out.printf("T: %3d > Delivered [%s]%n", Clock.getInstance().getTime(), deliveryItem.toString());
			mailDelivered.add(deliveryItem);
			// Calculate delivery score
			totalScore += calculateDeliveryScore(deliveryItem);
		} else {
			try {
				throw new MailAlreadyDeliveredException();
			} catch (MailAlreadyDeliveredException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Used to calculate the score of current item
	 * @param deliveryItem
	 * @return a score for current item
	 */
	private double calculateDeliveryScore(MailItem deliveryItem) {
		// Penalty for longer delivery times
		final double penalty = 1.2;
		double priority_weight = 0;
		// Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
		if (deliveryItem instanceof PriorityMailItem) {
			priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
		}
		return Math.pow(Clock.getInstance().getTime() - deliveryItem.getArrivalTime(), penalty)
				* (1 + Math.sqrt(priority_weight));
	}

	/**
     * Print final results
     */
	public void printResults() {
		System.out.println("T: " + Clock.getInstance().getTime() + " | Simulation complete!");
		System.out.println("Final Delivery time: " + Clock.getInstance().getTime());
		System.out.printf("Final Score: %.2f%n", totalScore);
	}
}