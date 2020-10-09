package foundly.containers;

import java.util.List;

import foundly.model.Item;
import foundly.App;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
		footer.setDate(item.getDateCreated().toLocaleString());
		
		imageViewWrapper.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		imageViewWrapper.setPrefSize(100, 100);
		imageViewWrapper.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		imageViewWrapper.setAlignment(Pos.CENTER);
		
		imageView.setPreserveRatio(true);
		imageView.fitWidthProperty().bind(imageViewWrapper.widthProperty());
		imageView.fitHeightProperty().bind(imageViewWrapper.heightProperty());
		imageView.setImage((item.getImage() != null) ? item.getImage() : defaultImage);
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage() {
		return this.imageView.getImage();
	}
	
	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(Image image) {
		this.imageView.setImage(image);
	}
	
	/**
	 * Gets the header.
	 *
	 * @return image the new image
	 */
	@SuppressWarnings("exports")
	public ObservableList<Node> getHeader(){
		return header.getChildren();
	}
	
	/**
	 * Sets the header.
	 *
	 * @param headerContent the new header
	 */
	@SuppressWarnings("exports")
	public void setHeader(Node... headerContent) {
		this.header.getChildren().setAll(headerContent);
	}
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	@SuppressWarnings("exports")
	public ObservableList<Node> getBody(){
		return body.getChildren();
	}
	
	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	@SuppressWarnings("exports")
	public void setBody(Node... body) {
		this.body.getChildren().setAll(body);
	}
	
	/**
	 * Gets the footer.
	 *
	 * @return the footer
	 */
	@SuppressWarnings("exports")
	public ObservableList<Node> getFooter(){
		return footer.getChildren();
	}
	
	/**
	 * Sets the footer.
	 *
	 * @param footer the new footer
	 */
	@SuppressWarnings("exports")
	public void setFooter(Node... footer) {
		this.footer.getChildren().setAll(footer);
	}
	
	/**
	 * Sets the footer.
	 *
	 * @param footer the new footer
	 */
	@SuppressWarnings("exports")
	public void setFooter(List<? extends Node> footer) {
        this.footer.getChildren().setAll(footer);
    }
	
	public String getUserAgentStylesheet() {
		return App.class.getResource("css/components/itemCell.css").toExternalForm();
	}
	
	/**
	 * The Class Header.
	 */
	private class Header extends AnchorPane {
		
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
		 * Gets the title.
		 *
		 * @return the title
		 */
		public String getTitle() {
			return this.title.getText();
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
		 * Gets the state.
		 *
		 * @return the state
		 */
		public String getState() {
			return this.state.getText();
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
	private class Body extends VBox {
		
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
		 * Gets the description.
		 *
		 * @return the description
		 */
		public String getDescription() {
			return this.description.getText();
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
	private class Footer extends VBox {
		
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
		 * Gets the date.
		 *
		 * @return the date
		 */
		public String getDate() {
			return this.date.getText();
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
