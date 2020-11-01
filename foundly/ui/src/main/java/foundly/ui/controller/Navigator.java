package foundly.ui.controller;

import foundly.core.dataaccess.ImageDataAccessObject;
import foundly.core.dataaccess.ItemDataAccessObject;
import foundly.core.model.Item;
import foundly.ui.App;
import foundly.ui.container.Modal;
import foundly.ui.container.ModalLayout;
import foundly.ui.control.ImagePicker;
import foundly.ui.effect.DepthManager;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class Navigator. Handles navigation between different views/panes of the app. Navigator will
 * only change the center-content of the app's container, which is a {@link BorderPane}. In other
 * words, the top, left, bottom and right content of the container will remain the same when a view
 * is changed.
 */
public class Navigator {

  @FXML
  private BorderPane container;

  private Scene scene;
  private FXMLLoader loader;

  @FXML
  Button btnFound;
  @FXML
  Button btnLost;

  /**
   * Instantiates a new navigator.
   */
  public Navigator() {
    setupScene();
    setView(View.ITEMS);
    DepthManager.setDepth(this.container.getTop(), 2);
    btnFound.setOnAction(foundItem());
    btnLost.setOnAction(lostItem());
  }

  /**
   * Event for adding found items.
   * 
   * @return (ActionEvent) event
   */
  private EventHandler<ActionEvent> foundItem() {
    return event -> {
      Modal<Item> modal = new Modal<Item>((Stage) btnFound.getScene().getWindow());

      ItemDataAccessObject itemDao = new ItemDataAccessObject(App.API_URL);
      ImageDataAccessObject imageDao = new ImageDataAccessObject(App.API_URL);
      modal.initModality(Modality.APPLICATION_MODAL);
      modal.setOverlayClose(false);

      ModalLayout layout = new ModalLayout();
      layout.setHeader(new Label("Legg til funnet gjenstand"));

      TextField itemTitle = new TextField();
      itemTitle.setPromptText("Tittel");

      TextArea itemDesc = new TextArea();
      itemDesc.setPromptText("Beskrivelse");

      TextField itemEmail = new TextField();
      itemEmail.setPromptText("Email-adresse");

      TextField itemPhone = new TextField();
      itemPhone.setPromptText("Telefon");

      ImagePicker itemImage = new ImagePicker("Velg bilde");

      VBox form = new VBox(itemTitle, itemImage, itemDesc, itemEmail, itemPhone);
      form.setAlignment(Pos.CENTER);
      form.setSpacing(3);

      layout.setBody(form);

      Button btnSubmit = new Button("Legg til");
      btnSubmit.setDefaultButton(true);
      btnSubmit.getStyleClass().add("primary");
      btnSubmit.setOnAction(addEvent -> {
        Item item = new Item(itemTitle.getText(), itemDesc.getText(), Item.State.FOUND,
            itemEmail.getText(), itemPhone.getText(), itemImage.getImageName(),
            LocalDateTime.now());
        modal.setResult(item);
        modal.hide();
      });

      Button btnCancel = new Button("Avbryt");
      btnCancel.setCancelButton(true);
      btnCancel.getStyleClass().add("danger");
      btnCancel.setOnAction(closeEvent -> modal.hide());

      layout.setActions(btnSubmit, btnCancel);
      modal.setContent(layout);
      modal.setResultConverter(buttonType -> {
        if (buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
          modal.setResult(null);
        }
        return modal.getResult();
      });
      Optional<Item> result = modal.showAndWait();
      if (result.isPresent()) {
        itemDao.insert((Item) result.get());
        if (itemImage.getSelectedFile() != null) {
          imageDao.upload(itemImage.getSelectedFile());
        }
        ItemController.getItems().addAll(result.get());
      }
    };
  }

  /**
   * Event for adding found items.
   * 
   * @return (ActionEvent) event
   */
  private EventHandler<ActionEvent> lostItem() {
    return event -> {
      Modal<Item> modal = new Modal<Item>((Stage) btnFound.getScene().getWindow());

      ItemDataAccessObject itemDao = new ItemDataAccessObject(App.API_URL);
      ImageDataAccessObject imageDao = new ImageDataAccessObject(App.API_URL);
      modal.initModality(Modality.APPLICATION_MODAL);
      modal.setOverlayClose(false);

      ModalLayout layout = new ModalLayout();
      layout.setHeader(new Label("Legg til mistet gjenstand"));

      TextField itemTitle = new TextField();
      itemTitle.setPromptText("Tittel");

      TextArea itemDesc = new TextArea();
      itemDesc.setPromptText("Beskrivelse");

      TextField itemEmail = new TextField();
      itemEmail.setPromptText("Email-adresse");

      TextField itemPhone = new TextField();
      itemPhone.setPromptText("Telefon");

      ImagePicker itemImage = new ImagePicker("Velg bilde");

      VBox form = new VBox(itemTitle, itemImage, itemDesc, itemEmail, itemPhone);
      form.setAlignment(Pos.CENTER);
      form.setSpacing(3);


      layout.setBody(form);

      Button btnSubmit = new Button("Legg til");
      btnSubmit.setDefaultButton(true);
      btnSubmit.getStyleClass().add("primary");
      btnSubmit.setOnAction(addEvent -> {
        Item item = new Item(itemTitle.getText(), itemDesc.getText(), Item.State.LOST,
            itemEmail.getText(), itemPhone.getText(), itemImage.getImageName(),
            LocalDateTime.now());
        modal.setResult(item);
        modal.hide();
      });

      Button btnCancel = new Button("Avbryt");
      btnCancel.setCancelButton(true);
      btnCancel.getStyleClass().add("danger");
      btnCancel.setOnAction(closeEvent -> modal.hide());

      layout.setActions(btnSubmit, btnCancel);
      modal.setContent(layout);
      modal.setResultConverter(buttonType -> {
        if (buttonType.getButtonData() == ButtonData.CANCEL_CLOSE) {
          modal.setResult(null);
        }
        return modal.getResult();
      });
      Optional<Item> result = modal.showAndWait();
      if (result.isPresent()) {
        itemDao.insert((Item) result.get());
        if (itemImage.getSelectedFile() != null) {
          imageDao.upload(itemImage.getSelectedFile());
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

      this.scene.getStylesheets().setAll(App.class.getResource("css/app.css").toExternalForm());
    } catch (IOException e) {
      if (inputStream != null) {
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
   * The Enum View. Defines a View.
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
    View(String fxml, AbstractViewController controller) {
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
     * @return the elements from the given fxml-file, linked with the set {@link ViewController}
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
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (inputStream != null) {
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
