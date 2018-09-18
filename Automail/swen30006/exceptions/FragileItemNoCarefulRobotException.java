package exceptions;

/**
 * This exception will be thrown when there is no careful robot in automail 
 * system but mailPool received fragile mail(s). 
 */
public class FragileItemNoCarefulRobotException extends Exception {
	public FragileItemNoCarefulRobotException() {
		super("There is fragile item in mail pool while no careful robot to carry!");
	}
}
