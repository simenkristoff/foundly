package foundly.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import foundly.model.Item;
import foundly.daoImpl.ItemDaoImpl;
import foundly.containers.ItemCellLayout;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/**
 * The Class ItemController.
 * Controller-class for the item view.
 */
public class ItemController extends AbstractViewController {

	@FXML private TabPane tabPane;
	private ObservableList<Tab> tabs = FXCollections.observableArrayList();
	private final String[] tabNames = {"Alle", "Mistet", "Funnet"};
	
	private ListView<Item> listView;
	
	private ItemDaoImpl itemDao;
	private static ObservableList<Item> items;
	
	/** Filters **/
	ObjectProperty<Predicate<Item>> stateFilter = new SimpleObjectProperty<>();
	ObjectProperty<Predicate<Item>> titleFilter = new SimpleObjectProperty<>();
	ObjectProperty<Predicate<Item>> descriptionFilter = new SimpleObjectProperty<>();
	
	private FilteredList<Item> filteredData;
	
	
	private SortedList<Item> sortedData;
	
	@FXML TextField searchFilter;
	
	public ItemController() {
		
	}
	
	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchData();
		setupListView();
		setupTabs();
		setupFilters();
	}

	private void fetchData() {
		itemDao = new ItemDaoImpl();
		items = FXCollections.observableArrayList(itemDao.getAll());
		
		filteredData = new FilteredList<Item>(items, p -> true);
		sortedData = new SortedList<>(filteredData);
	}
	
	private void setupTabs() {
		for(String tabName : tabNames) {
			Tab tab = new Tab(tabName);
			tab.setId(tabName.toLowerCase());
			tabs.add(tab);
		}
		
		tabPane.getTabs().setAll(tabs);
		System.out.println(tabPane.getTabs());
		tabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
			ov.setContent(null);
			nv.setContent(listView);
        });
		tabPane.getSelectionModel().select(0);;
		tabPane.getSelectionModel().getSelectedItem().setContent(listView);
	}
	
	private void setupListView() {
		this.listView = new ListView<Item>();
		
		/**
		 *  Changes the display of each cell to ItemCellLayout
		 */
		listView.setCellFactory(param -> new ListCell<Item>() {
			private ItemCellLayout itemCellLayout;
			
			@Override
			public void updateItem(Item item, boolean empty) {
				super.updateItem(item, empty);
				if(empty) {
					setText(null);
					setGraphic(null);
					return;
				}
				itemCellLayout = new ItemCellLayout(item);
				setGraphic(itemCellLayout);
			}
		});
		//sortedData.comparatorProperty().bind(listView);
		listView.setItems(filteredData);
	}
	
	private void setupFilters() {		
		stateFilter.bind(Bindings.createObjectBinding(() ->
			item -> {
				String pred = tabPane.getSelectionModel().selectedItemProperty().getValue().getId();
				if(!pred.equals("alle")) {
					if(item.getState().getValue().toLowerCase().equals(pred)) {
						return true;
					}
					return false;
				}
				return true;
			},
			tabPane.getSelectionModel().selectedItemProperty())
		);
		
		titleFilter.bind(Bindings.createObjectBinding(() -> 
            item -> item.getTitle().toLowerCase().contains(
            	searchFilter.getText().toLowerCase()
            ), 
            searchFilter.textProperty())
		);
		
		descriptionFilter.bind(Bindings.createObjectBinding(() -> 
	        item -> item.getDescription().toLowerCase().contains(
	        	searchFilter.getText().toLowerCase()
	        ), 
	        searchFilter.textProperty())
		);
		
		filteredData.predicateProperty().bind(Bindings.createObjectBinding(() ->
			stateFilter.get()
			.and(titleFilter.get().or(descriptionFilter.get())),
			stateFilter, titleFilter, descriptionFilter)
		);
	}
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public static ObservableList<Item> getItems(){
		return items;
	}

}
