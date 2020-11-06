package foundly.ui.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * The Class NumberField. Extension of input component TextField, which only allows user to input
 * numbers.
 * 
 * @see TextField
 */
public class NumberField extends TextField {

  /**
   * Instantiates a new {@code NumberField} with empty text content.
   */
  public NumberField() {
    super();
    textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> obs, String ov, String nv) {
        if (!nv.matches("\\d*")) {
          setText(nv.replaceAll("[^\\d]", ""));
        }
      }
    });
  }
}
