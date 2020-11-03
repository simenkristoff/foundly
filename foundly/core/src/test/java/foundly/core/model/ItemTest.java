package foundly.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import foundly.core.model.Item.State;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Class Item.
 */
public class ItemTest {

  private String title, description, email, phone, image;
  private LocalDateTime dateTime;

  /**
   * Setup before each test.
   */
  @BeforeEach
  public void setup() {
    this.title = "Mistet nøkkel";
    this.description = "Sist sett på R2";
    this.email = "simen.kristoffersen98@gmail.com";
    this.phone = "90360922";
    this.image = "default.png";
    this.dateTime = LocalDateTime.now();
  }

  /**
   * Test for constructor without datetime.
   */
  @Test
  @DisplayName("Test constructor without datetime")
  public void constructorTest() {
    Item item = new Item(title, description, State.FOUND, email, phone, image);
    String toString = "Item [id=" + 0 + ", title=" + title + ", description=" + description
        + ", state=" + State.FOUND + ", email=" + email + ", phone=" + phone + ", image=" + image
        + ", date=" + null + "]";
    assertEquals(item.toString(), toString);
  }

  /**
   * Test for constructor with datetime.
   */
  @Test
  @DisplayName("Test constructor with datetime")
  public void constructor2Test() {
    Item item = new Item(title, description, State.FOUND, email, phone, image, dateTime);
    String toString = "Item [id=" + 0 + ", title=" + title + ", description=" + description
        + ", state=" + State.FOUND + ", email=" + email + ", phone=" + phone + ", image=" + image
        + ", date=" + dateTime + "]";
    assertEquals(item.toString(), toString);
  }

  /**
   * Tests for the getters and setters.
   *
   */
  @Test
  @DisplayName("Test getters and setters")
  public void gettersSettersTest() {
    Item item = new Item();

    assertEquals(item.getId(), 0);

    item.setTitle(title);
    assertEquals(item.getTitle(), title);

    item.setDescription(description);
    assertEquals(item.getDescription(), description);

    item.setState(State.FOUND);
    assertEquals(item.getState(), State.FOUND);

    item.setEmail(email);
    assertEquals(item.getEmail(), email);

    item.setPhone(phone);
    assertEquals(item.getPhone(), phone);

    item.setImage(image);
    assertEquals(item.getImage(), image);

    item.setDate(this.dateTime);
    assertEquals(item.getDate(), this.dateTime);
  }

  /**
   * Tests the value for each state.
   */
  @Test
  @DisplayName("Test values for each state")
  public void stateValueTest() {
    State lostState = State.LOST;
    String lostVal = lostState.getValue();
    assertEquals(lostVal, "MISTET");

    State foundState = State.LOST;
    String foundVal = foundState.getValue();
    assertEquals(foundVal, "MISTET");
  }

  /**
   * The enum State should be able to return the correct State from a string corresponding to a
   * State value.
   */
  @Test
  @DisplayName("Test String to State")
  public void stateFromStringTest() {
    String foundToState = "FOUND";
    assertEquals(State.fromString(foundToState), State.FOUND);

    String lostToState = "LOST";
    assertEquals(State.fromString(lostToState), State.LOST);

    String nullToNull = "";
    assertEquals(State.fromString(nullToNull), null);
  }

}
