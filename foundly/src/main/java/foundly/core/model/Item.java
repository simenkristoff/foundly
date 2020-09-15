package foundly.core.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import javafx.scene.image.Image;

/**
 * The Class Item.
 * Model-object used to insert and retrieve data
 * from database
 */
public class Item extends Model{

	@DBTable(columnName="id", func="getId")
	protected Integer id;
	
	@DBTable(columnName="name", func="getName")
	private String name;
	
	@DBTable(columnName="description", func="getDescription")
	private String description;
	
	@DBTable(columnName="image", func="getImageBlob")
	private Blob imageBlob;
	
	private Image image;
	
	/**
	 * Instantiates a new item.
	 *
	 * @param id the id
	 * @param name the name
	 * @param description the description
	 * @param imageBlob the image blob
	 */
	public Item(Integer id, String name, String description, Blob imageBlob) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageBlob = imageBlob;
	}
	
	
	/**
	 * Instantiates a new item.
	 *
	 * @param id the id
	 * @param name the name
	 * @param description the description
	 * @param image the image
	 */
	public Item(Integer id, String name, String description, InputStream inputStream) {
		this.id = id;
		this.name = name;
		this.description = description;
		try {
			this.imageBlob = new SerialBlob(inputStream.readAllBytes());
		} catch (SQLException | IOException e) {
			this.imageBlob = null;
			e.printStackTrace();
		}

	}

	/**
	 * Instantiates a new item.
	 *
	 * @param rs the rs
	 * @throws SQLException the SQL exception
	 */
	public Item(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.description = rs.getString("description");
		this.imageBlob = rs.getBlob("image");
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the image blob.
	 *
	 * @return the image blob
	 */
	public Blob getImageBlob() {
		return imageBlob;
	}

	/**
	 * Sets the image blob.
	 *
	 * @param imageBlob the new image blob
	 */
	public void setImageBlob(Blob imageBlob) {
		this.imageBlob = imageBlob;
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage() {
		if(this.image == null && this.imageBlob != null) {
			try {
				this.image = new Image(this.imageBlob.getBinaryStream());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", image=" + imageBlob + "]";
	}
	
}
