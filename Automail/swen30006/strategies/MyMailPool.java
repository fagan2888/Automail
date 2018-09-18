package strategies;

import java.util.LinkedList;
import java.util.ListIterator;
import automail.BigRobot;
import automail.CarefulRobot;
import automail.Item;
import automail.ItemComparator;
import automail.MailItem;
import automail.Robot;
import automail.StandardRobot;
import automail.StorageTube;
import automail.WeakRobot;
import exceptions.TubeFullException;
import exceptions.FragileItemBrokenException;

public class MyMailPool implements IMailPool {
	
	private LinkedList<Item> pool;
	private LinkedList<Robot> robots;
	// The number of light items whose weights are less than 2000
	private int lightCount;

	public MyMailPool(){
		pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
		lightCount = 0;
	}

	/**
	 * Add a new mail into mailpool
	 */
	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		pool.add(item);
		if (!item.isHeavy()) lightCount++;
		pool.sort(new ItemComparator());
	}
	
	/**
	 * Load up robots with mail items
	 */
	@Override
	public void step() throws FragileItemBrokenException {
		for (Robot robot: (Iterable<Robot>) robots::iterator) { fillStorageTube(robot); }
	}
	
	/**
	 * Register a robot
	 */
	@Override
	public void registerWaiting(Robot robot) { // assumes won't be there
		if (robot.isStrong()) {
			robots.add(robot); 
		} else {
			robots.addLast(robot); // weak robot last as want more efficient delivery with highest priorities
		}
	}

	/**
	 * Deregister a robot
	 */
	@Override
	public void deregisterWaiting(Robot robot) {
		robots.remove(robot);
	}

	/**
	 * Select mail items and load up robot
	 * @param robot
	 * @throws FragileItemBrokenException
	 */
	private void fillStorageTube(Robot robot) throws FragileItemBrokenException {
		StorageTube tube = robot.getTube();
		
		try {
			// Standard Robot or Big Robot
			if(robot instanceof StandardRobot | robot instanceof BigRobot) {
				
				int maxTake = StandardRobot.MAX_TAKE;
				boolean canCarryFragile = StandardRobot.CAN_CARRY_FRAGILE;
				StorageTube temp = new StorageTube(maxTake, canCarryFragile);
				
				ListIterator<Item> i = pool.listIterator();
				while(temp.getSize() < maxTake && !pool.isEmpty() && i.hasNext()) {
					Item item = i.next();
					if(!item.isFragile()) {
						if (!item.isHeavy()) lightCount--;
						temp.addItem(item.getMailItem());
						i.remove();
					}
					
				}
				if (temp.getSize() > 0) {
					while (!temp.isEmpty()) tube.addItem(temp.pop());
				}
			}
			
			// Weak Robot
			else if(robot instanceof WeakRobot) {
				int maxTake = WeakRobot.MAX_TAKE;
				boolean canCarryFragile = WeakRobot.CAN_CARRY_FRAGILE;
				StorageTube temp = new StorageTube(maxTake, canCarryFragile);
				
				ListIterator<Item> i = pool.listIterator();
				while(temp.getSize() < maxTake &&lightCount > 0 && i.hasNext()) {
					Item item = i.next();
					if(!item.isFragile()) {
						if (!item.isHeavy()) {
							temp.addItem(item.getMailItem());
							i.remove();
							lightCount--;
						}
					}
					
				}
				
				if (temp.getSize() > 0) {
					while (!temp.isEmpty()) tube.addItem(temp.pop());
				}
			}
			
			// Careful Robot
			else if(robot instanceof CarefulRobot) {
				int maxTake = CarefulRobot.MAX_TAKE;
				boolean canCarryFragile = CarefulRobot.CAN_CARRY_FRAGILE;
				StorageTube temp = new StorageTube(maxTake, canCarryFragile);
				int fCount = 0;
				
				ListIterator<Item> i = pool.listIterator();
				while(temp.getSize() < maxTake && !pool.isEmpty() && i.hasNext()) {
					Item item = i.next();
					
					// Fragile Item
					if(item.isFragile()) {
						if(fCount < 1) {
							if (!item.isHeavy()) lightCount--;
							temp.addItem(item.getMailItem());
							i.remove();
							fCount++;
							break;
						}else {
							// Can not take more than 2 fragile mail items
							throw new FragileItemBrokenException();
						}
					}
					// Not Fragile Item
					else {
						if (!item.isHeavy()) lightCount--;
						temp.addItem(item.getMailItem());
						i.remove();
					}
				}
				
				if (temp.getSize() > 0) {
					while (!temp.isEmpty()) tube.addItem(temp.pop());
				}
			}
			
			robot.dispatch();
		}catch(TubeFullException e){
			e.printStackTrace();
		}
	}

}
