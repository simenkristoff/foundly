package foundly.core.containers;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ModalLayout extends VBox {

	private StackPane heading = new StackPane();
	private StackPane body = new StackPane();
	private FlowPane actions = new FlowPane();
	
	public ModalLayout() {
		initialize();
		actions.setAlignment(Pos.CENTER);
		
		VBox.setVgrow(body, Priority.ALWAYS);
		setSpacing(5);
		getChildren().setAll(heading, body, actions);
	}
	
	@SuppressWarnings("exports")
	public ObservableList<Node> getHeading(){
		return heading.getChildren();
	}
	
	@SuppressWarnings("exports")
	public void setHeading(Node... titleContent) {
		this.heading.getChildren().setAll(titleContent);
	}
	
	@SuppressWarnings("exports")
	public ObservableList<Node> getBody(){
		return body.getChildren();
	}
	
	@SuppressWarnings("exports")
	public void setBody(Node... body) {
		this.body.getChildren().setAll(body);
	}
	
	@SuppressWarnings("exports")
	public ObservableList<Node> getActions(){
		return actions.getChildren();
	}
	
	@SuppressWarnings("exports")
	public void setActions(Node... actions) {
		this.actions.getChildren().setAll(actions);
	}
	
	@SuppressWarnings("exports")
	public void setActions(List<? extends Node> actions) {
        this.actions.getChildren().setAll(actions);
    }
	
	private void initialize() {
		
	}
}
