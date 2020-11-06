package foundly.ui.control.form;

import foundly.ui.control.NumberField;
import foundly.ui.control.validator.AbstractValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * The Class FormPatternField. Validates user input by matching it with a regex-pattern.
 * 
 * @see AbstractValidator
 * @see TextInputControl
 * @see Matcher
 * @see Pattern
 */
public class FormPatternField extends AbstractValidator<TextInputControl> {

  private Pattern pattern;
  private Matcher matcher;
  private boolean required = true;
  private String regex;

  /**
   * Instantiates a new {@code FormPatternField} with a required boolean and a predefined pattern to
   * validate.
   *
   * @param required the field is required or not {true|false}
   * @param patternType the {@link PatternType} to validate input against
   */
  public FormPatternField(boolean required, PatternType patternType) {
    super(patternType.getControl());
    this.required = required;
    this.regex = patternType.getRegex();
    this.message = patternType.getMessage();
  }

  /**
   * Instantiates a new {@code FormPatternField} with a required boolean and a custom pattern to
   * validate.
   *
   * @param required the field is required or not {true|false}
   * @param regex the regex-pattern to validate input against
   */
  public FormPatternField(boolean required, String regex) {
    super(new TextField());
    this.required = required;
    this.regex = regex;
  }

  /**
   * Checks if input is valid.
   *
   * @return true, if input is valid
   */
  public boolean isValid() {
    pattern = Pattern.compile(regex);
    matcher = pattern.matcher(this.control.getText());
    if (!matcher.matches() && this.required) {
      this.setInvalid();
      return false;
    }

    this.reset();
    return true;
  }

  /**
   * The Enum PatternType. Predefined Enums with a input-field, regex-pattern for validation and a
   * error-message.
   * 
   * @see TextInputControl
   */
  public enum PatternType {
    EMAIL(TextField.class, "^(.+)@(.+)$", "Ugyldig e-mail adresse"),
    PHONE(NumberField.class, "^\\d{8}$", "Ugyldig telefonnummer");

    private Class<? extends TextInputControl> control;
    private String regex;
    private String message;

    /**
     * Instantiates a new {@code PatternType}.
     *
     * @param control the input-field to validate
     * @param regex the regex-pattern to match input against
     * @param message the error-message if validation fails
     */
    private PatternType(Class<? extends TextInputControl> control, String regex, String message) {
      this.control = control;
      this.regex = regex;
      this.message = message;
    }

    /**
     * Gets the input-field.
     *
     * @return the input-field
     */
    public TextInputControl getControl() {
      try {
        return this.control.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
        return null;
      }
    }

    /**
     * Gets the regex-pattern.
     *
     * @return the regex-pattern
     */
    public String getRegex() {
      return this.regex;
    }

    /**
     * Gets the error-message.
     *
     * @return the error-message
     */
    public String getMessage() {
      return this.message;
    }
  }

}
