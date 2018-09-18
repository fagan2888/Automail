package automail;

/**
 * Encapsulate a mail item to an Item
 */
public class Item {
	private int priority;
	private int destination;
	private boolean heavy;
	private MailItem mailItem;
	
	public int getPriority() {
		return priority;
	}

	public int getDestination() {
		return destination;
	}
	
	public boolean isHeavy() {
		return heavy;
	}

	public MailItem getMailItem() {
		return mailItem;
	}
	
	public boolean isFragile() {
		return mailItem.getFragile();
	}

	public Item(MailItem mailItem) {
		priority = (mailItem instanceof PriorityMailItem) ? ((PriorityMailItem) mailItem).getPriorityLevel() : 1;
		heavy = mailItem.getWeight() >= 2000;
		destination = mailItem.getDestFloor();
		this.mailItem = mailItem;
	}
}