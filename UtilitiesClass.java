import java.io.File;
import java.io.IOException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.json.JSONException;
import org.json.JSONObject;

public class UtilitiesClass {
	
	public static String getSetting(String setting) throws ConfigurationException{
		return (new XMLConfiguration("ContourImager_Settings.xml").getString(setting));
	}
	
	public static int getTotalNumberOfTanks(JSONObject data){
		String[] array = JSONObject.getNames(data);
		return(array.length);
	}
	
	public static String generateFilenameFromURL(String url){
		//Give this class a URL from generateArrayList() and get the filename in return
		String [] filename_array = url.split("/");
		String filename = filename_array[filename_array.length - 1];
		return filename;
	}
	
	public static String TankTypeReturner(){
		//This returns the currently selected tank type in an appropriate format
		String tanktype = null;
		switch (UIClass.tanktypes4combobox[UIClass.tanktypes.getSelectedIndex()]){
		case ("Heavy Tanks"):
				tanktype = "heavyTank";
			break;
		case ("Medium Tanks"):
				tanktype = "mediumTank";
			break;
		case ("Light Tanks"):
				tanktype = "lightTank";
			break;
		case ("Tankdestroyers"):
				tanktype = "AT-SPG";
			break;
		case ("SPGs"):
				tanktype = "SPG";
			break;
		}
		return (tanktype);
	}
	
	public static void saveSettings() throws ConfigurationException{
		XMLConfiguration settings = new XMLConfiguration("ContourImager_Settings.xml");
		settings.setProperty("filepath", UIClass.pathfield.getText());
		settings.setProperty("color_multipliers." + TankTypeReturner() + ".red", UIClass.slider_red_value.getText().substring(4,7));
		settings.setProperty("color_multipliers." + TankTypeReturner() + ".green", UIClass.slider_green_value.getText().substring(4,7));
		settings.setProperty("color_multipliers." + TankTypeReturner() + ".blue", UIClass.slider_blue_value.getText().substring(4,7));
		settings.setFileName("ContourImager_Settings.xml");
		settings.save();
		UIClass.mainframe.requestFocus();
	}
	
	public static void restoreSettings() throws ConfigurationException, JSONException, IOException{
		XMLConfiguration settings = new XMLConfiguration("ContourImager_Settings.xml");
		UIClass.slider_red.setValue((int)(settings.getDouble("color_multipliers." + TankTypeReturner() + ".red") * 10));
		UIClass.slider_green.setValue((int)(settings.getDouble("color_multipliers." + TankTypeReturner() + ".green") * 10));
		UIClass.slider_blue.setValue((int)(settings.getDouble("color_multipliers." + TankTypeReturner() + ".blue") * 10));
		UIClass.UIImager(TankTypeReturner());
		UIClass.mainframe.requestFocus();
	}
	
	public static void settingsFileChecker() throws ConfigurationException{
		try{
			@SuppressWarnings("unused")
			XMLConfiguration settings = new XMLConfiguration("ContourImager_Settings.xml");			
		} catch (ConfigurationException e){
			XMLConfiguration settings = new XMLConfiguration();
			settings.setRootElementName("settings");
			settings.setProperty("filepath", "C:/Games/World_of_Tanks/res_mods/0.9.9/gui/maps/icons/vehicle/contour");
			settings.setProperty("color_multipliers.heavyTank.red", 1.3);
			settings.setProperty("color_multipliers.heavyTank.green", 0.8);
			settings.setProperty("color_multipliers.heavyTank.blue", 0.5);
			settings.setProperty("color_multipliers.mediumTank.red", 0.6);
			settings.setProperty("color_multipliers.mediumTank.green", 1.3);
			settings.setProperty("color_multipliers.mediumTank.blue", 0.6);
			settings.setProperty("color_multipliers.lightTank.red", 1.3);
			settings.setProperty("color_multipliers.lightTank.green", 1.3);
			settings.setProperty("color_multipliers.lightTank.blue", 0.6);
			settings.setProperty("color_multipliers.AT-SPG.red", 0.7);
			settings.setProperty("color_multipliers.AT-SPG.green", 0.7);
			settings.setProperty("color_multipliers.AT-SPG.blue", 1.7);
			settings.setProperty("color_multipliers.SPG.red", 1.6);
			settings.setProperty("color_multipliers.SPG.green", 0.6);
			settings.setProperty("color_multipliers.SPG.blue", 0.6);
			settings.setFileName("ContourImager_Settings.xml");
			settings.save();
		}
	}
	
	public static void generateFolder(){
		File path = new File(UIClass.pathfield.getText());
		if (!path.exists()){
			path.mkdirs();
		}
	}

}