package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

@SuppressWarnings({ "unused" })
public class InventoryUI extends Application implements Initializable {

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

	public void restock(ActionEvent e) {
		InventoryDb.restock();
		displayAllInventory();
		Alert.Display("Success", "Restock completed");
	}

	public void checkIngredient(ActionEvent e) { // don't add integration code to this method, do it to addIngredient
		String ingredientName = ingredientNameTF.getText().toLowerCase();
		if (ingredientName == null || ingredientName.length() == 0) {
			Alert.Display("Error", "Please enter ingredient name.");
			return;
		}
		try {
			int quantity = Integer.parseInt(quantityTF.getText()); // checks if quantity is a number

			if (quantity <= 0) {
				Alert.Display("Error", "Quantity must be positive.");
				return;
			} else {
				addIngredient(e, quantity, ingredientName);
			}
		} catch (NumberFormatException ex) {
			Alert.Display("Error", "Quantity must be a number");
		}
	}

	public void addIngredient(ActionEvent e, int quantity, String ingredientName) { // integration goes here, not
																					// checkIngredient
		if (InventoryDb.addItem(ingredientName.toLowerCase(), quantity)) {
			Alert.Display("Success", "Ingredient has been added!");
		} else {
			Alert.Display("Error", "Ingredient with that name already exists");
		}
		ingredientNameTF.clear();
		quantityTF.clear();
		updateTable();
	}

	public void updateTable() {
		List<InventoryItem> list = InventoryDb.retrieveAllItem();

		if (list == null || list.size() < 1) {
			return;
		}

		inventoryObservableList.clear();
		inventoryObservableList.addAll(list);
	}

	public void deleteIngredient(ActionEvent e) {
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

	public void goToManagerUtilities(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	public void displayAllInventory(ActionEvent e) {
		displayAllInventory();
	}

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

	@Override
	public void start(Stage arg0) throws Exception {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("name"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("quantity"));
		inventoryObservableList = FXCollections.observableArrayList();
		// inventoryObservableList =
		// FXCollections.observableArrayList(InventoryDB.getAllItems())
		// inventoryObservableList.add(new InventoryItem("pepperoni", 100));
		// inventoryObservableList.add(new InventoryItem("greenPepper", 150));
		inventoryTableView.setItems(inventoryObservableList);
		inventoryTableView.setEditable(true);
		displayAllInventory();
	}

}
