package foundly.ui.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import foundly.ui.App;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Tests for the class Navigator.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NavigatorTest extends ApplicationTest {

  private final String foundButton = "#btnFound";
  private final String foundItemHeaderText = "Legg til funnet gjenstand";
  private final String addFoundItemButton = "Legg til";
  private final String cancelFoundItemButton = "Avbryt";

  private final String lostButton = "#btnLost";
  private final String lostItemHeaderText = "Legg til mistet gjenstand";
  private final String addLostItemButton = "Legg til";
  private final String cancelLostItemButton = "Avbryt";

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

  /**
   * Test if the button for found items opens the correct modal and verify that the close button
   * works.
   */
  @Test
  @Order(1)
  @DisplayName("Test Found-Item modal")
  public void testFoundButton() {
    Button found = lookup(foundButton).query();
    clickOn(found);

    // Checks if Found-button opens the "Found-Item Dialog"
    assertTrue(this.listTargetWindows().size() == 2);
    Node modal = this.targetWindow(1).lookup(".dialog-pane").query();
    assertNotNull(modal);
    assertNotNull(from(modal).lookup(foundItemHeaderText));

    // Checks if add- and cancel-buttons exists
    Button add = lookup(addFoundItemButton).queryButton();
    Button cancel = lookup(cancelFoundItemButton).queryButton();
    assertNotNull(add);
    assertNotNull(cancel);

    // Checks if close-button closes the window
    clickOn(cancel);
    assertTrue(this.listTargetWindows().size() == 1);
  }

  /**
   * Test if the button for lost items opens the correct modal and verify that the close button
   * works.
   */
  @Test
  @Order(2)
  @DisplayName("Test Lost-Item modal")
  public void testLostButton() {
    Button found = lookup(lostButton).query();
    clickOn(found);

    // Checks if Found-button opens the "Lost-Item Dialog"
    assertTrue(this.listTargetWindows().size() == 2);
    Node modal = this.targetWindow(1).lookup(".dialog-pane").query();
    assertNotNull(modal);
    assertNotNull(from(modal).lookup(lostItemHeaderText));

    // Checks if add- and cancel-buttons exists
    Button add = lookup(addLostItemButton).queryButton();
    Button cancel = lookup(cancelLostItemButton).queryButton();
    assertNotNull(add);
    assertNotNull(cancel);

    // Checks if close-button closes the window
    clickOn(cancel);
    assertTrue(this.listTargetWindows().size() == 1);
  }
}

