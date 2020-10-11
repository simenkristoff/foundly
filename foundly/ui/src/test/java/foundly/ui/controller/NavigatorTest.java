package foundly.ui.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import foundly.ui.AppTest;
import javafx.scene.Node;
import javafx.scene.control.Button;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NavigatorTest extends AppTest{

	private final String FOUND_BUTTON = "#btn_found";
	private final String FOUND_ITEM_POPUP_HEADER = "Legg til funnet gjenstand";
	private final String FOUND_ITEM_ADD = "Legg til";
	private final String FOUND_ITEM_CANCEL = "Avbryt";
	
	private final String LOST_BUTTON = "#btn_lost";
	private final String LOST_ITEM_POPUP_HEADER = "Legg til mistet gjenstand";
	private final String LOST_ITEM_ADD = "Legg til";
	private final String LOST_ITEM_CANCEL = "Avbryt";
	
	@Test
    @Order(1)
    @DisplayName("Test Found-Item function")
    public void testFoundButton() {
        Button found = lookup(FOUND_BUTTON).query();
        clickOn(found);
        
        // Checks if Found-button opens the "Found-Item Dialog"
        assertTrue(this.listTargetWindows().size() == 2);
        Node modal = this.targetWindow(1).lookup(".dialog-pane").query();
        assertNotNull(modal);
        assertNotNull(from(modal).lookup(FOUND_ITEM_POPUP_HEADER));
        
        // Checks if add- and cancel-buttons exists
        Button add = lookup(FOUND_ITEM_ADD).queryButton();
        Button cancel = lookup(FOUND_ITEM_CANCEL).queryButton();
        assertNotNull(add);
        assertNotNull(cancel);
        
        // Checks if close-button closes the window
        clickOn(cancel);
        assertTrue(this.listTargetWindows().size() == 1);
    }
	
	@Test
    @Order(2)
    @DisplayName("Test Lost-Item function")
    public void testLostButton() {
		Button found = lookup(LOST_BUTTON).query();
        clickOn(found);
        
        // Checks if Found-button opens the "Lost-Item Dialog"
        assertTrue(this.listTargetWindows().size() == 2);
        Node modal = this.targetWindow(1).lookup(".dialog-pane").query();
        assertNotNull(modal);
        assertNotNull(from(modal).lookup(LOST_ITEM_POPUP_HEADER));
        
        // Checks if add- and cancel-buttons exists
        Button add = lookup(LOST_ITEM_ADD).queryButton();
        Button cancel = lookup(LOST_ITEM_CANCEL).queryButton();
        assertNotNull(add);
        assertNotNull(cancel);
        
        // Checks if close-button closes the window
        clickOn(cancel);
        assertTrue(this.listTargetWindows().size() == 1);       
    }
}
