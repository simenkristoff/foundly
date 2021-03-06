package foundly.ui.container;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The Class ModalLayout. Defines the layout for parent {@link Modal}
 */
public class ModalLayout extends VBox {

  private StackPane header = new StackPane();
  private StackPane body = new StackPane();
  private HBox actions = new HBox();

  /**
   * Instantiates a new modal layout.
   */
  public ModalLayout() {
    actions.setAlignment(Pos.CENTER);
    actions.setSpacing(10);
    body.setPadding(new Insets(10));
    VBox.setVgrow(body, Priority.ALWAYS);
    this.setPadding(new Insets(10));
    setSpacing(10);
    getChildren().setAll(header, body, actions);
  }

  /**
   * Gets the header.
   *
   * @return the header
   */
  public ObservableList<Node> getHeader() {
    return header.getChildren();
  }

  /**
   * Sets the header.
   *
   * @param titleContent the new header
   */
  public void setHeader(Node... titleContent) {
    this.header.getChildren().setAll(titleContent);
  }

  /**
   * Gets the body.
   *
   * @return the body
   */
  public ObservableList<Node> getBody() {
    return body.getChildren();
  }

  /**
   * Sets the body.
   *
   * @param body the new body
   */
  public void setBody(Node... body) {
    this.body.getChildren().setAll(body);
  }

  /**
   * Gets the actions.
   *
   * @return the actions
   */
  public ObservableList<Node> getActions() {
    return actions.getChildren();
  }

  /**
   * Sets the actions.
   *
   * @param actions the new actions
   */
  public void setActions(Node... actions) {
    this.actions.getChildren().setAll(actions);
  }

  /**
   * Sets the actions.
   *
   * @param actions the new actions
   */
  public void setActions(List<? extends Node> actions) {
    this.actions.getChildren().setAll(actions);
  }
}
