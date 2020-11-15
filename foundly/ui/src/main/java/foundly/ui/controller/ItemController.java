package foundly.ui.controller;

import foundly.core.model.Item;
import foundly.ui.App;
import foundly.ui.container.ItemCellLayout;
import foundly.ui.dataaccess.ItemDataAccess;
import foundly.ui.dataaccess.ItemDataAccessObject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * The Class ItemController. Controller-class for the item view.
 */
public class ItemController extends AbstractViewController {

  @FXML
  private TabPane tabPane;
  private ObservableList<Tab> tabs;
  private final String[] tabNames = {"Alle", "Mistet", "Funnet"};

  private ListView<Item> listView;

  // Data access
  private ItemDataAccess itemDao;

  private static ObservableList<Item> items;

  // Filters for items.
  ObjectProperty<Predicate<Item>> stateFilter = new SimpleObjectProperty<>();
  ObjectProperty<Predicate<Item>> titleFilter = new SimpleObjectProperty<>();
  ObjectProperty<Predicate<Item>> descriptionFilter = new SimpleObjectProperty<>();

  private FilteredList<Item> filteredData;

  @FXML
  TextField searchFilter;

  @FXML
  Button btnRefresh;

  /**
   * Instantiates a new item controller.
   */
  public ItemController() {
    setupDataAccess();
  }

  private void setupDataAccess() {
    this.itemDao = new ItemDataAccessObject();
  }

  /**
   * Initialize the ItemController.
   *
   * @param location the location
   * @param resources the resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setupListView();
    setupTabs();
    setupFilters();
    fetchData();

    // Setup refresh button
    btnRefresh.setOnAction(refresh -> {
      fetchData();
    });

    // Setup automatic refresh for fetching items
    Timeline refresh = new Timeline(
        new KeyFrame(Duration.millis(Integer.parseInt(App.getProperty("api.refreshMillis"))),
            new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                fetchData();
              }
            }));
    refresh.setCycleCount(Timeline.INDEFINITE);
    refresh.play();
  }

  /**
   * Fetch data from the rest-api and bind them to filters.
   */
  private void fetchData() {
    // Check connection to REST Api
    if (App.isRemote()) {
      items = FXCollections.observableArrayList(itemDao.getAll());
    } else {
      items = (items == null) ? FXCollections.observableArrayList() : items;
    }
    bindFilters();

  }

  /**
   * Setup the tabs for filtering items by their state [LOST|FOUND].
   */
  private void setupTabs() {
    tabs = FXCollections.observableArrayList();

    for (String tabName : tabNames) {
      Tab tab = new Tab(tabName);
      tab.setId(tabName.toLowerCase());
      tabs.add(tab);
    }

    tabPane.getTabs().setAll(tabs);
    tabPane.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
      ov.setContent(null);
      nv.setContent(listView);
    });
    tabPane.getSelectionModel().select(0);;
    tabPane.getSelectionModel().getSelectedItem().setContent(listView);
  }

  /**
   * Setup the list view which displays the items.
   */
  private void setupListView() {
    this.listView = new ListView<Item>();
    this.listView.setId("items-list");

    // Changes the display of each cell to ItemCellLayout.
    listView.setCellFactory(param -> new ListCell<Item>() {
      private ItemCellLayout itemCellLayout;

      @Override
      public void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText(null);
          setGraphic(null);
          return;
        }
        itemCellLayout = new ItemCellLayout(item);
        setGraphic(itemCellLayout);
      }
    });
  }

  /**
   * Setup the filters.
   */
  private void setupFilters() {
    stateFilter.bind(Bindings.createObjectBinding(() -> item -> {
      String pred = tabPane.getSelectionModel().selectedItemProperty().getValue().getId();
      if (!pred.equals("alle")) {
        if (item.getState().getValue().toLowerCase().equals(pred)) {
          return true;
        }
        return false;
      }
      return true;
    }, tabPane.getSelectionModel().selectedItemProperty()));

    titleFilter.bind(Bindings.createObjectBinding(
        () -> item -> item.getTitle().toLowerCase().contains(searchFilter.getText().toLowerCase()),
        searchFilter.textProperty()));

    descriptionFilter
        .bind(
            Bindings
                .createObjectBinding(
                    () -> item -> item.getDescription().toLowerCase()
                        .contains(searchFilter.getText().toLowerCase()),
                    searchFilter.textProperty()));
  }

  /**
   * Binds the filters to a new Filtered List.
   */
  private void bindFilters() {
    filteredData = new FilteredList<Item>(items, p -> true);

    filteredData.predicateProperty()
        .bind(Bindings.createObjectBinding(
            () -> stateFilter.get().and(titleFilter.get().or(descriptionFilter.get())), stateFilter,
            titleFilter, descriptionFilter));

    // Add the filtered list to ListView
    listView.setItems(filteredData);
  }

  /**
   * Gets the items.
   *
   * @return the items
   */
  public static ObservableList<Item> getItems() {
    return items;
  }

}
