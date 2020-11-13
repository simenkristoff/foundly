package foundly.ui;

import foundly.ui.controller.Navigator;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * JavaFX App. The launcher application for this project.
 */
public class App extends Application {

  public static final DateTimeFormatter DATEPATTERN = DateTimeFormatter.ofPattern("dd.MM.YY HH:mm");
  public static final String API_URL = "http://localhost:8098";

  private static final String TITLE = "Foundly";
  private static final double WIDTH = 720;
  private static final double HEIGHT = 640;
  private static final int COUNT_LIMIT = 10;
  private static final String ICON = "img/icons/icon.png";

  private Navigator navigator;

  /**
   * Sets up the stage and starts the app.
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
        close();
      }
    });
  }

  /**
   * Closes the application.
   *
   */
  public void close() {
    Platform.exit();
  }

  /**
   * Initialize the app. Initializes the navigator and updates the preloader.
   *
   * @throws Exception the exception
   */
  public void init() throws Exception {
    this.navigator = new Navigator();
    for (int i = 0; i < COUNT_LIMIT; i++) {
      double progress = (double) i / COUNT_LIMIT;
      this.notifyPreloader(new Preloader.ProgressNotification(progress));
      Thread.sleep(100);
    }
  }

  /**
   * Checks connection to the REST Api.
   *
   * @return true, if connection is established
   */
  public static boolean isRemote() {
    SocketAddress socketAddress = new InetSocketAddress("localhost", 8098);
    Socket socket = new Socket();
    int timeout = 200;
    try {
      socket.connect(socketAddress, timeout);
      socket.close();
      return true;
    } catch (SocketTimeoutException e) {
      System.out.println("SocketTimeoutException localhost:8098. " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IOException - Unable to connect to localhost:8098. " + e.getMessage());
    }
    return false;
  }

  /**
   * Gets the navigator.
   *
   * @return the navigator
   */
  public Navigator getNavigator() {
    return this.navigator;
  }

  /**
   * The main method. Starts the preloader and launches the application.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    System.setProperty("javafx.preloader", SplashScreen.class.getCanonicalName());
    Application.launch(args);
  }

}
