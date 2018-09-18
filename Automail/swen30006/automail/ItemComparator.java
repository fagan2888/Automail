package automail;

import java.util.Comparator;

/**
 * Compare two item according to their priority and destination
 */
public class ItemComparator implements Comparator<Item> {
	
	@Override
	public int compare(Item i1, Item i2) {
		int order = 0;
		if (i1.getPriority() < i2.getPriority()) {
			order = 1;
		} else if (i1.getPriority() > i2.getPriority()) {
			order = -1;
		} else if (i1.getDestination() < i2.getDestination()) {
			order = 1;
		} else if (i1.getDestination() > i2.getDestination()) {
			order = -1;
		}
		return order;
	}
}