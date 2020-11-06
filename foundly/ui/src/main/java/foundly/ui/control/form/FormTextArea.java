package foundly.ui.control.form;

import foundly.ui.control.validator.AbstractValidator;
import javafx.scene.control.TextArea;

/**
 * The Class FormTextArea. Validates user input in a {@link TextArea}.
 * 
 * @see AbstractValidator
 * @see TextArea
 */
public class FormTextArea extends AbstractValidator<TextArea> {

  private boolean required = true;
  private int minLen = 0;

  /**
   * Instantiates a new {@code FormTextArea} with a required boolean and minimum length.
   *
   * @param required the field is required or not {true|false}
   * @param minLen the minimum length of characters in the input
   */
  public FormTextArea(boolean required, int minLen) {
    super(new TextArea());
    this.required = required;
    this.minLen = minLen;
    this.message = "Du må ha minst " + minLen + " bokstaver i dette feltet";
  }

  /**
   * Checks if input is valid.
   *
   * @return true, if input is valid
   */
  public boolean isValid() {
    if ((this.control.getText().isEmpty() && this.required)
        && this.control.getText().length() < this.minLen) {
      this.setInvalid();
      return false;
    }

    this.reset();
    return true;
  }

}
