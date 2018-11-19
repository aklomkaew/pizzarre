package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class DrinksUI implements Initializable {

	@FXML
	private Button confirm;
	@FXML
	private Button cancel;
	@FXML
	private Button remove;

	private String id = null; // string used to get drink from database
	@FXML
	private Button soda;
	@FXML
	private Button icedtea;
	@FXML
	private Button juice;
	@FXML
	private Button hottea;
	@FXML
	private Button beer;
	@FXML
	private Button wine;
	@FXML
	private ListView<String> drinkListView = new ListView<String>();

	private ObservableList<String> drinkObservableList = FXCollections.observableArrayList();

	private ArrayList<Drink> drinkArrayList = new ArrayList<Drink>();

	private static ArrayList<String> drinkIdArrayList = new ArrayList<String>();

	private HashMap<String, Integer> drinkMap = new HashMap<String, Integer>();

	public static ArrayList<String> getDrinkList() {
		return drinkIdArrayList;
	}

	public void selectDrink(ActionEvent e) {
		id = ((Button) e.getSource()).getId();
		System.out.println(id + " added");
		drinkIdArrayList.add(id);
		drinkObservableList.add(id);

		drinkListView.setItems(drinkObservableList); // displays toppings in the list
	}

	public void confirmDrinks(ActionEvent e) {
		boolean flag = false;
		if (drinkObservableList.isEmpty()) {
			Alert.Display("Information", "No drink was added");
			return;
		}

//		for(String item : drinkObservableList) {
//			if(!drinkMap.containsKey(item)) {
//				drinkMap.put(item, 1);
//			}
//			else {
//				drinkMap.put(item, drinkMap.get(item) + 1);
//			}
//		}

//		Iterator itr = drinkMap.entrySet().iterator();
//		ArrayList<String> count = new ArrayList<String>();
//		while(itr.hasNext()) {
//			Map.Entry pair = (Map.Entry)itr.next();
//			String item = (String) pair.getKey();
//			int quantity = (int) pair.getValue();
//			int num = InventoryDb.getQuantityOfItem(item);
//			
//			if(num == -1) {
//				Alert.Display("Error", "Item " + item + " not in the inventory.");
//				flag = true;
//				break;
//			}
//			if(num < 1) {
//				Alert.Display("Error", "Not enough " + item
//						+ " in the inventory. Ask your manager to restock the inventory");
//				flag = true;
//				break;
//			}
//			else {
//				InventoryDb.changeQuantity(item, quantity, "decrease");
//				NewOrderUI.addIngredient(item, quantity);
//				count.add(item);
//			}
//		}

		int count = 0;
		for (String item : drinkObservableList) {
			int num = InventoryDb.getQuantityOfItem(item);
			if (num == -1) {
				Alert.Display("Error", "Item " + item + " not in inventory.");
				flag = true;
				break;
			} else {
				InventoryDb.changeQuantity(item, 1, "decrease");
				NewOrderUI.addIngredient(item, 1);
				count++;
			}
		}

		if (flag) {
			for (int i = 0; i < count; i++) {
				InventoryDb.changeQuantity(drinkObservableList.get(i), 1, "increase");
				NewOrderUI.removeIngredient(drinkObservableList.get(i), 1);
			}
			return;
		}

//		Iterator it = drinkMap.entrySet().iterator();
//		Order order = NewOrderUI.getOrder();
//		while(it.hasNext()) {
//			Map.Entry pair = (Map.Entry)it.next();
//			String item = (String) pair.getKey();
//			int quantity = (int) pair.getValue();
//			Drink d = new Drink(item, 2);
//			order.getDrink().add(d);
//			//order.getDrinkQuantity().add(quantity);
//		}

		// NewOrderUI.addDrinks(drinkIdArrayList);
		Order order = NewOrderUI.getOrder();
		/*
		 * for(String item : drinkObservableList) { Drink d = new Drink(item, 2);
		 * order.getDrink().add(d); }
		 */
		String newName = "";
		for (int i = 0; i < drinkIdArrayList.size(); i++) { // loop that adds and increments pizza's price
			Drink newDrink = new Drink("", 0);
			newName = drinkIdArrayList.get(i);
			newDrink.setName(newName);
			newDrink.setPrice();
			drinkArrayList.add(newDrink);
		}
		order.setDrink(drinkArrayList);

		Alert.Display("Success", "Your drink has been added to your order!");

//		drinkObservableList.clear();
//		drinkIdArrayList.clear();
//		drinkListView.getItems().clear();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, confirm);

	}

	public void cancelDrink(ActionEvent e) {
		drinkObservableList.clear();
		drinkListView.getItems().clear();
		goToOrderScreen(e);
	}

	public void removeDrink(ActionEvent e) {
		int index = drinkListView.getSelectionModel().getSelectedIndex();
		if(drinkArrayList.size() <= index) {
			if(drinkArrayList.get(index).getIsNew() == 0) {
				Alert.Display("Error", "This item cannot be removed.");
				return;
			}
		}
		drinkIdArrayList.remove(index);
		drinkObservableList.remove(index);
		drinkListView.setItems(drinkObservableList);
	}

	public void goToOrderScreen(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancel);
	}

	public void start(Stage arg0) throws Exception {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		drinkIdArrayList.clear();
		drinkObservableList.clear();
		drinkIdArrayList.addAll(NewOrderUI.getDrinks());
		drinkObservableList.addAll(drinkIdArrayList);
		drinkListView.setItems(drinkObservableList);

		if (!drinkIdArrayList.isEmpty()) {
			for (String str : drinkIdArrayList) {
				InventoryDb.changeQuantity(str, 1, "increase");
			}
		}
	}

}
