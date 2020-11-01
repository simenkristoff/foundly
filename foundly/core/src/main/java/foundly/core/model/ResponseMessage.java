package foundly.core.model;

/**
 * The Class ResponseMessage. Model for message returned by the rest-api.
 */
public class ResponseMessage {

  private String message;

  /**
   * Instantiates a new response message.
   *
   * @param message the message
   */
  public ResponseMessage(String message) {
    this.message = message;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message.
   *
   * @param message the new message
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
