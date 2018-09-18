package automail;

/**
 * A global clock
 */
public class Clock {

	private int time = 0;
	private int lastDeliveryTime;

	private static volatile Clock clock = null;

	private Clock() {}

	public static Clock getInstance() {
		if (clock == null) {
			synchronized (Clock.class) {
				if (clock == null) {
					clock = new Clock();
				}
			}
		}
		return clock;
	}

	public void doTick() {
		time++;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLastDeliveryTime() {
		return lastDeliveryTime;
	}

	public void setLastDeliveryTime(int lastDeliveryTime) {
		this.lastDeliveryTime = lastDeliveryTime;
	}

}
