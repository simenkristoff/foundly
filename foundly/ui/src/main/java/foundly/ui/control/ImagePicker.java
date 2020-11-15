package foundly.ui.control;

import foundly.ui.App;
import java.io.File;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * The Class ImagePicker. ImagePicker, used to select images from local storage.
 */
public class ImagePicker extends VBox {

  final FileChooser.ExtensionFilter imageFilter =
      new FileChooser.ExtensionFilter("Select Image", "*.png", "*.jpg");
  final FileChooser fc = new FileChooser();
  final ImageView imageView = new ImageView();
  final TextField pathholder = new TextField();
  final BorderPane imageViewWrapper = new BorderPane();

  private Image image;
  private File selectedFile;
  private String defaultPath = "Velg bilde";

  /**
   * Instantiates a new image picker.
   */
  public ImagePicker() {
    initialize();
  }

  /**
   * Instantiates a new image picker with prompt text.
   *
   * @param prompt the prompt text
   */
  public ImagePicker(String prompt) {
    defaultPath = prompt;
    initialize();
  }

  /**
   * Adds required components to the image picker.
   */
  private void initialize() {
    getStyleClass().add("image-picker");
    imageViewWrapper.setMinSize(100, 100);
    imageViewWrapper.setPrefSize(150, 150);
    imageViewWrapper.setMaxSize(ImagePicker.USE_PREF_SIZE, ImagePicker.USE_PREF_SIZE);
    imageViewWrapper.getStyleClass().add("image-view-wrapper");
    BorderPane.setAlignment(imageView, Pos.CENTER);

    setPath(defaultPath);
    pathholder.setEditable(false);
    pathholder.maxWidthProperty().bind(imageViewWrapper.widthProperty());
    pathholder.getStyleClass().add("pathholder");
    pathholder.getStylesheets().add(this.getUserAgentStylesheet());

    imageView.setPreserveRatio(true);
    imageView.fitWidthProperty().bind(imageViewWrapper.widthProperty());
    imageView.fitHeightProperty().bind(imageViewWrapper.heightProperty());

    fc.getExtensionFilters().add(this.imageFilter);

    imageViewWrapper.setOnMouseClicked(event -> {
      selectedFile = fc.showOpenDialog(this.getScene().getWindow());
      try {
        String path = selectedFile.toURI().toString();
        image = new Image(path);
        imageView.setImage(image);
        setPath(path);
      } catch (NullPointerException e) {
        imageView.setImage(null);
        setPath(defaultPath);
      }
    });
    imageViewWrapper.getChildren().add(imageView);
    getChildren().addAll(imageViewWrapper, pathholder);
    this.setAlignment(Pos.CENTER);
  }

  /**
   * Path text property.
   *
   * @return the string property
   */
  public final StringProperty pathTextProperty() {
    return pathholder.textProperty();
  }

  /**
   * Gets the path of the selected image.
   *
   * @return the path
   */
  public final String getPath() {
    return pathholder.getText();
  }

  /**
   * Sets the path.
   *
   * @param value the new path
   */
  private final void setPath(String value) {
    pathholder.setText(value);
  }

  /**
   * Gets the name of the image.
   *
   * @return the name
   */
  public String getImageName() {
    if (this.getSelectedFile() != null) {
      return this.selectedFile.getName();
    }
    return null;
  }

  /**
   * Gets the selected file.
   *
   * @return the selected file
   */
  public File getSelectedFile() {
    return this.selectedFile;
  }

  /**
   * Gets the stylesheet.
   *
   * @return the user agent stylesheet
   */
  public String getUserAgentStylesheet() {
    return App.class.getResource("css/components/image-picker.css").toExternalForm();
  }
}
