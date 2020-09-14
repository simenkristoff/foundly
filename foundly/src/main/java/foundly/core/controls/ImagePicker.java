package foundly.core.controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class ImagePicker extends Button {
	
	final FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Select Image", "*.png", "*.jpg");
	final FileChooser fc = new FileChooser();
	
	private File file;
	private InputStream inputStream;

	public ImagePicker() {
		this.fc.getExtensionFilters().add(this.imageFilter);
		this.setOnAction(event -> {
			file = fc.showOpenDialog(this.getScene().getWindow());
		});
	}
	
	public ImagePicker(String name) {
		this.setText(name);
		this.fc.getExtensionFilters().add(this.imageFilter);
		this.setOnAction(event -> {
			file = fc.showOpenDialog(this.getScene().getWindow());
		});
	}
	
	public Blob getImageAsBlob() {
		if(file != null) {
			try {
				inputStream = new FileInputStream(file);
				return new SerialBlob(inputStream.readAllBytes());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
