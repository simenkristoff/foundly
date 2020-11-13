package foundly.ui;

import foundly.ui.controller.Navigator;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Properties;
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

  private static final Properties config = new Properties();
  private static final Properties defaults = new Properties();

  private Navigator navigator;

  /**
   * Sets up the stage and starts the app.
   *
   * @param stage the stage
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void start(Stage stage) throws IOException {
    stage.getIcons()
        .add(new Image(this.getClass().getResource(getProperty("resource.icon")).toExternalForm()));
    stage.setTitle(getProperty("app.title"));
    stage.setScene(this.navigator.getScene());
    stage.setWidth(Double.parseDouble(getProperty("app.width")));
    stage.setHeight(Double.parseDouble(getProperty("app.height")));
    stage.setMaximized(Boolean.parseBoolean(getProperty("maximized")));
    stage.setResizable(Boolean.parseBoolean(getProperty("resizable")));
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
   * Initialize the app. Loads configuration, initializes the navigator and updates the preloader.
   *
   * @throws Exception the exception
   */
  public void init() throws Exception {
    loadProperties(config, "client.properties"); // Load custom settings
    loadProperties(defaults, "default.properties"); // Load default settings
    
    this.navigator = new Navigator();

    int loadCounter = Integer.parseInt(getProperty("app.loadCounter"));
    for (int i = 0; i < loadCounter; i++) {
      double progress = (double) i / loadCounter;
      this.notifyPreloader(new Preloader.ProgressNotification(progress));
      Thread.sleep(100);
    }
  }

  /**
   * Thread safe function that checks status of the connection to REST Api.
   *
   * @return true, if connection is established
   */
  public static synchronized boolean isRemote() {
    String hostname = getProperty("api.hostname");
    int port = Integer.parseInt(getProperty("api.port"));
    int timeout = Integer.parseInt(getProperty("api.timeoutMillis"));

    SocketAddress socketAddress = new InetSocketAddress(hostname, port);
    Socket socket = new Socket();
    try {
      socket.connect(socketAddress, timeout);
      socket.close();
      return true;
    } catch (SocketTimeoutException e) {
      System.out
          .println("SocketTimeoutException: " + hostname + ":" + port + ". " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IOException - Unable to connect to " + hostname + " at port: " + port
          + ". " + e.getMessage());
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
   * Thread safe function to retrieve property-settings. The App will fetch properties from
   * 'client.properties' first, which means that this file can be used to override default settings.
   * However, if a field is declared but left empty in this config file, the app will use the
   * settings from defaults.properties instead.
   * 
   * @param propertyName the property to fetch
   * @return String property
   */
  public static synchronized String getProperty(String propertyName) {
    String property;
    try {
      property = config.getProperty(propertyName);
      if (property.isEmpty()) {
        System.err.println("Property field \"" + propertyName
            + "\" is empty. Please check the configuration file.\nReverting to default settings");
        property = defaults.getProperty(propertyName);
      }
    } catch (NullPointerException e) {
      property = defaults.getProperty(propertyName);
    }
    return property;
  }

  /**
   * Loads configuration settings from a property file into a Properties object.
   * 
   * @param property the property object to load into
   * @param filename the file to load from
   */
  private void loadProperties(Properties property, String filename) {
    InputStream propertyStream = null;
    try {
      propertyStream = this.getClass().getResourceAsStream("properties/" + filename);
      property.load(propertyStream);
      propertyStream.close();
    } catch (IOException e) {
      System.out.println("IOException: Could not load " + filename + ". " + e.getMessage());
    }
    if (propertyStream != null) {
      try {
        propertyStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
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
