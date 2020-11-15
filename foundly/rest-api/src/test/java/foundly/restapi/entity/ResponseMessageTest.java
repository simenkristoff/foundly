package foundly.restapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Class ResponseMessage.
 */
public class ResponseMessageTest {

  private String message = "Test message";
  
  /**
   * Test the constructor.
   */
  @Test
  @DisplayName("Test constructor")
  public void constructorTest() {
    ResponseMessage resMsg = new ResponseMessage(message);
    String toString = "ResponseMessage [message=" + message + "]";
    assertEquals(resMsg.toString(), toString);
  }
  
  /**
   * Tests for the getters and setters.
   *
   */
  @Test
  @DisplayName("Test getters and setters")
  public void gettersSettersTest() {
    ResponseMessage resMsg = new ResponseMessage(message);
    String newMsg = "New message";
    resMsg.setMessage(newMsg);
    assertEquals(resMsg.getMessage(), newMsg);
  }
  
}
