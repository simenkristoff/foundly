package foundly.ui.container;

import foundly.core.model.Item;
import foundly.ui.App;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The Class ItemCellLayout. Defines the layout for displaying each Item. ItemCellLayout consists of
 * three classes, Header, Body, and Footer. The Layout is structured extends BorderPane, and is
 * structured with the Item's image on the left-hand side, and the Item's content in the center.
 */
public class ItemCellLayout extends BorderPane {

  private Item item;
  private Header header = new Header();
  private Body body = new Body();
  private Footer footer = new Footer();

  private Image image;
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
    setMargin(contentWrapper, new Insets(0, 0, 0, 10));
  }

  /**
   * Initialize. Loads data from the given Item into it's dedicated fields.
   */
  private void initialize() {
    this.getStyleClass().add("itemCell");

    header.setTitle(item.getTitle());
    header.setState(item.getState().getValue());

    body.setDescription(item.getDescription());

    footer.setEmail(item.getEmail());
    footer.setPhone(item.getPhone());
    footer.setDate(item.getDate().format(App.DATEPATTERN));

    imageViewWrapper.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
    imageViewWrapper.setPrefSize(100, 100);
    imageViewWrapper.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
    imageViewWrapper.setAlignment(Pos.CENTER);

    imageView.setPreserveRatio(true);
    imageView.fitWidthProperty().bind(imageViewWrapper.widthProperty());
    imageView.fitHeightProperty().bind(imageViewWrapper.heightProperty());

    // Will URL-encode the filename if it contains unsupported characters
    image = new Image(
        App.API_URL + "/img/" + URLEncoder.encode(item.getImage(), StandardCharsets.UTF_8));
    imageView.setImage(image);
  }

  /**
   * Gets the stylesheet.
   *
   * @return the user agent stylesheet
   */
  public String getUserAgentStylesheet() {
    return App.class.getResource("css/components/itemCell.css").toExternalForm();
  }

  /**
   * The Class Header.
   */
  private static class Header extends AnchorPane {

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
      switch (state.toLowerCase()) {
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
  private static class Body extends VBox {

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
  private static class Footer extends AnchorPane {

    private Text contactTitle = new Text("Kontakt");
    private Text email = new Text();
    private Text phone = new Text();
    private Text date = new Text();

    private HBox contactWrapper = new HBox(email, phone);
    private HBox dateWrapper = new HBox(date);

    /**
     * Instantiates a new footer.
     */
    public Footer() {
      contactTitle.getStyleClass().add("subtitle");
      date.getStyleClass().add("date");

      contactWrapper.setSpacing(10);
      contactWrapper.setAlignment(Pos.BOTTOM_LEFT);
      dateWrapper.setSpacing(10);
      dateWrapper.setAlignment(Pos.BOTTOM_RIGHT);
      getChildren().addAll(contactTitle, contactWrapper, dateWrapper);

      setLeftAnchor(contactTitle, 0.0);
      setBottomAnchor(contactTitle, contactWrapper.getSpacing() + 1.5);
      setLeftAnchor(contactWrapper, 0.0);
      setBottomAnchor(contactWrapper, 0.0);
      setRightAnchor(dateWrapper, 0.0);
      setBottomAnchor(dateWrapper, 0.0);
    }

    /**
     * Sets the email.
     *
     * @param email the new email-address
     */
    private void setEmail(String email) {
      this.email.setText("E-mail: " + email);
    }

    /**
     * Sets the phone.
     *
     * @param phone the new phone number
     */
    private void setPhone(String phone) {
      this.phone.setText("Tlf: " + phone);
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
