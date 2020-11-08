package foundly.ui;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;


/**
 * The Class AppTest.
 */
public class AppTest extends ApplicationTest {

  /**
   * Runs the App for testing environment.
   *
   * @throws Exception the exception
   */
  @BeforeEach
  public void runAppToTests() throws Exception {
    FxToolkit.registerPrimaryStage();
    FxToolkit.setupApplication(App::new);
    FxToolkit.showStage();
    WaitForAsyncUtils.waitForFxEvents(100);
  }

  /**
   * Stop the app.
   *
   * @throws TimeoutException the timeout exception
   */
  @AfterEach
  public void stopApp() throws TimeoutException {
    FxToolkit.cleanupStages();
  }

  /**
   * Star the app.
   *
   * @param primaryStage the primary stage
   */
  @Override
  public void start(Stage primaryStage) {
    primaryStage.toFront();
  }
}
