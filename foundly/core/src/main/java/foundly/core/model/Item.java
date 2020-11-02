package foundly.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import foundly.core.json.ItemDeserializer;
import foundly.core.json.ItemSerializer;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Item. Item's holds information about a Lost- or Found-post.
 */
@Entity
@Table(name = "items")
@JsonDeserialize(using = ItemDeserializer.class)
@JsonSerialize(using = ItemSerializer.class)
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

  /**
   * Instantiates a new empty item.
   */
  public Item() {

  }

  /**
   * Instantiates a new Item with Title, Description, State, Email, Phone-number and Image.
   *
   * @param title the title
   * @param description the description
   * @param state the state
   * @param email the email
   * @param phone the phone
   * @param image the image
   */
  public Item(String title, String description, State state, String email, String phone,
      String image) {
    this.title = title;
    this.description = description;
    this.state = state;
    this.email = email;
    this.phone = phone;
    this.image = image;
  }

  /**
   * Instantiates a new Item with Title, Description, State, Email, Phone-number, Image and date of
   * creation.
   *
   * @param title the title
   * @param description the description
   * @param state the state
   * @param email the email
   * @param phone the phone
   * @param image the image
   * @param date the date
   */
  public Item(String title, String description, State state, String email, String phone,
      String image, LocalDateTime date) {
    this.title = title;
    this.description = description;
    this.state = state;
    this.email = email;
    this.phone = phone;
    this.image = image;
    this.date = date;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public long getId() {
    return id;
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
   * Gets the title.
   *
   * @return the title
   */
  public String getTitle() {
    return this.title;
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
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return this.description;
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
   * Sets the image.
   *
   * @param image the new image
   */
  public void setImage(String image) {
    this.image = image;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the email.
   *
   * @param email the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the phone.
   *
   * @return the phone
   */
  public String getPhone() {
    return this.phone;
  }

  /**
   * Sets the phone.
   *
   * @param phone the new phone
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Gets the image.
   *
   * @return the image
   */
  public String getImage() {
    return this.image;
  }

  /**
   * Sets the date.
   *
   * @param date the new date
   */
  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  /**
   * Gets the date.
   *
   * @return the date
   */
  @JsonValue
  public LocalDateTime getDate() {
    return this.date;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Item [id=" + id + ", title=" + title + ", description=" + description + ", state="
        + state + ", email=" + email + ", phone=" + phone + ", image=" + image + ", date=" + date
        + "]";
  }

  /**
   * The Enum State. Indicates whether the Item is LOST or FOUND.
   */
  public enum State {
    @JsonProperty("LOST")
    LOST("MISTET"), @JsonProperty("FOUND")
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
     * Gets the value.
     * 
     * @return the value
     */
    @JsonValue
    public String getValue() {
      return value;
    }

    /**
     * Checks if the given String matches either LOST or FOUND, and returns the corresponding State.
     *
     * @param stateStr the string to match
     * @return the matched State
     */
    @JsonCreator
    public static State fromString(String stateStr) {
      if (stateStr != null) {
        for (State state : State.values()) {
          if (stateStr.equalsIgnoreCase(state.toString())) {
            return state;
          }
        }
      }
      return null;
    }
  }
}
