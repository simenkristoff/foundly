package foundly.ui.controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import foundly.App;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * The Class ImagePicker.
 * ImagePicker, used to select images from local storage.
 */
public class ImagePicker extends VBox {
	
	final FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Select Image", "*.png", "*.jpg");
	final FileChooser fc = new FileChooser();
	final ImageView imageView = new ImageView();
	final TextField pathholder = new TextField();
	final BorderPane imageViewWrapper = new BorderPane();
	
	private Image image;
	private File selectedFile;
	private InputStream inputStream;
	private String default_path = "Velg bilde";
	
	/**
	 * Instantiates a new image picker.
	 */
	public ImagePicker() {
		initialize();
	}
	
	/**
	 * Instantiates a new image picker.
	 *
	 * @param name the name
	 */
	public ImagePicker(String prompt) {
		default_path = prompt;
		initialize();
	}
	
	/**
	 * Initialize.
	 */
	private void initialize() {
		getStyleClass().add("image-picker");
		imageViewWrapper.setMinSize(100, 100);
		imageViewWrapper.setPrefSize(150, 150);
		imageViewWrapper.setMaxSize(ImagePicker.USE_PREF_SIZE, ImagePicker.USE_PREF_SIZE);
		imageViewWrapper.getStyleClass().add("image-view-wrapper");
		BorderPane.setAlignment(imageView, Pos.CENTER);
		
		setPath(default_path);
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
			} catch(NullPointerException e) {
				imageView.setImage(null);
				setPath(default_path);
			}
		});
		imageViewWrapper.getChildren().add(imageView);
		getChildren().addAll(imageViewWrapper, pathholder);
		this.setAlignment(Pos.CENTER);
	}

    public final StringProperty pathtTextProperty() { return pathholder.textProperty(); }
    public final String getPath() { return pathholder.getText(); }
    public final void setPath(String value) { pathholder.setText(value); }
	
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
	 * Gets the image as blob.
	 *
	 * @return the image as blob
	 */
	public Blob getImageAsBlob() {
		if(selectedFile != null) {
			try {
				inputStream = new FileInputStream(selectedFile);
				return new SerialBlob(inputStream.readAllBytes());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getUserAgentStylesheet() {
		return App.class.getResource("css/components/image-picker.css").toExternalForm();
	}
}
