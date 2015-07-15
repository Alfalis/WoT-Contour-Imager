import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.json.JSONException;

public class UIClass {
	static JLabel slider_red_info;
	static JSlider slider_red;
	static JLabel slider_red_value;
	static JLabel slider_green_info;
	static JSlider slider_green;
	static JLabel slider_green_value;
	static JLabel slider_blue_info;
	static JSlider slider_blue;
	static JLabel slider_blue_value;
	static JLabel original_image = new JLabel();
	static JLabel tinted_image = new JLabel();
	static JLabel imagesDownloaded_label = new JLabel();
	static BufferedImage original_bi;
	static BufferedImage tinted_bi;
	static JTextField pathfield;
	static JButton changepath_button;
	static JButton savesettings_button;
	static JButton restoresettings_button;
	static JButton downloadimages_button;
	static int imagesToDownload;
	static int imagesDownloaded = 0;
	static JProgressBar progressbar;

	static String [] tanktypes4combobox = {"Heavy Tanks", "Medium Tanks", "Light Tanks", "Tankdestroyers", "SPGs"};
	static String [] tanktypes4download = {"heavyTank", "mediumTank", "lightTank", "AT-SPG", "SPG"};
	static JFrame mainframe;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static JComboBox tanktypes = new JComboBox(tanktypes4combobox);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void UIBuilder(String version) throws JSONException, IOException, ConfigurationException{
		mainframe = new JFrame("WoT Contour Imager v" + version);
		mainframe.setSize(550,410);
		mainframe.setLocation(180,120);
		mainframe.setLayout(new FlowLayout());
		mainframe.setResizable(false);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pathfield = new JTextField(UtilitiesClass.getSetting("filepath"));
		pathfield.setPreferredSize(new Dimension(450,25));
		pathfield.setEditable(false);
		final JFileChooser fc = new JFileChooser();
		
		changepath_button = new JButton("Path");
		changepath_button.setPreferredSize(new Dimension(60,25));
		changepath_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fc.setCurrentDirectory(new File(pathfield.getText()));
				fc.setDialogTitle("Select path for contour icons");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int fc_return = fc.showOpenDialog(null);
				if(fc_return == JFileChooser.APPROVE_OPTION)
		        {
		            pathfield.setText(fc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
		    		XMLConfiguration settings;
					try {
						settings = new XMLConfiguration("ContourImager_Settings.xml");
			    		settings.setProperty("filepath", pathfield.getText());
			    		settings.setFileName("ContourImager_Settings.xml");
			    		settings.save();
					} catch (ConfigurationException e1){
						e1.printStackTrace();
					}
		        }
				mainframe.requestFocus();
			}
		});
		
		tanktypes.setPreferredSize(new Dimension(500,25));
		tanktypes.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event) {
			       if (event.getStateChange() == ItemEvent.SELECTED){
			    	   try{
							mainframe.requestFocus();
							UISliderUpdater();
							UIImager(UtilitiesClass.TankTypeReturner());
							saveButtonChanger();
			    	   } catch (ConfigurationException | JSONException | IOException e){
			    		   e.printStackTrace();
			    	   }
			       }
			    } 
		});
		
		//Red slider
		slider_red_info = new JLabel("R:");
		slider_red_info.setPreferredSize(new Dimension(50,25));
		
		slider_red = new JSlider(JSlider.HORIZONTAL, 0, 30, 0);
		slider_red.setPreferredSize(new Dimension(400,45));
		slider_red.setMinorTickSpacing(1);
	    slider_red.setMajorTickSpacing(10);
	    slider_red.setPaintTicks(true);
	    slider_red.setSnapToTicks(true);
	    Hashtable labelTable_red = new Hashtable();
	    labelTable_red.put(new Integer(0), new JLabel("0.0"));
	    labelTable_red.put(new Integer(10), new JLabel("1.0"));
	    labelTable_red.put(new Integer(20), new JLabel("2.0"));
	    labelTable_red.put(new Integer(30), new JLabel("3.0"));
	    slider_red.setLabelTable(labelTable_red);
	    slider_red.setPaintLabels(true);
	    
	    slider_red.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent ce){
	            slider_red_value.setText("    " + Double.toString(slider_red.getValue() * 0.1).substring(0,3));
	        }
	    });
	    slider_red.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				try {
					UIImager(UtilitiesClass.TankTypeReturner());
					saveButtonChanger();
					restoreButtonChanger();
				} catch (ConfigurationException | JSONException | IOException e) {
					e.printStackTrace();
				}
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
	    	
	    });
	    
	    slider_red_value = new JLabel("    " + Double.toString(slider_red.getValue() * 0.1).substring(0,3));
		slider_red_value.setPreferredSize(new Dimension(50,25));
		
		//Green slider
		slider_green_info = new JLabel("G:");
		slider_green_info.setPreferredSize(new Dimension(50,25));
		
		slider_green = new JSlider(JSlider.HORIZONTAL, 0, 30, 0);
		slider_green.setPreferredSize(new Dimension(400,45));
		slider_green.setMinorTickSpacing(1);
	    slider_green.setMajorTickSpacing(10);
	    slider_green.setPaintTicks(true);
	    Hashtable labelTable_green = new Hashtable();
	    labelTable_green.put(new Integer(0), new JLabel("0.0"));
	    labelTable_green.put(new Integer(10), new JLabel("1.0"));
	    labelTable_green.put(new Integer(20), new JLabel("2.0"));
	    labelTable_green.put(new Integer(30), new JLabel("3.0"));
	    slider_green.setLabelTable(labelTable_green);
	    slider_green.setPaintLabels(true);
	    
	    slider_green.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent ce) {
	            slider_green_value.setText("    " + Double.toString(slider_green.getValue() * 0.1).substring(0,3));
	        }
	    });
	    slider_green.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				try {
					UIImager(UtilitiesClass.TankTypeReturner());
					saveButtonChanger();
					restoreButtonChanger();
				} catch (ConfigurationException | JSONException | IOException e) {
					e.printStackTrace();
				}
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
	    	
	    });
	    
	    slider_green_value = new JLabel("    " + Double.toString(slider_green.getValue() * 0.1).substring(0,3));
		slider_green_value.setPreferredSize(new Dimension(50,25));
				
		//Blue slider
		slider_blue_info = new JLabel("B:");
		slider_blue_info.setPreferredSize(new Dimension(50,25));
		
		slider_blue = new JSlider(JSlider.HORIZONTAL, 0, 30, 0);
		slider_blue.setPreferredSize(new Dimension(400,45));
		slider_blue.setMinorTickSpacing(1);
	    slider_blue.setMajorTickSpacing(10);
	    slider_blue.setPaintTicks(true);
	    Hashtable labelTable_blue = new Hashtable();
	    labelTable_blue.put(new Integer(0), new JLabel("0.0"));
	    labelTable_blue.put(new Integer(10), new JLabel("1.0"));
	    labelTable_blue.put(new Integer(20), new JLabel("2.0"));
	    labelTable_blue.put(new Integer(30), new JLabel("3.0"));
	    slider_blue.setLabelTable(labelTable_blue);
	    slider_blue.setPaintLabels(true);
	    
	    slider_blue.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent ce) {
	            slider_blue_value.setText("    " + Double.toString(slider_blue.getValue() * 0.1).substring(0,3));
	        }
	    });
	    slider_blue.addMouseListener(new MouseListener(){
			public void mouseReleased(MouseEvent arg0) {
				try {
					UIImager(UtilitiesClass.TankTypeReturner());
					saveButtonChanger();
					restoreButtonChanger();
				} catch (ConfigurationException | JSONException | IOException e) {
					e.printStackTrace();
				}
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
	    	
	    });
	    
	    slider_blue_value = new JLabel("    " + Double.toString(slider_blue.getValue() * 0.1).substring(0,3));
		slider_blue_value.setPreferredSize(new Dimension(50,25));
		
		UISliderUpdater();
		UIImager("heavyTank");
		original_image.setPreferredSize(new Dimension(250,60));
		original_image.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tinted_image.setPreferredSize(new Dimension(250,60));
		tinted_image.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		savesettings_button = new JButton("Save Heavy Tanks config");
		savesettings_button.setPreferredSize(new Dimension(200,25));
		savesettings_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					UtilitiesClass.saveSettings();
					saveButtonChanger();
					restoreButtonChanger();
				} catch (ConfigurationException e1){
					e1.printStackTrace();
				}
			}
		});
		savesettings_button.setEnabled(false);
		
		restoresettings_button = new JButton ("Restore Heavy Tanks config");
		restoresettings_button.setPreferredSize(new Dimension(200,25));
		restoresettings_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					UtilitiesClass.restoreSettings();
					saveButtonChanger();
					restoreButtonChanger();
				} catch (ConfigurationException | JSONException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		restoresettings_button.setEnabled(false);
		
		downloadimages_button = new JButton("Save settings and download contour icons");
		downloadimages_button.setPreferredSize(new Dimension(405,25));
		downloadimages_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					UtilitiesClass.saveSettings();
					UtilitiesClass.generateFolder();
					saveButtonChanger();
					restoreButtonChanger();
				} catch (ConfigurationException e1) {
					e1.printStackTrace();
				}
				changepath_button.setEnabled(false);
				slider_red.setEnabled(false);
				slider_green.setEnabled(false);
				slider_blue.setEnabled(false);
				downloadimages_button.setEnabled(false);
				imagesDownloaded = 0;
				imagesDownloaded_label.setText("(" + String.format("%03d", imagesDownloaded) + "/" + imagesToDownload + " images downloaded)");
				Thread t1 = new Thread (new imageDownloader());
				t1.start();
				mainframe.requestFocus();
			}
		});
		
		for (int tt = 0; tt < tanktypes4download.length; tt++){
            String type = tanktypes4download[tt];
            try {
				imagesToDownload += APIClass.generateArrayList(APIClass.accessAPI(), type).size();
			} catch (JSONException e1){
				e1.printStackTrace();
			}
        }
		imagesDownloaded_label.setText("(" + String.format("%03d", imagesDownloaded) + "/" + imagesToDownload + " images downloaded)");
		
		progressbar = new JProgressBar(0, imagesToDownload);
		progressbar.setPreferredSize(new Dimension (400,15));
		
		mainframe.add(pathfield);
		mainframe.add(changepath_button);
		mainframe.add(tanktypes);
		mainframe.add(slider_red_info);
		mainframe.add(slider_red);
		mainframe.add(slider_red_value);
		mainframe.add(slider_green_info);
		mainframe.add(slider_green);
		mainframe.add(slider_green_value);
		mainframe.add(slider_blue_info);
		mainframe.add(slider_blue);
		mainframe.add(slider_blue_value);
		mainframe.add(original_image);
		mainframe.add(tinted_image);
		mainframe.add(savesettings_button);
		mainframe.add(restoresettings_button);
		mainframe.add(downloadimages_button);
		mainframe.add(progressbar);
		mainframe.add(imagesDownloaded_label);
		
		mainframe.setVisible(true);
		mainframe.requestFocus();
	}
	
	public static void UISliderUpdater() throws ConfigurationException{
		XMLConfiguration settings = new XMLConfiguration("ContourImager_Settings.xml");
		switch (tanktypes4combobox[tanktypes.getSelectedIndex()]){
			case ("Heavy Tanks"):
				slider_red.setValue((int)(settings.getDouble("color_multipliers.heavyTank.red") * 10));
				slider_green.setValue((int)(settings.getDouble("color_multipliers.heavyTank.green") * 10));
				slider_blue.setValue((int)(settings.getDouble("color_multipliers.heavyTank.blue") * 10));
				break;
			case ("Medium Tanks"):
					slider_red.setValue((int)(settings.getDouble("color_multipliers.mediumTank.red") * 10));
					slider_green.setValue((int)(settings.getDouble("color_multipliers.mediumTank.green") * 10));
					slider_blue.setValue((int)(settings.getDouble("color_multipliers.mediumTank.blue") * 10));
				break;
			case ("Light Tanks"):
					slider_red.setValue((int)(settings.getDouble("color_multipliers.lightTank.red") * 10));
					slider_green.setValue((int)(settings.getDouble("color_multipliers.lightTank.green") * 10));
					slider_blue.setValue((int)(settings.getDouble("color_multipliers.lightTank.blue") * 10));
				break;
			case ("Tankdestroyers"):
					slider_red.setValue((int)(settings.getDouble("color_multipliers.AT-SPG.red") * 10));
					slider_green.setValue((int)(settings.getDouble("color_multipliers.AT-SPG.green") * 10));
					slider_blue.setValue((int)(settings.getDouble("color_multipliers.AT-SPG.blue") * 10));
				break;
			case ("SPGs"):
					slider_red.setValue((int)(settings.getDouble("color_multipliers.SPG.red") * 10));
					slider_green.setValue((int)(settings.getDouble("color_multipliers.SPG.green") * 10));
					slider_blue.setValue((int)(settings.getDouble("color_multipliers.SPG.blue") * 10));
				break;
		}
	}
	
	public static void UIImager(String tanktype) throws JSONException, IOException, ConfigurationException{
		original_bi = ImageClass.originalImage(APIClass.generateArrayList(APIClass.accessAPI(), tanktype).get(0), tanktype);
		Image image_original = Toolkit.getDefaultToolkit().createImage(original_bi.getSource());
		original_image.setIcon(new ImageIcon(image_original));
		tinted_bi = ImageClass.tintImage(original_bi, tanktype);
		Image image_tinted = Toolkit.getDefaultToolkit().createImage(tinted_bi.getSource());
		tinted_image.setIcon(new ImageIcon(image_tinted));
	}
	
	public static void saveButtonChanger() throws ConfigurationException{
		Boolean something_changed = false;
		XMLConfiguration settings = new XMLConfiguration("ContourImager_Settings.xml");
		savesettings_button.setText("Save " + tanktypes4combobox[tanktypes.getSelectedIndex()] + " config");
		if (!UIClass.slider_red_value.getText().substring(4,7).equals(settings.getString("color_multipliers." + UtilitiesClass.TankTypeReturner() + ".red"))){
			something_changed = true;
		}
		if (!UIClass.slider_green_value.getText().substring(4,7).equals(settings.getString("color_multipliers." + UtilitiesClass.TankTypeReturner() + ".green"))){
			something_changed = true;
		}
		if (!UIClass.slider_blue_value.getText().substring(4,7).equals(settings.getString("color_multipliers." + UtilitiesClass.TankTypeReturner() + ".blue"))){
			something_changed = true;
		}
		
		if (something_changed == true){
			savesettings_button.setEnabled(true);
		}
		else{
			savesettings_button.setEnabled(false);
		}
	}
	
	public static void restoreButtonChanger() throws ConfigurationException{
		Boolean something_changed = false;
		XMLConfiguration settings = new XMLConfiguration("ContourImager_Settings.xml");
		restoresettings_button.setText("Restore " + tanktypes4combobox[tanktypes.getSelectedIndex()] + " config");
		if (!UIClass.slider_red_value.getText().substring(4,7).equals(settings.getString("color_multipliers." + UtilitiesClass.TankTypeReturner() + ".red"))){
			something_changed = true;
		}
		if (!UIClass.slider_green_value.getText().substring(4,7).equals(settings.getString("color_multipliers." + UtilitiesClass.TankTypeReturner() + ".green"))){
			something_changed = true;
		}
		if (!UIClass.slider_blue_value.getText().substring(4,7).equals(settings.getString("color_multipliers." + UtilitiesClass.TankTypeReturner() + ".blue"))){
			something_changed = true;
		}
		
		if (something_changed == true){
			restoresettings_button.setEnabled(true);
		}
		else{
			restoresettings_button.setEnabled(false);
		}
	}
	
}