package strategies;

import java.util.List;
import automail.BigRobot;
import automail.CarefulRobot;
import automail.IMailDelivery;
import automail.Robot;
import automail.Simulation.RobotType;
import automail.StandardRobot;
import automail.WeakRobot;
import exceptions.NoRobotTypeException;

/**
 * Automail creates robots and mailpool
 */
public class Automail {
	      
    public Robot[] robot;
    public IMailPool mailPool;
    
    public Automail(IMailPool mailPool, IMailDelivery delivery, List<RobotType> robotTypes) throws NoRobotTypeException {
    	this.mailPool = mailPool;
    	robot = new Robot[robotTypes.size()];
    	
    	for(int i=0; i<robotTypes.size(); i++) {
    		if(robotTypes.get(i) == RobotType.Standard) {
    			robot[i] = new StandardRobot(delivery, mailPool);
    		}else if(robotTypes.get(i) == RobotType.Weak) {
    			robot[i] = new WeakRobot(delivery, mailPool);
    		}else if(robotTypes.get(i) == RobotType.Big) {
    			robot[i] = new BigRobot(delivery, mailPool);
    		}else if(robotTypes.get(i) == RobotType.Careful) {
    			robot[i] = new CarefulRobot(delivery, mailPool);
    		}else {
    			// if there is no such robot type
    			throw new NoRobotTypeException();
    		}
    	}
    }
    
}
