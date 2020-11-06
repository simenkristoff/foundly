package foundly.ui.control.form;

import foundly.ui.control.NumberField;
import foundly.ui.control.validator.AbstractValidator;

/**
 * The Class FormNumberField. Validates user input in a {@link NumberField}.
 * 
 * @see AbstractValidator
 * @see NumberField
 */
public class FormNumberField extends AbstractValidator<NumberField> {

  private boolean required = true;
  private int minLen = 0;

  /**
   * Instantiates a new {@code FormNumberField} with a required boolean and minimum length.
   *
   * @param required the field is required or not {true|false}
   * @param minLen the minimum length of characters in the input
   */
  public FormNumberField(boolean required, int minLen) {
    super(new NumberField());
    this.required = required;
    this.minLen = minLen;
    this.message = "Du må ha minst " + minLen + " tall i dette feltet";
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
