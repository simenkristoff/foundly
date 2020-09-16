package foundly.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import foundly.core.containers.ItemCellLayout;
import foundly.core.model.Item;
import foundly.database.daoImpl.ItemDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The Class ItemController.
 * Controller-class for the item view.
 */
public class ItemController extends AbstractViewController {

	@FXML ListView<Item> list_items;
	
	private ItemDaoImpl itemDao;
	private static ObservableList<Item> items;
	
	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemDao = new ItemDaoImpl();
		items = FXCollections.observableArrayList(itemDao.getAll());
		
		list_items.setItems(items);
		
		/**
		 *  Changes the display of each cell to ItemCellLayout
		 */
		list_items.setCellFactory(param -> new ListCell<Item>() {
			private ItemCellLayout itemCellLayout;
			
			@Override
			public void updateItem(Item item, boolean empty) {
				super.updateItem(item, empty);
				if(empty) {
					setText(null);
					setGraphic(null);
					return;
				}
				itemCellLayout = new ItemCellLayout(item);
				setGraphic(itemCellLayout);
			}
		});
	}
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public static ObservableList<Item> getItems(){
		return items;
	}

}
