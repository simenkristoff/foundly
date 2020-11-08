package foundly.ui.control.validator;

import foundly.ui.App;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The Class AbstractValidator is an implementation of the interface Validator. This
 * validation-component is displayed as a container that holds an input-field for user-input and an
 * error-field to display errors related to invalidated input.
 *
 * @param <T> the generic type of TextInputControl
 * 
 * @see TextInputControl
 * @see VBox
 * @see Validator
 */
public abstract class AbstractValidator<T extends TextInputControl> extends VBox
    implements Validator {

  protected T control;
  protected Text error;
  protected String message;

  /**
   * Instantiates a new {@code AbstractValidator} with an input-control.
   *
   * @param control the control
   */
  public AbstractValidator(T control) {
    this.control = control;
    this.error = new Text();
    this.error.getStyleClass().add("error");
    this.getChildren().addAll(error, control);
    this.getStylesheets().add(getUserAgentStylesheet());
  }

  /**
   * Check if input-control is valid.
   *
   * @return true, if input is valid
   */
  public abstract boolean isValid();

  /**
   * Sets the prompt text.
   *
   * @param value the new prompt text
   */
  public void setPromptText(String value) {
    this.control.setPromptText(value);
  }

  /**
   * Gets the text from the Input-field.
   *
   * @return the text
   */
  public String getText() {
    return this.control.getText();
  }

  /**
   * Gets the the input-control.
   *
   * @return the input-control
   */
  public T getControl() {
    return this.control;
  }

  /**
   * Gets the error message.
   *
   * @return message the error message
   */
  public String getErrorMessage() {
    return this.message;
  }

  /**
   * Sets the error message.
   *
   * @param message the new error message
   */
  public void setErrorMessage(String message) {
    this.message = message;
  }

  /**
   * Sets class-style and message if input is invalid.
   */
  protected void setInvalid() {
    if (!this.control.getStyleClass().contains("error")) {
      this.control.getStyleClass().add("error");
    }
    this.error.setText(message);
  }

  /**
   * Resets class-style and message.
   */
  protected void reset() {
    this.control.getStyleClass().remove("error");
    this.error.setText(null);
  }

  /**
   * Gets the stylesheet.
   *
   * @return the user agent stylesheet
   */
  public String getUserAgentStylesheet() {
    return App.class.getResource("css/components/form-control.css").toExternalForm();
  }
}
