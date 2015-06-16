import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class APIClass {
	
	public static JSONObject accessAPI(){
		//Create and return a JSONObject "data" for further use
		JSONObject data = null;
		try {
			URI uri = new URI("http://api.worldoftanks.eu/wot/encyclopedia/tanks/?application_id=e1085ece3a418de490d31a8ca5088066");
			JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
			JSONObject root = new JSONObject(tokener);
			data = root.getJSONObject("data");
		} catch (JSONException | URISyntaxException | IOException e) {e.printStackTrace();}
		return data;
	}

	public static ArrayList<String> generateArrayList(JSONObject data, String type) throws JSONException{
		//Create and return an arraylist which contains links to all contour images of tanks of the selected "type"
		String[] array = JSONObject.getNames(data);
		ArrayList<String> arraylist = new ArrayList<String>();
		for (int i = 0; i < array.length; i++){
			JSONObject jObj = data.getJSONObject(array[i]);
			if (jObj.getString("type").equals(type)){
				arraylist.add(jObj.getString("contour_image"));
			}
		}
		return arraylist;
	}
}