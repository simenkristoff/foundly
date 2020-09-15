package foundly.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import foundly.database.ConnectionHandler;
import foundly.ui.controller.Navigator;

/**
 * JavaFX App.
 */
public class App extends Application {
	
	/** The title. */
	private final String TITLE = "Foundly";
	
	/** The width. */
	private final double WIDTH = 720;
	
	/** The height. */
	private final double HEIGHT = 640;
	
	
	/** The navigator. */
	private Navigator navigator;
	
	/**
	 * Start the app.
	 *
	 * @param stage the stage
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("exports")
	public void start(Stage stage) throws IOException {
		initialize();
		stage.setTitle(TITLE);
		stage.setScene(this.navigator.getScene());
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		stage.setMaximized(false);
		stage.setResizable(true);
		stage.centerOnScreen();
		stage.show();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				close();
			}
		});
	}
	
    /**
     * Initialize app.
     */
    private void initialize() {
    	this.navigator = new Navigator();
    }
    
    /**
     * Close app.
     */
    private void close() {
    	ConnectionHandler.closePool();
    	Platform.exit();
    	System.exit(-1);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	System.setProperty("os.target", "ios");
        System.setProperty("os.name", "iOS");
        System.setProperty("glass.platform", "ios");
        System.setProperty("targetos.name", "iOS");
        launch();
    }

}