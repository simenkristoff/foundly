package foundly.ui;

import foundly.restapi.RestApi;
import foundly.ui.controller.Navigator;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * JavaFX App.
 */
@SpringBootApplication
public class App extends Application {

  public static final DateTimeFormatter DATEPATTERN = DateTimeFormatter.ofPattern("dd.MM.YY HH:mm");
  public static final String API_URL = "http://localhost:8098";

  private static final String TITLE = "Foundly";
  private static final double WIDTH = 720;
  private static final double HEIGHT = 640;
  private static final int COUNT_LIMIT = 10;
  private static final String ICON = "img/icons/icon.png";

  private ConfigurableApplicationContext springContext;
  private Navigator navigator;

  /**
   * Start the app.
   *
   * @param stage the stage
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void start(Stage stage) throws IOException {
    stage.getIcons().add(new Image(this.getClass().getResource(ICON).toExternalForm()));
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
