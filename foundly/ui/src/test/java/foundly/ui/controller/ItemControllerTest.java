package foundly.ui.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import foundly.core.model.Item;
import foundly.core.model.Item.State;
import foundly.ui.App;
import foundly.ui.control.validator.AbstractValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Tests for the class ItemController.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemControllerTest extends ApplicationTest {

  // Buttons
  private final String foundButton = "#btnFound";
  private final String lostButton = "#btnLost";
  private final String addItemButton = "Legg til";

  // Fields
  String[] inputIds = {"_itemTitle", "_itemDesc", "_itemEmail", "_itemPhone"};

  // Tabs
  List<String> expectedTabs = Arrays.asList("alle", "mistet", "funnet");

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
   * Test if the validation works for all fields, and if a found item is added correctly. Should
   * have the state: FOUND.
   */
  @Test
  @Order(1)
  @DisplayName("Test input validation and adding a found item")
  public void invalidFieldValidatorTest() {
    List<AbstractValidator<?>> foundInputs = FXCollections.observableArrayList();

    Button found = lookup(foundButton).query();
    clickOn(found);

    // Check if fields are in order
    for (int i = 0; i < 4; i++) {
      AbstractValidator<?> input = lookup("#found" + inputIds[i]).query();
      assertNotNull(input);
      foundInputs.add(input);
    }

    // Try to add item
    Button foundAdd = lookup(addItemButton).queryButton();
    clickOn(foundAdd);
    WaitForAsyncUtils.waitForFxEvents(100); // wait

    // Check if modal didn't close
    assertTrue(this.listTargetWindows().size() == 2);

    // Check if inputs contain errors
    for (AbstractValidator<?> input : foundInputs) {
      assertTrue(input.getControl().getStyleClass().contains("error"));
      assertFalse(input.getErrorMessage().isEmpty());
    }

    // Check if input clears errors when they are corrected
    clickOn(foundInputs.get(0).getControl()).write("Funnet airpods");
    clickOn(foundAdd);
    assertFalse(foundInputs.get(0).getControl().getStyleClass().contains("error"));

    clickOn(foundInputs.get(1).getControl()).write("Fant et par med airpods på SIT i dag.");
    clickOn(foundAdd);
    assertFalse(foundInputs.get(1).getControl().getStyleClass().contains("error"));

    clickOn(foundInputs.get(2).getControl()).write("peter.nordby@live.no");
    clickOn(foundAdd);
    assertFalse(foundInputs.get(2).getControl().getStyleClass().contains("error"));

    clickOn(foundInputs.get(3).getControl()).write("91371023");
    clickOn(foundAdd);
    assertFalse(foundInputs.get(3).getControl().getStyleClass().contains("error"));

    // Checks if modal closes
    assertTrue(this.listTargetWindows().size() == 1);

    // Check if item has been added to the list view
    ListView<Item> itemsList = lookup("#items-list").queryListView();
    assertTrue(itemsList.getItems().size() == 1);

    // Check if the item has the correct state
    assertTrue(itemsList.getItems().get(0).getState() == State.FOUND);
  }

  /**
   * Test if a lost item is added correcly. Should have the state: LOST.
   */
  @Test
  @Order(2)
  @DisplayName("Test adding a lost item")
  public void addLostItemTest() {
    List<AbstractValidator<?>> lostInputs = FXCollections.observableArrayList();

    Button lost = lookup(lostButton).query();
    clickOn(lost);

    // Check if fields are in order
    for (int i = 0; i < 4; i++) {
      AbstractValidator<?> input = lookup("#lost" + inputIds[i]).query();
      assertNotNull(input);
      lostInputs.add(input);
    }

    // Fill in form and add a lost item
    clickOn(lostInputs.get(0).getControl()).write("Mistet airpodsa mine");
    clickOn(lostInputs.get(1).getControl())
        .write("Mistet airpodsa mine på SIT i går. Var der rundt kl 17-18.");
    clickOn(lostInputs.get(2).getControl()).write("simen.kristoffersen98@gmail.com");
    clickOn(lostInputs.get(3).getControl()).write("90360922");

    Button lostAdd = lookup(addItemButton).queryButton();
    clickOn(lostAdd);
    WaitForAsyncUtils.waitForFxEvents(100); // wait

    // Checks if modal closes
    assertTrue(this.listTargetWindows().size() == 1);

    // Check if item has been added to the list view
    ListView<Item> itemsList = lookup("#items-list").queryListView();
    assertTrue(itemsList.getItems().size() == 2);

    // Check if the item has the correct state
    assertTrue(itemsList.getItems().get(1).getState() == State.LOST);
  }

  /**
   * Test if tabs are filtering the items correctly.
   */
  @Test
  @Order(3)
  @DisplayName("Test filtering of items")
  public void filteringItemsTest() {

    // Add test-items to the ListView
    ListView<Item> itemsList = lookup("#items-list").queryListView();
    WaitForAsyncUtils.waitForFxEvents(100); // wait

    TabPane tabPane = lookup("#tabPane").query();
    List<String> tabNames = new ArrayList<String>();
    for (Tab tab : tabPane.getTabs()) {
      tabNames.add(tab.getId().toLowerCase());
    }

    // Check if the expected tabs are loaded
    assertTrue(expectedTabs.containsAll(tabNames));

    // Test if filtering is working properly
    for (Tab tab : tabPane.getTabs()) {
      Node clk = lookup("#" + tab.getId()).query(); // Workaround in order to use "clickOn"-function
      clickOn(clk);
      if (tab.getId() == "mistet") {
        assertTrue(itemsList.getItems().get(0).getState() == State.LOST);
      }

      if (tab.getId() == "funnet") {
        assertTrue(itemsList.getItems().get(0).getState() == State.FOUND);
      }
    }
  }
}
