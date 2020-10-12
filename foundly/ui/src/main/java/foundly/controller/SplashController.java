package foundly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;

/**
 * The Class SplashController.
 * Controls the Preloader.
 */
public class SplashController implements Initializable {

	private static Label label;
	private static ProgressBar progress;
	
    @FXML
    private Label splash_txt;
    
    @FXML
    private ProgressBar splash_progress;

	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setLabel(splash_txt);
		setProgressBar(splash_progress);
	}
	
	/**
	 * Thread safe function to set the Label
	 * @param label the Label to be set
	 */
	public synchronized static void setLabel(Label label) {
		SplashController.label = label;
	}
	
	/**
	 * Sets the text of label
	 * @param text
	 */
	public static void setLabelText(String text) {
		label.setText(text);
	}
	
	/**
	 * Thread safe function to set the ProgressBar
	 * @param progressBar the ProgressBar to be set
	 */
	public synchronized static void setProgressBar(ProgressBar progressBar) {
		SplashController.progress = progressBar;
	}
	
	/**
	 * Sets the progress value
	 * @param value the progress
	 */
	public static void setProgressValue(Double value) {
		progress.setProgress(value);
	}


}
