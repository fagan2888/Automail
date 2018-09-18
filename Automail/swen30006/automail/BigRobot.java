package automail;

import strategies.IMailPool;

public class BigRobot extends Robot {
	
	public static final int MAX_TAKE = 6;
	public static final boolean STRONG = true;
	public static final boolean CAN_CARRY_FRAGILE = false;
	
	/**
	 * Initialize a standard robot
	 * @param delivery governs the final delivery
	 * @param mailPool is the source of mail items
	 */
	public BigRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, STRONG, MAX_TAKE, CAN_CARRY_FRAGILE);
	}
	
}
