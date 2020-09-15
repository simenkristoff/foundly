package foundly.core.containers;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The Class ModalLayout.
 * Defines the layout for parent {@link Modal}
 */
public class ModalLayout extends VBox {

	private StackPane heading = new StackPane();
	private StackPane body = new StackPane();
	private FlowPane actions = new FlowPane();
	
	/**
	 * Instantiates a new modal layout.
	 */
	public ModalLayout() {
		initialize();
		actions.setAlignment(Pos.CENTER);
		body.setPadding(new Insets(10));
		VBox.setVgrow(body, Priority.ALWAYS);
		this.setPadding(new Insets(10));
		setSpacing(10);
		getChildren().setAll(heading, body, actions);
	}
	
	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	@SuppressWarnings("exports")
	public ObservableList<Node> getHeading(){
		return heading.getChildren();
	}
	
	/**
	 * Sets the heading.
	 *
	 * @param titleContent the new heading
	 */
	@SuppressWarnings("exports")
	public void setHeading(Node... titleContent) {
		this.heading.getChildren().setAll(titleContent);
	}
	
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	@SuppressWarnings("exports")
	public ObservableList<Node> getBody(){
		return body.getChildren();
	}
	
	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	@SuppressWarnings("exports")
	public void setBody(Node... body) {
		this.body.getChildren().setAll(body);
	}
	
	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	@SuppressWarnings("exports")
	public ObservableList<Node> getActions(){
		return actions.getChildren();
	}
	
	/**
	 * Sets the actions.
	 *
	 * @param actions the new actions
	 */
	@SuppressWarnings("exports")
	public void setActions(Node... actions) {
		this.actions.getChildren().setAll(actions);
	}
	
	/**
	 * Sets the actions.
	 *
	 * @param actions the new actions
	 */
	@SuppressWarnings("exports")
	public void setActions(List<? extends Node> actions) {
        this.actions.getChildren().setAll(actions);
    }
	
	/**
	 * Initialize.
	 */
	private void initialize() {
		
	}
}
