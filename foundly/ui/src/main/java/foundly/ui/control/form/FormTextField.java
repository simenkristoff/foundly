package foundly.ui.control.form;

import foundly.ui.control.validator.AbstractValidator;
import javafx.scene.control.TextField;

/**
 * The Class FormTextField. Validates user input in a {@link TextField}.
 * 
 * @see AbstractValidator
 * @see TextField
 */
public class FormTextField extends AbstractValidator<TextField> {

  private boolean required = true;
  private int minLen = 0;

  /**
   * Instantiates a new {@code FormTextField} with a required boolean and minimum length.
   *
   * @param required the field is required or not {true|false}
   * @param minLen the minimum length of characters in the input
   */
  public FormTextField(boolean required, int minLen) {
    super(new TextField());
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
