package foundly;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import foundly.controller.Navigator;

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
	
	private final int COUNT_LIMIT = 10;
	
	/** The icon **/
	private final Image ICON = new Image(App.class.getResource("img/icons/icon.png").toExternalForm());
	
	/** The logo **/
	public static final Image LOGO = new Image(App.class.getResource("img/logo.png").toExternalForm());
	
	/** The navigator. */
	private Navigator navigator;
	
	/**
	 * Start the app.
	 *
	 * @param stage the stage
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void start(Stage stage) throws IOException {
		stage.getIcons().add(ICON);
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
     * 
     *TODO implement SwingWorker later for handling data-loading
     * in the background.
     */
    public void init() throws Exception {
    	this.navigator = new Navigator();
    	for(int i = 0; i < COUNT_LIMIT; i++) {
    		double progress = (double) i / COUNT_LIMIT;
    		this.notifyPreloader(new Preloader.ProgressNotification(progress));
    		Thread.sleep(100);
    	}
    }
    
    /**
     * Close app.
     */
    private void close() {
    	ConnectionHandler.closePool();
    	Platform.exit();
    	//System.exit(-1);
    }
    
    public Navigator getNavigator() {
    	return this.navigator;
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
        System.setProperty("javafx.preloader", SplashScreen.class.getCanonicalName());
        launch();
    }

}