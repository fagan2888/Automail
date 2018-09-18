package exceptions;

/**
 * This exception is thrown when a careful robot takes more than one fragile item.
 */
public class TooManyFragileItemException extends Exception {
	public TooManyFragileItemException(){
        super("The robot are taking tomany  fragile items!");
    }
}
