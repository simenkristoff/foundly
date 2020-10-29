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
	
	@DBTable(columnName="state", func="getStateAsString")
	protected State state;
	
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
	 */
	public Item(Integer id, State state, String title, String description) {
		this.id = id;
		this.state = state;
		this.title = title;
		this.description = description;
		this.imageBlob = null;
		this.dateCreated = new Date();
	}
	
	/**
	 * Instantiates a new item.
	 *
	 * @param id the id
	 * @param title the title
	 * @param description the description
	 * @param imageBlob the image blob
	 */
	public Item(Integer id, State state, String title, String description, Blob imageBlob) {
		this.id = id;
		this.state = state;
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
	public Item(Integer id, State state, String title, String description, InputStream inputStream) {
		this.id = id;
		this.state = state;
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
		readStateFromRs(rs);
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
		return this.id;
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
	 * Gets the state as a String.
	 *
	 * @return the state as String
	 */
	public String getStateAsString() {
		return this.state.toString();
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public State getState() {
		return this.state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
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
		return this.description;
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
		return this.imageBlob;
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
		return this.image;
	}
	
	/**
	 * Gets the date the item was created.
	 *
	 * @return the date of creation
	 */
	public Date getDateCreated() {
		return new Date(dateCreated.getTime());
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", state=" + state +", title=" + title + ", image=" + imageBlob + "]";
	}
	
	
	/**
	 * Sets the correct State of Items stored in database
	 *
	 * @param rs the Item fetched from database
	 *
	 * @throws SQLException 
	 */
	private void readStateFromRs(ResultSet rs) throws SQLException {
		String state = rs.getString("state");
		switch (state) {
			case "FOUND":
				this.state = State.FOUND;
				break;
			case "LOST":
				this.state = State.LOST;
				break;
			default:
				this.state = State.FOUND;
				break;
		}	
	}
	
	public enum State {
		LOST("MISTET") {
			public String toString() {
				return "LOST";
			}
		},
		FOUND("FUNNET"){
			public String toString() {
				return "FOUND";
			}
		};
		
		private String value;
		
		
		/**
		 * Instantiates a new State.
		 *
		 * @param value the string value of the state
		 */
		private State(final String value) {
			this.value = value;
		}
		
	    /**
		 * Gets the value
		 * 
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		
		
	}
	
}
