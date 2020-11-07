package foundly.restapi.exception.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import foundly.restapi.exception.ItemNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The Class ItemNotFoundAdviceTest.
 */
public class ItemNotFoundAdviceTest {

  /**
   * Testing console-advice on ItemNotFoundExceptions.
   */
  @Test
  @DisplayName("Testing advice for not found Items")
  public void itemNotFoundHandlerTest() {
    ItemNotFoundException exception = new ItemNotFoundException(1L);

    ItemNotFoundAdvice advice = new ItemNotFoundAdvice();
    String adviceMsg = advice.itemNotFoundHandler(exception);

    System.out.println(adviceMsg);
    assertEquals(adviceMsg, "Could not find item with id: 1");
  }
}