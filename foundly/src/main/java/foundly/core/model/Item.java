package foundly.core.model;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.image.Image;


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
	
	public Item(Integer id, String name, String description, Blob imageBlob) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageBlob = imageBlob;
	}

	public Item(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.description = rs.getString("description");
		this.imageBlob = rs.getBlob("image");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Blob getImageBlob() {
		return imageBlob;
	}

	public void setImageBlob(Blob imageBlob) {
		this.imageBlob = imageBlob;
	}
	
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

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", image=" + imageBlob + "]";
	}
	
}
