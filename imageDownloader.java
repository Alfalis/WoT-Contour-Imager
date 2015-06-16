import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.json.JSONException;

public class imageDownloader implements Runnable {

	@Override
	public void run(){
		int size = 0;
		String url = null;
		String text;
		//do the following for all selected tank types:
		for (int tt = 0; tt < UIClass.tanktypes4download.length; tt++){
			String type = UIClass.tanktypes4download[tt];
			try {
				size = APIClass.generateArrayList(APIClass.accessAPI(), type).size();
			} catch (JSONException e){
				e.printStackTrace();
			}
			for (int i = 0; i < size; i++){
				try {
					url = APIClass.generateArrayList(APIClass.accessAPI(), type).get(i);
					ImageClass.saveBufferedImage(ImageClass.tintImage(url, type), url, UtilitiesClass.getSetting("filepath"));
				} catch (ConfigurationException | IOException | JSONException e){
					e.printStackTrace();
				}
				UIClass.imagesDownloaded++;
				text = "(" + String.format("%03d", UIClass.imagesDownloaded) + "/" + UIClass.imagesToDownload + " images downloaded - " + size + " " + UIClass.tanktypes4combobox[tt] + ")";
				UIClass.imagesDownloaded_label.setText(text);
				UIClass.progressbar.setValue(UIClass.imagesDownloaded);
			}	
		}
		UIClass.changepath_button.setEnabled(true);
		UIClass.slider_red.setEnabled(true);
		UIClass.slider_green.setEnabled(true);
		UIClass.slider_blue.setEnabled(true);
		UIClass.downloadimages_button.setEnabled(true);
	}

}
