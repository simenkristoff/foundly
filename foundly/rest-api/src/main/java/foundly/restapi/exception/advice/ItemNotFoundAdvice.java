package foundly.restapi.exception.advice;

import foundly.restapi.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ItemNotFoundAdvice. Returns a response if an ItemNotFoundException is thrown.
 */
@ControllerAdvice
public class ItemNotFoundAdvice {

  /**
   * Handler for ItemNotFoundException. Will Set the Http-status to NOT FOUND and return an
   * advice-message.
   *
   * @param ex the exception
   * @return the advice-message
   */
  @ResponseBody
  @ExceptionHandler(ItemNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String itemNotFoundHandler(ItemNotFoundException ex) {
    System.err.println(HttpStatus.NOT_FOUND.toString() + ": " + ex);
    return ex.getMessage();
  }
}
