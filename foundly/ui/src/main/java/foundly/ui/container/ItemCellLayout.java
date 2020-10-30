package foundly.ui.container;

import foundly.core.model.Item;
import foundly.ui.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The Class ItemCellLayout. Defines the layout for displaying each Item.
 * ItemCellLayout consists of three classes, Header, Body, and Footer.
 * The Layout is structured extends BorderPane, and is structured with
 * the Item's image on the left-hand side, and the Item's content in the center. 
 */
public class ItemCellLayout extends BorderPane {
	
	private Item item;
	private Header header = new Header();
	private Body body = new Body();
	private Footer footer = new Footer();
	private Image defaultImage = new Image(App.class.getResourceAsStream("img/icons/lost_items_icon.png"));
	private ImageView imageView = new ImageView();
	
	private VBox contentWrapper = new VBox(header, body, footer);
	private VBox imageViewWrapper = new VBox(imageView);
	
	
	/**
	 * Instantiates a new item cell layout.
	 *
	 * @param item the item
	 */
	public ItemCellLayout(Item item) {
		this.item = item;
		initialize();
		setLeft(imageViewWrapper);
		setCenter(contentWrapper);
	}
	
	/**
	 * Initialize.
	 */
	private void initialize() {
		this.getStyleClass().add("itemCell");
		
		header.setTitle(item.getTitle());
		header.setState(item.getState().getValue());
		body.setDescription(item.getDescription());
		//footer.setDate(item.getDateCreated().toLocaleString());
		
		imageViewWrapper.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		imageViewWrapper.setPrefSize(100, 100);
		imageViewWrapper.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		imageViewWrapper.setAlignment(Pos.CENTER);
		
		imageView.setPreserveRatio(true);
		imageView.fitWidthProperty().bind(imageViewWrapper.widthProperty());
		imageView.fitHeightProperty().bind(imageViewWrapper.heightProperty());
		//imageView.setImage((item.getImage() != null) ? item.getImage() : defaultImage);
	}
	
	public String getUserAgentStylesheet() {
		return App.class.getResource("css/components/itemCell.css").toExternalForm();
	}
	
	/**
	 * The Class Header.
	 */
	static private class Header extends AnchorPane {
		
		private Text title = new Text();
		private Text state = new Text();
		private VBox badge;
		
		/**
		 * Instantiates a new header.
		 */
		public Header() {
			setPadding(new Insets(10, 0, 5, 0));
	
			setLeftAnchor(title, 0.0);
			setBottomAnchor(title, 0.0);
			title.getStyleClass().add("title");
			
			badge = new VBox(state);
			setRightAnchor(badge, 0.0);
			setBottomAnchor(badge, 5.0);
			badge.getStyleClass().add("state");
			
			getChildren().addAll(title, badge);
		}
		
		/**
		 * Sets the title.
		 *
		 * @param title the new title
		 */
		public void setTitle(String title) {
			this.title.setText(title);
		}
		
		/**
		 * Sets the state.
		 *
		 * @param state the new state
		 */
		public void setState(String state) {
			switch (state.toLowerCase()){
				default:
					badge.getStyleClass().remove("success");
					badge.getStyleClass().add("danger");
					break;
				case "funnet":
					badge.getStyleClass().remove("success");
					badge.getStyleClass().add("success");
					break;
				}
			this.state.setText(state);
		}	
	}
	
	/**
	 * The Class Body.
	 */
	static private class Body extends VBox {
		
		private Text description = new Text();
		
		/**
		 * Instantiates a new body.
		 */
		public Body() {
			description.getStyleClass().add("description");
			
			VBox.setVgrow(this, Priority.ALWAYS);
			getChildren().addAll(description);
		}
		
		/**
		 * Sets the description.
		 *
		 * @param description the new description
		 */
		public void setDescription(String description) {
			this.description.setText(description);
		}
	}
	
	/**
	 * The Class Footer.
	 */
	static private class Footer extends VBox {
		
		private Text date = new Text();
		
		/**
		 * Instantiates a new footer.
		 */
		public Footer() {
			date.getStyleClass().add("date");
			setAlignment(Pos.BOTTOM_RIGHT);
			getChildren().add(date);
		}
		
		/**
		 * Sets the date.
		 *
		 * @param title the new date
		 */
		public void setDate(String title) {
			this.date.setText(title);
		}
	}
}
