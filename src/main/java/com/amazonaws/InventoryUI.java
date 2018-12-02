package com.amazonaws;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Represents an interface for displaying the inventory database
 * 
 * @author Christopher
 *
 */
@SuppressWarnings("restriction")
public class InventoryUI implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button restockBtn;
	@FXML
	private Button addIngredientBtn;
	@FXML
	private Button deleteIngredientBtn;
	@FXML
	private Button refreshBtn;
	@FXML
	private TextField ingredientNameTF;
	@FXML
	private TextField quantityTF;
	@FXML
	private TableView<InventoryItem> inventoryTableView;
	@FXML
	private TableColumn<InventoryItem, String> nameColumn;
	@FXML
	private TableColumn<InventoryItem, Integer> quantityColumn;

	private ObservableList<InventoryItem> inventoryObservableList;

	/**
	 * Increases quantity of all inventory items
	 */
	public void restock() {
		InventoryDb.restock();
		displayAllInventory();
	}

	/**
	 * When adding a new ingredient, checks if a positive quantity was inputted or
	 * if inventory item name already exists
	 * 
	 * @throws NumberFormatException if integer was not used
	 */
	public void checkIngredient() {
		String ingredientName = ingredientNameTF.getText().toLowerCase();
		if (ingredientName == null || ingredientName.length() == 0) {
			Alert.Display("Error", "Please enter ingredient name.");
			return;
		}
		try {
			int quantity = Integer.parseInt(quantityTF.getText());

			if (quantity <= 0) {
				Alert.Display("Error", "Quantity must be positive.");
				return;
			} else {
				addIngredient(quantity, ingredientName);
			}
		} catch (NumberFormatException ex) {
			Alert.Display("Error", "Quantity must be a number");
		}
	}

	/**
	 * Adds the item to the inventory database
	 * 
	 * @param quantity       An integer representing the amount of an item being
	 *                       added to the database
	 * @param ingredientName A string representing the name of the item being added
	 *                       to the database
	 */
	public void addIngredient(int quantity, String ingredientName) {
		if (InventoryDb.addItem(ingredientName.toLowerCase(), quantity)) {
			Alert.Display("Success", "Ingredient has been added!");
		} else {
			Alert.Display("Error", "Ingredient with that name already exists");
		}
		ingredientNameTF.clear();
		quantityTF.clear();
		updateTable();
	}

	/**
	 * Displays the table after adding or removing items from the database
	 */
	public void updateTable() {
		List<InventoryItem> list = InventoryDb.retrieveAllItem();

		if (list == null || list.size() < 1) {
			return;
		}

		inventoryObservableList.clear();
		inventoryObservableList.addAll(list);
	}

	/**
	 * Removes an item from the database
	 */
	public void deleteIngredient() {
		InventoryItem itemToDelete = inventoryTableView.getSelectionModel().getSelectedItem();
		if (itemToDelete == null) {
			Alert.Display("Error", "Select an item to delete.");
			return;
		}

		inventoryObservableList.remove(itemToDelete);
		inventoryTableView.setItems(inventoryObservableList);

		Alert.Display("Success", "Ingredient " + itemToDelete.getName() + " deleted.");
		InventoryDb.deleteItem(itemToDelete.getName());
		updateTable();
	}

	/**
	 * Display ManagerUtilitiesUI stage and closes the current (InventoryUI) stage
	 */
	public void goToManagerUtilities() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	/**
	 * Adds all items from inventory database to the tableview
	 */
	public void displayAllInventory() {
		List<InventoryItem> list = InventoryDb.retrieveAllItem();

		if (list == null || list.size() < 1) {
			return;
		}

		inventoryObservableList.clear();
		inventoryObservableList.addAll(list);
		System.out.println("\nPrint from observableList");
		for (InventoryItem item : inventoryObservableList) {
			System.out.println("Inventory name = " + item.getName() + " quantity = " + item.getQuantity());
		}
	}

	/**
	 * Creates a two-column table displaying an InventoryItem's name and quantity
	 * then calls {@link #displayAllInventory}
	 * 
	 * @param location  Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("name"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("quantity"));
		inventoryObservableList = FXCollections.observableArrayList();
		inventoryTableView.setItems(inventoryObservableList);
		inventoryTableView.setEditable(true);
		displayAllInventory();
	}

}
