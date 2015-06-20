import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class ImageClass {
	
	public static BufferedImage originalImage(String url, String type) throws IOException{
		//Feed me a url and a tynk type and I'll return the original image for you
		URL url_internal = new URL(url);
		InputStream in = new BufferedInputStream(url_internal.openStream());
		BufferedImage bi = ImageIO.read(in);
		
		return (bi);
	}
	
	public static BufferedImage tintImage(String url, String type) throws IOException, ConfigurationException{
		//Feed me a url and a tank type and I'll tint that bitch - result is the tinted BufferedImage
		URL url_internal = new URL(url);
		double red_multiplier = 0;
		double green_multiplier = 0;
		double blue_multiplier = 0;
		InputStream in = new BufferedInputStream(url_internal.openStream());
		BufferedImage bi = ImageIO.read(in);
		XMLConfiguration settings = new XMLConfiguration("ContourImager_Settings.xml");
        
        switch (type){
        	case "heavyTank":
					red_multiplier = settings.getDouble("color_multipliers.heavyTank.red");
					green_multiplier = settings.getDouble("color_multipliers.heavyTank.green");
					blue_multiplier = settings.getDouble("color_multipliers.heavyTank.blue");
        		break;
        	case "mediumTank":
				red_multiplier = settings.getDouble("color_multipliers.mediumTank.red");
				green_multiplier = settings.getDouble("color_multipliers.mediumTank.green");
				blue_multiplier = settings.getDouble("color_multipliers.mediumTank.blue");
        		break;
        	case "lightTank":
				red_multiplier = settings.getDouble("color_multipliers.lightTank.red");
				green_multiplier = settings.getDouble("color_multipliers.lightTank.green");
				blue_multiplier = settings.getDouble("color_multipliers.lightTank.blue");
        		break;
        	case "AT-SPG":
				red_multiplier = settings.getDouble("color_multipliers.AT-SPG.red");
				green_multiplier = settings.getDouble("color_multipliers.AT-SPG.green");
				blue_multiplier = settings.getDouble("color_multipliers.AT-SPG.blue");
        		break;
        	case "SPG":
				red_multiplier = settings.getDouble("color_multipliers.SPG.red");
				green_multiplier = settings.getDouble("color_multipliers.SPG.green");
				blue_multiplier = settings.getDouble("color_multipliers.SPG.blue");
        		break;
        }

        //do something with the image
        int width = bi.getWidth();
        int height = bi.getHeight();
        int red, green, blue, alpha;
        for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				Color c = new Color(bi.getRGB(j, i), true);
                alpha = c.getAlpha();
            	red = (int)(c.getRed() * red_multiplier);
				green = (int)(c.getGreen() * green_multiplier);
				blue = (int)(c.getBlue() * blue_multiplier);
				if (red > 255){
					red = 255;
				}
				if (green > 255){
					green = 255;
				}
				if (blue > 255){
					blue = 255;
				}
				if (red < 0){
					red = 0;
				}
				if (green < 0){
					green = 0;
				}
				if (blue < 0){
					blue = 0;
				}
				Color color = new Color(red, green, blue, alpha);
				bi.setRGB(j,i,color.getRGB());
			}
        }
        //finished doing something with the image
        
	    return (bi);
	}
	
	public static BufferedImage tintImage(BufferedImage bi, String type) throws IOException, ConfigurationException{
		//Feed me a BufferedImage and a tank type and I'll tint that bitch - result is the tinted BufferedImage
		double red_multiplier = 0;
		double green_multiplier = 0;
		double blue_multiplier = 0;
        
        switch (type){
        	case "heavyTank":
        			red_multiplier = UIClass.slider_red.getValue() * 0.1;
        			green_multiplier = UIClass.slider_green.getValue() * 0.1;
        			blue_multiplier = UIClass.slider_blue.getValue() * 0.1;
        		break;
        	case "mediumTank":
	        		red_multiplier = UIClass.slider_red.getValue() * 0.1;
	    			green_multiplier = UIClass.slider_green.getValue() * 0.1;
	    			blue_multiplier = UIClass.slider_blue.getValue() * 0.1;
        		break;
        	case "lightTank":
	        		red_multiplier = UIClass.slider_red.getValue() * 0.1;
	    			green_multiplier = UIClass.slider_green.getValue() * 0.1;
	    			blue_multiplier = UIClass.slider_blue.getValue() * 0.1;
        		break;
        	case "AT-SPG":
	        		red_multiplier = UIClass.slider_red.getValue() * 0.1;
	    			green_multiplier = UIClass.slider_green.getValue() * 0.1;
	    			blue_multiplier = UIClass.slider_blue.getValue() * 0.1;
        		break;
        	case "SPG":
	        		red_multiplier = UIClass.slider_red.getValue() * 0.1;
	    			green_multiplier = UIClass.slider_green.getValue() * 0.1;
	    			blue_multiplier = UIClass.slider_blue.getValue() * 0.1;
        		break;
        }

        //do something with the image
        int width = bi.getWidth();
        int height = bi.getHeight();
        int red, green, blue, alpha;
        for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				Color c = new Color(bi.getRGB(j, i), true);
                alpha = c.getAlpha();
            	red = (int)(c.getRed() * red_multiplier);
				green = (int)(c.getGreen() * green_multiplier);
				blue = (int)(c.getBlue() * blue_multiplier);
				if (red > 255){
					red = 255;
				}
				if (green > 255){
					green = 255;
				}
				if (blue > 255){
					blue = 255;
				}
				if (red < 0){
					red = 0;
				}
				if (green < 0){
					green = 0;
				}
				if (blue < 0){
					blue = 0;
				}
				Color color = new Color(red, green, blue, alpha);
				bi.setRGB(j,i,color.getRGB());
			}
        }
        //finished doing something with the image
        
	    return (bi);
	}
	
	public static void saveBufferedImage (BufferedImage bi, String url, String filepath){
		//Insert the converted BI, url to the original file for filename generation and the desired path to save the BI to
		OutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filepath + "/" + UtilitiesClass.generateFilenameFromURL(url)));
		    ImageIO.write(bi, "png", out);
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
