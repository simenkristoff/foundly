package foundly.ui;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

import foundly.restapi.RestApi;
import foundly.ui.controller.Navigator;

/**
 * JavaFX App.
 */
@SpringBootApplication
public class App extends Application {

	private ConfigurableApplicationContext springContext;
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
	public final Image LOGO = new Image(App.class.getResource("img/logo.png").toExternalForm());

	/** The navigator. */
	private Navigator navigator;
	
	/** BASE-URL for the API-services **/
	private final static String API_URL = "http://localhost:8098";
	
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
				try {
					stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
		System.exit(-1);
	}

	/**
	 * Initialize app.
	 */
	public void init() throws Exception {
		this.springContext = SpringApplication.run(RestApi.class);
		this.navigator = new Navigator();
		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress = (double) i / COUNT_LIMIT;
			this.notifyPreloader(new Preloader.ProgressNotification(progress));
			Thread.sleep(100);
		}
	}

	public Navigator getNavigator() {
		return this.navigator;
	}
	
	public static String getApiUrl() {
		return API_URL;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.setProperty("javafx.preloader", SplashScreen.class.getCanonicalName());
		Application.launch(args);
	}

}