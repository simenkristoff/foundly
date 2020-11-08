package foundly.ui.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import foundly.core.model.Item;
import foundly.core.model.Item.State;
import foundly.ui.AppTest;
import foundly.ui.control.validator.AbstractValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests for the class ItemController.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemControllerTest extends AppTest {

  // Buttons
  private final String foundButton = "#btnFound";
  private final String lostButton = "#btnLost";
  private final String addItemButton = "Legg til";

  // Fields
  String[] inputIds = {"_itemTitle", "_itemDesc", "_itemEmail", "_itemPhone"};

  // Tabs
  List<String> expectedTabs = Arrays.asList("alle", "mistet", "funnet");

  @Test
  @Order(2)
  @DisplayName("Test Item controller")
  public void invalidFieldValidatorTest() {

    // TEST ADDING A FOUND ITEM. //
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

    // TEST ADDING A LOST ITEM. //
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

    // Checks if modal closes
    assertTrue(this.listTargetWindows().size() == 1);

    // Check if item has been added to the list view
    assertTrue(itemsList.getItems().size() == 2);

    // Check if the item has the correct state
    assertTrue(itemsList.getItems().get(1).getState() == State.LOST);

    // TEST IF TABS ARE FILTERING THE ITEMS CORRECTLY. //
    System.out.println(itemsList.getItems());
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
