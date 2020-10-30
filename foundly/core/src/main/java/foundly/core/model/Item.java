package foundly.core.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import foundly.core.json.ItemDeserializer;
 
@Entity
@Table(name = "items")
@JsonDeserialize(using = ItemDeserializer.class)
public class Item {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	  
	@Column(name = "title")
	private String title;
	  
	@Column(name = "description")
	private String description;
	  
	@Enumerated(EnumType.STRING)
	private State state;
	  
	@Column(name = "email")
	private String email;
	  
	@Column(name = "phone")
	private String phone;
	  
	@Column(name = "image")
	private String image;
	  
	@Column(name = "date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;
	 
	public Item() {}

	public Item(String title, String description, State state, String email, String phone, String image) {
		this.title = title;
		this.description = description;
		this.state = state;
		this.email = email;
		this.phone = phone;
		this.image = image;
	}
  
	public Item(String title, String description, State state, String email, String phone, String image, LocalDateTime date) {
		this.title = title;
		this.description = description;
		this.state = state;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.date = date;
	}
	  
	public long getId() {
		return id;
	}
	 
	public void setTitle(String title) {
		this.title = title;
	}
	 
	public String getTitle() {
		return this.title;
	}
	  
	public void setDescription(String description) {
		this.description = description;
	}
	 
	public String getDescription() {
		return this.description;
	}
	 
	public State getState() {
		return this.state;
	}
	 
	public void setState(State state) {
		this.state = state;
	}
	  
	public void setImage(String image) {
		this.image = image;
	}
	  
	public String getEmail() {
		return this.email;
	}
	  
	public void setEmail(String email) {
		this.email = email;
	}
	  
	public String getPhone() {
		return this.phone;
	}
	  
	public void setPhone(String phone) {
		this.phone = phone;
	}
	  
	public String getImage() {
		return this.image;
	}
	  
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	  
	@JsonValue
	public LocalDateTime getDate() {
		return this.date;
	}
	 
	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", description=" + description + ", state=" + state + ", email=" + email + ", phone=" + phone + ", image=" + image + ", date=" + date + "]";
	}
	  
	public enum State {
		@JsonProperty("LOST")
		LOST("MISTET"),
		@JsonProperty("FOUND")
		FOUND("FUNNET");
		  
		private String value;
			
		/**
		 * Instantiates a new State.
		 *
		 * @param value the string value of the state
		 */
		private State(String value) {
			this.value = value;
		}
		
		/**
		 * Gets the value
		 * 
		 * @return the value
		 */
		@JsonValue
		public String getValue() {
			return value;
		}
		
		@JsonCreator
		public static State fromString(String stateStr) {
			if(stateStr != null) {
				for(State state : State.values()) {
					if(stateStr.equalsIgnoreCase(state.toString())) {
						return state;
					}
				}
			}
			return null;
		}
	}
}
