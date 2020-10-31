package foundly.ui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

import foundly.core.dataaccess.ImageDataAccessObject;
import foundly.core.dataaccess.ItemDataAccessObject;
import foundly.core.model.Item;
import foundly.ui.App;
import foundly.ui.container.Modal;
import foundly.ui.container.ModalLayout;
import foundly.ui.control.ImagePicker;
import foundly.ui.effect.DepthManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class Navigator.
 * Handles navigation between different views/panes of the app. Navigator will only change
 * the center-content of the app's container, which is a {@link BorderPane}. In other words, the top, left, bottom and right
 * content of the container will remain the same when a view is changed.
 */
public class Navigator {

	@FXML private BorderPane container;
	
	private Scene scene;
	private FXMLLoader loader;
	
	@FXML Button btn_found;
	@FXML Button btn_lost;

	/**
	 * Instantiates a new navigator.
	 */
	public Navigator() {	
		setupScene();
		setView(View.ITEMS);
		DepthManager.setDepth(this.container.getTop(), 2);
		btn_found.setOnAction(foundItem());
		btn_lost.setOnAction(lostItem());
	}
	
	/**
	 * Event for adding found items
	 * 
	 * @return (ActionEvent) event
	 */
	private EventHandler<ActionEvent> foundItem() {
		return event -> {
			Modal<Item> modal = new Modal<Item>((Stage) btn_found.getScene().getWindow());
			
			ItemDataAccessObject itemDao = new ItemDataAccessObject(App.getApiUrl());
			ImageDataAccessObject imageDao = new ImageDataAccessObject(App.getApiUrl());
			modal.initModality(Modality.APPLICATION_MODAL);
			modal.setOverlayClose(false);
			
			ModalLayout layout = new ModalLayout();
			layout.setHeader(new Label("Legg til funnet gjenstand"));
			
			TextField item_title = new TextField();
			item_title.setPromptText("Tittel");
			
			TextArea item_desc = new TextArea();
			item_desc.setPromptText("Beskrivelse");
			
			TextField item_email = new TextField();
			item_email.setPromptText("Email-adresse");
			
			TextField item_phone = new TextField();
			item_phone.setPromptText("Telefon");
			
			ImagePicker item_image = new ImagePicker("Velg bilde");
			
			VBox form = new VBox(item_title, item_image, item_desc, item_email, item_phone);
			form.setAlignment(Pos.CENTER);
			form.setSpacing(3);
			
			layout.setBody(form);
			
			Button btn_submit = new Button("Legg til");
			btn_submit.setDefaultButton(true);
			btn_submit.getStyleClass().add("primary");
			btn_submit.setOnAction(addEvent -> {	
				Item item = new Item(
					item_title.getText(),
					item_desc.getText(),
					Item.State.FOUND,
					item_email.getText(),
					item_phone.getText(),
					item_image.getImageName(),
					LocalDateTime.now()
				);
				modal.setResult(item);
				modal.hide();
			});
			
			Button btn_cancel = new Button("Avbryt");
			btn_cancel.setCancelButton(true);
			btn_cancel.getStyleClass().add("danger");
			btn_cancel.setOnAction(closeEvent -> modal.hide());
			
			layout.setActions(btn_submit, btn_cancel);
			modal.setContent(layout);
			modal.setResultConverter(buttonType -> {
				if(buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
	            	modal.setResult(null);
	            }
	            return modal.getResult();
			});
			Optional<Item> result = modal.showAndWait();
			if (result.isPresent()){
				itemDao.insert((Item) result.get());
				if (item_image.getSelectedFile() != null) {
					imageDao.upload(item_image.getSelectedFile());
				}
				ItemController.getItems().addAll(result.get());
			}
		};
	}
	
	/**
	 * Event for adding found items
	 * 
	 * @return (ActionEvent) event
	 */
	private EventHandler<ActionEvent> lostItem() {
		return event -> {
			Modal<Item> modal = new Modal<Item>((Stage) btn_found.getScene().getWindow());

			ItemDataAccessObject itemDao = new ItemDataAccessObject(App.getApiUrl());
			ImageDataAccessObject imageDao = new ImageDataAccessObject(App.getApiUrl());
			modal.initModality(Modality.APPLICATION_MODAL);
			modal.setOverlayClose(false);
			
			ModalLayout layout = new ModalLayout();
			layout.setHeader(new Label("Legg til mistet gjenstand"));
			
			TextField item_title = new TextField();
			item_title.setPromptText("Tittel");
			
			TextArea item_desc = new TextArea();
			item_desc.setPromptText("Beskrivelse");
			
			TextField item_email = new TextField();
			item_email.setPromptText("Email-adresse");
			
			TextField item_phone = new TextField();
			item_phone.setPromptText("Telefon");
			
			ImagePicker item_image = new ImagePicker("Velg bilde");
			
			VBox form = new VBox(item_title, item_image, item_desc, item_email, item_phone);
			form.setAlignment(Pos.CENTER);
			form.setSpacing(3);
			
			
			layout.setBody(form);
			
			Button btn_submit = new Button("Legg til");
			btn_submit.setDefaultButton(true);
			btn_submit.getStyleClass().add("primary");
			btn_submit.setOnAction(addEvent -> {
				Item item = new Item(
					item_title.getText(),
					item_desc.getText(),
					Item.State.LOST,
					item_email.getText(),
					item_phone.getText(),
					item_image.getImageName(),
					LocalDateTime.now()
				);
				modal.setResult(item);
				modal.hide();
			});
			
			Button btn_cancel = new Button("Avbryt");
			btn_cancel.setCancelButton(true);
			btn_cancel.getStyleClass().add("danger");
			btn_cancel.setOnAction(closeEvent -> modal.hide());
			
			layout.setActions(btn_submit, btn_cancel);
			modal.setContent(layout);
			modal.setResultConverter(buttonType -> {
				if(buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
	            	modal.setResult(null);
	            }
	            return modal.getResult();
			});
			Optional<Item> result = modal.showAndWait();
			if (result.isPresent()){
				itemDao.insert((Item) result.get());
				if (item_image.getSelectedFile() != null) {
					imageDao.upload(item_image.getSelectedFile());
				}
				ItemController.getItems().addAll(result.get());
			}
		};
	}
	
	/**
	 * Setup scene.
	 */
	private void setupScene() {
		loader = new FXMLLoader();
		loader.setController(this);
		InputStream inputStream = null;
		try {
			inputStream = App.class.getResourceAsStream("views/app.fxml");
			this.scene = new Scene(loader.load(inputStream));
			inputStream.close();
			
			this.scene.getStylesheets().setAll(
					App.class.getResource("css/app.css").toExternalForm()
			);
		} catch (IOException e) {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the scene.
	 *
	 * @return the scene
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	/**
	 * Sets the view.
	 *
	 * @param view the new view to be displayed on the app
	 */
	public void setView(View view) {
		this.container.setCenter(view.loadView());
	}
	
	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public Node getView() {
		return this.container.getCenter();
	}
	
	/**
	 * The Enum View.
	 * Defines a View
	 */
	public enum View {
		ITEMS("items", new ItemController());
		
		private String fxml;
		private AbstractViewController controller;
		
		/**
		 * Instantiates a new view.
		 *
		 * @param fxml the fxml belonging to this view
		 * @param controller the controller belonging to this view
		 */
		View(String fxml, AbstractViewController controller){
			this.fxml = fxml;
			this.controller = controller;
		}
		
		/**
		 * Gets the controller.
		 *
		 * @return the controller
		 */
		public AbstractViewController getController() {
			return this.controller;
		}
		
		/**
		 * Loads the view.
		 *
		 * @param <T> the generic type
		 * @return the elements from the given fxml-file, linked with the set {@link ViewController}
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public Region loadView() {
			FXMLLoader loader = new FXMLLoader();
	    	loader.setController(this.controller);
	    	InputStream inputStream = null;
	    	Region region = null;
	    	try {
	    		inputStream = App.class.getResourceAsStream("views/" + this.fxml + ".fxml");
	    		region = loader.load(inputStream);
	    		inputStream.close();
	    	} catch(IOException e) {
	    		e.printStackTrace();
	    	}
	    	if(inputStream != null) {
	    		try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	return region;
		}
	}
}
