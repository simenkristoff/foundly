package foundly.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ResponseMessageTest {

  private String message = "Test message";
  
  @Test
  public void constructorTest() {
    ResponseMessage resMsg = new ResponseMessage(message);
    String toString = "ResponseMessage [message=" + message + "]";
    assertEquals(resMsg.toString(), toString);
  }
  
  @Test
  public void gettersSettersTest() {
    ResponseMessage resMsg = new ResponseMessage(message);
    String newMsg = "New message";
    resMsg.setMessage(newMsg);
    assertEquals(resMsg.getMessage(), newMsg);
  }
  
}
