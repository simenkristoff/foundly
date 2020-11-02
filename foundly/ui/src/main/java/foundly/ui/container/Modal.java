package foundly.ui.container;

import foundly.ui.App;
import foundly.ui.effect.DepthManager;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class Modal. Modal window, a child window that requires users to interact with it before they
 * can return to operating the parent application.
 * 
 * @param <R> the generic type
 */
public class Modal<R> extends Dialog<R> {

  private final StackPane contentContainer;

  /**
   * Instantiates a new empty modal.
   */
  public Modal() {
    this(null);
  }

  /**
   * Instantiates a new modal with a set stage.
   *
   * @param stage the stage
   */
  public Modal(Stage stage) {

    contentContainer = new StackPane();
    contentContainer.getStyleClass().add("content-container");
    final Node materialNode = DepthManager.createMaterialNode(contentContainer, 2);
    materialNode.setPickOnBounds(false);
    materialNode.addEventHandler(MouseEvent.MOUSE_CLICKED, Event::consume);

    final DialogPane dialogPane = new DialogPane() {
      private boolean performingLayout = false;

      {
        getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = this.lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
      }

      @Override
      protected double computePrefHeight(double width) {
        Window owner = getOwner();
        if (owner != null) {
          return owner.getHeight();
        } else {
          return super.computePrefHeight(width);
        }
      }

      @Override
      protected double computePrefWidth(double height) {
        Window owner = getOwner();
        if (owner != null) {
          return owner.getWidth();
        } else {
          return super.computePrefHeight(height);
        }
      }

      @Override
      public void requestLayout() {
        if (performingLayout) {
          return;
        }
        super.requestLayout();
      }

      @Override
      protected void layoutChildren() {
        performingLayout = true;
        List<Node> managed = getManagedChildren();
        final double width = getWidth();
        double height = getHeight();
        double top = getInsets().getTop();
        double right = getInsets().getRight();
        double left = getInsets().getLeft();
        double bottom = getInsets().getBottom();
        double contentWidth = width - left - right;
        double contentHeight = height - top - bottom;
        for (Node child : managed) {
          layoutInArea(child, left, top, contentWidth, contentHeight, 0, Insets.EMPTY, HPos.CENTER,
              VPos.CENTER);
        }
        performingLayout = false;
      }

      public String getUserAgentStylesheet() {
        return App.class.getResource("css/components/modal.css").toExternalForm();
      }

      @Override
      protected Node createButtonBar() {
        return null;
      }
    };
    dialogPane.getStyleClass().add("modal-overlay");
    dialogPane.setContent(materialNode);
    setDialogPane(dialogPane);
    dialogPane.getScene().setFill(Color.TRANSPARENT);
    if (stage != null) {
      initStyle(StageStyle.TRANSPARENT);
      initOwner(stage);

      dialogPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        if (this.isOverlayClose()) {
          hide();
        }
      });
    }


    getDialogPane().getScene().getWindow().addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ESCAPE) {
        if (!isHideOnEscape()) {
          keyEvent.consume();
        }
      }
    });

  }

  /**
   * Sets the content of the modal.
   *
   * @param content the new content
   */
  public void setContent(Node... content) {
    contentContainer.getChildren().setAll(content);
  }

  /** indicates whether the dialog will close when clicking on the overlay or not. */
  private BooleanProperty overlayClose = new SimpleBooleanProperty(true);

  /**
   * Checks if modal will close if overlay is clicked.
   *
   * @return true, if is overlay close
   */
  public boolean isOverlayClose() {
    return overlayClose.get();
  }

  /**
   * Overlay close property.
   *
   * @return the boolean property
   */
  public BooleanProperty overlayCloseProperty() {
    return overlayClose;
  }

  /**
   * Sets the overlay close. If true, the Modal will close when the overlay is clicked.
   *
   * @param overlayClose the new overlay close
   */
  public void setOverlayClose(boolean overlayClose) {
    this.overlayClose.set(overlayClose);
  }

  /**
   * Sets the size.
   *
   * @param prefWidth the pref width
   * @param prefHeight the pref height
   */
  public void setSize(double prefWidth, double prefHeight) {
    contentContainer.setPrefSize(prefWidth, prefHeight);
  }

  /** The hide on escape. */
  private BooleanProperty hideOnEscape = new SimpleBooleanProperty(this, "hideOnEscape", true);

  /**
   * Sets the hide on escape.
   *
   * @param value the new hide on escape
   */
  public final void setHideOnEscape(boolean value) {
    hideOnEscape.set(value);
  }

  /**
   * Checks if is hide on escape.
   *
   * @return true, if is hide on escape
   */
  public final boolean isHideOnEscape() {
    return hideOnEscape.get();
  }

  /**
   * Hide on escape property.
   *
   * @return the boolean property
   */
  public final BooleanProperty hideOnEscapeProperty() {
    return hideOnEscape;
  }
}
