package foundly.ui.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.*;
import org.testfx.service.query.PointQuery;

import foundly.ui.AppTest;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ItemControllerTest extends AppTest {

	private List<String> EXPECTED_TABS = Arrays.asList("alle", "mistet", "funnet");
	
	@Test
	@Order(3)
	@DisplayName("Test tabs")
	public void testTabs() {
		TabPane tabPane = lookup("#tabPane").query();
		List<String> tabNames = new ArrayList<String>();
		for(Tab tab : tabPane.getTabs()) {
			tabNames.add(tab.getId().toLowerCase());
		}
		
		// Check if the expected tabs are loaded
		assertTrue(EXPECTED_TABS.containsAll(tabNames));
		
		// This test has no purpose as of now.
		for(Tab tab : tabPane.getTabs()) {
			Node clk = lookup("#" + tab.getId()).query(); // Workaround in order to use "clickOn"-function
			clickOn(clk);
		}
	}
}
