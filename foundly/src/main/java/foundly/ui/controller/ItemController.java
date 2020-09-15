package foundly.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;


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
		 *  Sets each cell of the list to display a BorderPane consisting
		 *  of an ImageView on the left side, and the content in the center.
		 *  The content is currently a VBox consisting of each item's title and
		 *  description
		 *  
		 *  TODO Clean up this code by writing a new class (ex. ItemListCell) extending BorderPane which
		 *  takes in an Item in the constructor.
		 */
		list_items.setCellFactory(param -> new ListCell<Item>() {
			private BorderPane bp = new BorderPane();
			private ImageView imageView = new ImageView();
			private VBox imageViewWrapper = new VBox(imageView);
			
			private VBox content;
			private Text title, description;
			
			@Override
			public void updateItem(Item item, boolean empty) {
				super.updateItem(item, empty);
				if(empty) {
					setText(null);
					setGraphic(null);
					return;
				}
				
				imageViewWrapper.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
				imageViewWrapper.setPrefSize(100, 100);
				imageViewWrapper.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
				imageViewWrapper.setAlignment(Pos.CENTER);
				
				imageView.setPreserveRatio(true);
				imageView.fitWidthProperty().bind(imageViewWrapper.widthProperty());
				imageView.fitHeightProperty().bind(imageViewWrapper.heightProperty());
				imageView.setImage(item.getImage());
				
				title = new Text(item.getName());
				description = new Text(item.getDescription());
				content = new VBox(title, description);
				content.setPadding(new Insets(10));
				content.setSpacing(5);
				
				bp.setCenter(content);
				bp.setLeft(imageViewWrapper);
				setGraphic(bp);
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
