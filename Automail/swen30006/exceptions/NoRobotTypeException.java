package exceptions;

/**
 * This exception is thrown when a non-exist robot type are given
 */
public class NoRobotTypeException extends Exception {
    public NoRobotTypeException(){
        super("The robot type does not exist!");
    }
}
