package foundly.ui.control.validator;

/**
 * The Interface Validator. Holds functions to be used by validation-fields.
 */
public interface Validator {

  /**
   * Checks if input is valid.
   *
   * @return true, if input is valid
   */
  public boolean isValid();
}
