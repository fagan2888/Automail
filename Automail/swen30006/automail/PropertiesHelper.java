package automail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Used to help simulation class read properties from file
 */
public class PropertiesHelper {
	
	private Properties automailProperties;
	
	/**
	 * Constructor. Read properties from the file. Build a Properties object to store
	 * those properties in such file.
	 * @param fileName
	 */
	public PropertiesHelper(String fileName){
		automailProperties = new Properties();
		automailProperties.setProperty("Robots", "Standard");
		automailProperties.setProperty("MailPool", "strategies.SimpleMailPool");
		automailProperties.setProperty("Floors", "10");
		automailProperties.setProperty("Fragile", "false");
		automailProperties.setProperty("Mail_to_Create", "80");
		automailProperties.setProperty("Last_Delivery_Time", "100");
		
		FileReader inStream = null;
		try {
			inStream = new FileReader(fileName);
			automailProperties.load(inStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param propertyName
	 * @return the value of the property
	 */
	public String getProperty(String propertyName) {
		return automailProperties.getProperty(propertyName);
	}

}
