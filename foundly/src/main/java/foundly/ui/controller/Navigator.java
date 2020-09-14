package foundly.ui.controller;

import java.io.IOException;
import java.util.Optional;

import foundly.core.containers.Modal;
import foundly.core.containers.ModalLayout;
import foundly.core.controls.ImagePicker;
import foundly.core.model.Item;
import foundly.database.daoImpl.ItemDaoImpl;
import foundly.ui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Navigator {

	@FXML private BorderPane container;
	
	private Scene scene;
	private FXMLLoader loader;
	
	@FXML Button btn_found;
	@FXML Button btn_lost;

	public Navigator() {
		setupScene();
		setView(View.ITEMS);
		
		btn_found.setOnAction(event -> {
			Modal<Item> modal = new Modal<Item>((Stage) btn_found.getScene().getWindow());
			ItemDaoImpl itemDao = new ItemDaoImpl();
			modal.initModality(Modality.APPLICATION_MODAL);
			modal.setOverlayClose(false);
			
			ModalLayout layout = new ModalLayout();
			layout.setHeading(new Label("Legg til ny gjenstand"));
			
			TextField item_name = new TextField();
			item_name.setPromptText("Navn");
			
			ImagePicker item_image = new ImagePicker("Velg bilde");
			
			TextArea item_desc = new TextArea();
			item_desc.setPromptText("Beskrivelse");
			
			VBox form = new VBox(item_name, item_image, item_desc);
			
			
			layout.setBody(form);
			
			Button btn_submit = new Button("Legg til");
			btn_submit.setDefaultButton(true);
			btn_submit.setOnAction(addEvent -> {
				Item item = new Item(
					null, 						// ID
					item_name.getText(), 		// NAME
					item_desc.getText(), 		// DESCRIPTION
					item_image.getImageAsBlob() // IMAGE
				);
				modal.setResult(item);
				modal.hide();
			});
			
			Button btn_cancel = new Button("Avbryt");
			btn_cancel.setCancelButton(true);
			btn_cancel.setOnAction(closeEvent -> modal.hide());
			
			layout.setActions(btn_submit, btn_cancel);
			modal.setContent(layout);
			modal.setResultConverter(buttonType -> {
				if(buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
                	return null;
                }
                return modal.getResult();
			});
			Optional<Item> result = modal.showAndWait();
			if (result.isPresent()){
				itemDao.insert((Item) result.get());
				ItemController.getItems().addAll(result.get());
			}
		});
		setupRoutes();
	}
	
	private void setupRoutes() {

	}
	
	private void setupScene() {
		loader = new FXMLLoader();
		loader.setController(this);
		try {
			this.scene = new Scene(loader.load(
				App.class.getResourceAsStream("views/app.fxml"))
			);
			
			this.scene.getStylesheets().setAll(
					App.class.getResource("css/app.css").toExternalForm()
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public void setView(View view) {
		try {
			this.container.setCenter(view.loadView());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Node getView() {
		return this.container.getCenter();
	}
	
	public enum View {
		ITEMS("items", new ItemController());
		
		private String fxml;
		private AbstractViewController controller;
		
		View(String fxml, AbstractViewController controller){
			this.fxml = fxml;
			this.controller = controller;
		}
		
		public AbstractViewController getController() {
			return this.controller;
		}
		
		public <T> T loadView() throws IOException {
			FXMLLoader loader = new FXMLLoader();
	    	loader.setController(this.controller);
	    	
	    	return loader.load(
	    		App.class.getResourceAsStream("views/" + this.fxml + ".fxml")
	    	);
		}
	}
}
