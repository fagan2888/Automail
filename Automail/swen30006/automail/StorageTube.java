package automail;

import exceptions.FragileItemBrokenException;
import exceptions.TubeFullException;
import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube {
    
	private Stack<MailItem> tube;
	// The max number of mail items the storage tube can load
    private int maximumCapacity;
    // if a tube can load a fragile item
    private boolean canLoadFragile;
    
    /**
     * Constructor for the storage tube
     */
    public StorageTube(int maximum_capacity, boolean canLoadFragile){
        this.tube = new Stack<MailItem>();
        this.maximumCapacity = maximum_capacity;
        this.canLoadFragile = canLoadFragile;
    }

    /**
     * @return if the storage tube is full
     */
    public boolean isFull(){
        return tube.size() == maximumCapacity;
    }

    /**
     * @return if the storage tube is empty
     */
    public boolean isEmpty(){
        return tube.isEmpty();
    }
    
    /**
     * @return the first item in the storage tube (without removing it)
     */
    public MailItem peek() {
    	return tube.peek();
    }

    /** @return the size of the tube **/
    public int getSize(){
    	return tube.size();
    }
    
    /** 
     * @return the first item in the storage tube (after removing it)
     */
    public MailItem pop(){
        return tube.pop();
    }
    
    /**
     * @return the maximum capacity of the tube
     */
    public int getMaximumCapacity() {
		return maximumCapacity;
	}

    /**
     * @return if the tube can load fragile mail item
     */
	public boolean canLoadFragile() {
		return canLoadFragile;
	}


	/**
     * Add an item to the tube
     * @param item The item being added
     * @throws TubeFullException thrown if an item is added which exceeds the capacity
     */
    public void addItem(MailItem item) throws TubeFullException, FragileItemBrokenException {
        if(tube.size() < maximumCapacity){
        	// Fragile item
        	if(item.fragile) {
        		// Tube can load fragile item
        		if(canLoadFragile) {
        			tube.add(item);
        		// Tube can not load fragile item
        		}else {
        			throw new FragileItemBrokenException();
        		}
        	// not fragile item
        	}else {
        		tube.add(item);
        	}
        } else {
            throw new TubeFullException();
        }
    }

}
