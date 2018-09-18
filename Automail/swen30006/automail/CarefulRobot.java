package automail;

import strategies.IMailPool;

public class CarefulRobot extends Robot {
	
	public static final int MAX_TAKE = 3;
	public static final boolean STRONG = true;
	public static final boolean CAN_CARRY_FRAGILE = true;
	
	// To simulate careful robot mover slower than other robots, record a fake current floor
	private double fakeCurrentFloor;
	// Represent the speed careful robot moving. Move half floor each time.
	private final double SPEED = 0.5;
	
	
	/**
	 * Initialize a standard robot
	 * @param delivery governs the final delivery
	 * @param mailPool is the source of mail items
	 */
	public CarefulRobot(IMailDelivery delivery, IMailPool mailPool) {
		super(delivery, mailPool, STRONG, MAX_TAKE, CAN_CARRY_FRAGILE);
		fakeCurrentFloor = (double)currentFloor;
	}
	
    /**
     * Generic function that moves the robot towards the destination
     * @param destination the floor towards which the robot is moving
     */
	@Override
    protected void moveTowards(int destination) {
        if(currentFloor < destination){
        	fakeCurrentFloor += SPEED;
            currentFloor = (int)fakeCurrentFloor;
        }
        else{
        	fakeCurrentFloor -= SPEED;
            currentFloor = (int)fakeCurrentFloor;
        }
    }
	
}
