import java.io.IOException;
import org.apache.commons.configuration.ConfigurationException;
import org.json.JSONException;


public class WoTCI_main {
	
	public static String filepath;
	public static String [] tanktypes;
	
	public static void main (String args[]) throws ConfigurationException, JSONException, IOException{
		String version = "1.3";
		UtilitiesClass.settingsFileChecker();
		UIClass.UIBuilder(version);
	}

}