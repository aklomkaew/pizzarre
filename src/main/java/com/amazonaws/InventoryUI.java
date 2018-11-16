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
    	//InventoryDb.restock();
    	inventoryTableView.setItems(inventoryObservableList);
    }
    
    public void checkIngredient(ActionEvent e) { // don't add integration code to this method, do it to addIngredient
    	try {
			Integer.parseInt(quantityTF.getText()); //checks if quantity is a number
			if (Integer.parseInt(quantityTF.getText()) > 0)
			{
				addIngredient(e);
			} else {
				Alert.Display("Error", "Quantity must be positive.");
			}
		} catch (NumberFormatException ex) {
			Alert.Display("Error", "Quantity must be a number");
		}
    }
    
    public void addIngredient(ActionEvent e) { //integration goes here, not checkIngredient
    	String ingredientName = ingredientNameTF.getText();
    	int quantity = Integer.parseInt(quantityTF.getText());
    	ingredientNameTF.clear();
    	quantityTF.clear();
    }
    
    public void deleteIngredient(ActionEvent e) {
    	
    }
	public void goToManagerUtilities(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage managerUtiliesStage = new Stage();
			managerUtiliesStage.setScene(new Scene(root));
			managerUtiliesStage.setTitle("Main Menu");
			managerUtiliesStage.show();
			Stage inventoryStage = (Stage) backBtn.getScene().getWindow();
			inventoryStage.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
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
		//inventoryObservableList = FXCollections.observableArrayList(InventoryDB.getAllItems())
		//inventoryObservableList.add(new InventoryItem("pepperoni", 100));
		//inventoryObservableList.add(new InventoryItem("greenPepper", 150));
		inventoryTableView.setItems(inventoryObservableList);
		inventoryTableView.setEditable(true);
    displayAllInventory();
	}
	
}
