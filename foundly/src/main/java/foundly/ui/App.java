package foundly.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import foundly.core.model.Item;
import foundly.database.ConnectionHandler;
import foundly.database.daoImpl.ItemDaoImpl;
import foundly.ui.controller.Navigator;

/**
 * JavaFX App
 */
public class App extends Application {
	
	private final String TITLE = "Foundly";
	private final double WIDTH = 720;
	private final double HEIGHT = 480;
	
	
	private Navigator navigator;
	private ItemDaoImpl itemDao = new ItemDaoImpl();
	
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
	
    private void initialize() {
    	this.navigator = new Navigator();
    }
    
    private void close() {
    	ConnectionHandler.closePool();
    	Platform.exit();
    	System.exit(-1);
    }

    public static void main(String[] args) {
    	System.setProperty("os.target", "ios");
        System.setProperty("os.name", "iOS");
        System.setProperty("glass.platform", "ios");
        System.setProperty("targetos.name", "iOS");
        launch();
    }

}