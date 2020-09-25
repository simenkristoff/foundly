package foundly.ui.controller;

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

	public static Label label;
	public static ProgressBar progress;
	
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
		label = splash_txt;
		progress = splash_progress;
	}

}
