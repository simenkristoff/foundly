package foundly.core.containers;

import java.util.List;

import foundly.core.effects.DepthManager;
import foundly.ui.App;
import javafx.beans.InvalidationListener;
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
 * The Class Modal.
 * Modal window, a child window that requires users to interact with it 
 * before they can return to operating the parent application.
 * @param <R> the generic type
 */
public class Modal<R> extends Dialog<R> {
	
	private final StackPane contentContainer;
	private InvalidationListener widthListener;
    private InvalidationListener heightListener;
    private InvalidationListener xListener;
    private InvalidationListener yListener;
    
    /**
     * Instantiates a new modal.
     */
    public Modal() {
    	this(null);
    }
    
    /**
     * Instantiates a new modal.
     *
     * @param stage the stage
     */
    @SuppressWarnings("exports")
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
    			System.out.println(closeButton);
    			closeButton.managedProperty().bind(closeButton.visibleProperty());
    			closeButton.setVisible(false);
    		}
    		
    		@Override
    		protected double computePrefHeight(double width) {
    			Window owner = getOwner();
    			if(owner != null) {
    				return owner.getHeight();
    			} else {
    				return super.computePrefHeight(width);
    			}
    		}
    		
    		@Override
    		protected double computePrefWidth(double height) {
    			Window owner = getOwner();
    			if(owner != null) {
    				return owner.getWidth();
    			} else {
    				return super.computePrefHeight(height);
    			}
    		}
    		
    		@Override
    		public void requestLayout() {
    			if(performingLayout) {
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
                    layoutInArea(child, left, top, contentWidth, contentHeight,
                        0, Insets.EMPTY, HPos.CENTER, VPos.CENTER);
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
    	if(stage != null) {
    		initStyle(StageStyle.TRANSPARENT);
    		initOwner(stage);
    		
    		dialogPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
    			if(this.isOverlayClose()) {
    				hide();
    			}
    		});
    		
    		widthListener = observable -> updateWidth();
            heightListener = observable -> updateHeight();
            xListener = observable -> updateX();
            yListener = observable -> updateY();
    	}
    	
    	
    	getDialogPane().getScene().getWindow().addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
    		if(keyEvent.getCode() == KeyCode.ESCAPE) {
    			if(!isHideOnEscape()) {
    				keyEvent.consume();
    			}
    		}
    	});
    	
    }
    
    /**
     * Removes the layout listeners.
     */
    private void removeLayoutListeners() {
        Window stage = getOwner();
        if (stage != null) {
            stage.getScene().widthProperty().removeListener(widthListener);
            stage.getScene().heightProperty().removeListener(heightListener);
            stage.xProperty().removeListener(xListener);
            stage.yProperty().removeListener(yListener);
        }
    }
    
    /**
     * Adds the layout listeners.
     */
    private void addLayoutListeners() {
        Window stage = getOwner();
        if (stage != null) {
            if (widthListener == null) {
                throw new RuntimeException("Owner can only be set using the constructor");
            }
            stage.getScene().widthProperty().addListener(widthListener);
            stage.getScene().heightProperty().addListener(heightListener);
            stage.xProperty().addListener(xListener);
            stage.yProperty().addListener(yListener);
        }
    }

    /**
     * Update layout.
     */
    private void updateLayout() {
        updateX();
        updateY();
        updateWidth();
        updateHeight();
    }

    /**
     * Update height.
     */
    private void updateHeight() {
        Window stage = getOwner();
        setHeight(stage.getScene().getHeight());
    }

    /**
     * Update width.
     */
    private void updateWidth() {
        Window stage = getOwner();
        setWidth(stage.getScene().getWidth());
    }

    /**
     * Update Y.
     */
    private void updateY() {
        Window stage = getOwner();
        setY(stage.getY() + stage.getScene().getY());
    }

    /**
     * Update X.
     */
    private void updateX() {
        Window stage = getOwner();
        setX(stage.getX() + stage.getScene().getX());
    }
    
    /**
     * Sets the content.
     *
     * @param content the new content
     */
    @SuppressWarnings("exports")
	public void setContent(Node... content) {
        contentContainer.getChildren().setAll(content);
    }
    
    /** indicates whether the dialog will close when clicking on the overlay or not. */
    private BooleanProperty overlayClose = new SimpleBooleanProperty(true);

    /**
     * Checks if is overlay close.
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
    @SuppressWarnings("exports")
	public BooleanProperty overlayCloseProperty() {
        return overlayClose;
    }

    /**
     * Sets the overlay close.
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
    @SuppressWarnings("exports")
	public final BooleanProperty hideOnEscapeProperty() {
        return hideOnEscape;
    }
}
