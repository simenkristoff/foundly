package foundly.ui.control.form;

import foundly.ui.control.validator.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * The Class Form. A VBox-container that holds an observable list of validation inputs. This class
 * is to be used in cases where you need to submit required/validated content.
 * 
 * @see VBox
 * @see Validator
 */
public class Form extends VBox {

  public ObservableList<Validator> inputFields = FXCollections.observableArrayList();
  boolean validForm;

  /**
   * Instantiates a new form with children.
   *
   * @param children the children
   */
  public Form(Node... children) {
    super();
    getChildren().addAll((Node[]) children);
  }

  /**
   * Sets an input and add it to the observable list.
   *
   * @param input the new input
   */
  public void setInput(Validator input) {
    inputFields.add(input);
  }

  /**
   * Sets a list of inputs and adds them to the observable list.
   *
   * @param inputs the new inputs
   */
  public void setInputs(Validator... inputs) {
    inputFields.addAll(inputs);
  }

  /**
   * Checks if all observed inputs are valid.
   *
   * @return true, if all inputs are valid
   */
  public boolean isValid() {
    validForm = true;
    inputFields.forEach(input -> {
      if (!input.isValid()) {
        validForm = false;
      }
    });
    return validForm;
  }

}
