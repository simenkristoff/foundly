package foundly.core.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
	
	@DBTable(columnName="title", func="getTitle")
	private String title;
	
	@DBTable(columnName="description", func="getDescription")
	private String description;
	
	@DBTable(columnName="image", func="getImageBlob")
	private Blob imageBlob;
	
	@DBTable(columnName="created_at", func="getDateCreated")
	private Date dateCreated;
	
	private Image image;
	
	/**
	 * Instantiates a new item.
	 *
	 * @param id the id
	 * @param title the title
	 * @param description the description
	 * @param imageBlob the image blob
	 */
	public Item(Integer id, String title, String description, Blob imageBlob) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageBlob = imageBlob;
		this.dateCreated = new Date();
	}
	
	
	/**
	 * Instantiates a new item.
	 *
	 * @param id the id
	 * @param title the title
	 * @param description the description
	 * @param image the image
	 */
	public Item(Integer id, String title, String description, InputStream inputStream) {
		this.id = id;
		this.title = title;
		this.description = description;
		try {
			this.imageBlob = new SerialBlob(inputStream.readAllBytes());}
			catch (SQLException | IOException e) {this.imageBlob = null; e.printStackTrace();
		}
		this.dateCreated = new Date();
	}

	/**
	 * Instantiates a new item.
	 *
	 * @param rs the rs
	 * @throws SQLException the SQL exception
	 */
	public Item(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.title = rs.getString("title");
		this.description = rs.getString("description");
		this.imageBlob = rs.getBlob("image");
		this.dateCreated = rs.getTimestamp("created_at");
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * Gets the date the item was created.
	 *
	 * @return the date of creation
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", image=" + imageBlob + "]";
	}
	
}
