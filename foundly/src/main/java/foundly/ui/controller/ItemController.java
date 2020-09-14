package foundly.ui.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import foundly.core.model.Item;
import foundly.database.daoImpl.ItemDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class ItemController extends AbstractViewController {

	@FXML ListView<Item> list_items;
	
	private ItemDaoImpl itemDao;
	private static ObservableList<Item> items;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemDao = new ItemDaoImpl();
		items = FXCollections.observableArrayList(itemDao.getAll());
		
		list_items.setItems(items);
		
		list_items.setCellFactory(param -> new ListCell<Item>() {
			private BorderPane bp = new BorderPane();
			private ImageView imageView = new ImageView();
			
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
				
				imageView.setFitHeight(100);
				imageView.setFitWidth(100);
				imageView.setPreserveRatio(false);
				imageView.setImage(item.getImage());
				
				title = new Text(item.getName());
				description = new Text(item.getDescription());
				content = new VBox(title, description);
				content.setPadding(new Insets(10));
				content.setSpacing(5);
				
				bp.setCenter(content);
				bp.setLeft(imageView);
				setGraphic(bp);
			}
		});
	}
	
	public static ObservableList<Item> getItems(){
		return items;
	}

}
