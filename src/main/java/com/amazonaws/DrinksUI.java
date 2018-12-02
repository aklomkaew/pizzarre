package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class DrinksUI implements Initializable {
	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button removeBtn;

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

	//private HashMap<String, Integer> drinkMap = new HashMap<String, Integer>();
	
	private static ArrayList<Drink> oldDrinks = new ArrayList<Drink>();
	
	public static ArrayList<String> getDrinkList() {
		return drinkIdArrayList;
	}
	
	public static void setDrinks(ArrayList<Drink> list) {
		oldDrinks.clear();
		oldDrinks.addAll(list);
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

		int count = 0;
		for (String item : drinkObservableList) {
			int num = InventoryDb.getQuantityOfItem(item);
			if (num == -1) {
				Alert.Display("Error", "Item " + item + " not in inventory.");
				flag = true;
				break;
			} else {
				InventoryDb.changeQuantity(item, 1, "decrease");
				count++;
			}
		}

		if (flag) {
			for (int i = 0; i < count; i++) {
				InventoryDb.changeQuantity(drinkObservableList.get(i), 1, "increase");
				CurrentOrderUI.removeIngredient(drinkObservableList.get(i), 1);
			}
			return;
		}
		
		Order order = CurrentOrderUI.getOrder();
		String newName = "";
		drinkArrayList.clear();
		for (int i = 0; i < drinkIdArrayList.size(); i++) { // loop that adds and increments pizza's price
			newName = drinkIdArrayList.get(i);
			Drink newDrink = new Drink(newName, 2);
			Drink tmp = containsDrink(newName);
			if(tmp != null) {
				newDrink = tmp;
				removeDrink(newName);
			}
			
			drinkArrayList.add(newDrink);
		}
		order.getDrink().clear();
		order.setDrink(drinkArrayList);

		Alert.Display("Success", "Your drink has been added to your order!");

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, confirmBtn);

	}
	
	private Drink containsDrink(String str) {
		Drink ret = null;
		for(Drink d : oldDrinks) {
			if(d.getName().equals(str)) {
				ret = d;
				break;
			}
		}
		
		return ret;
	}
	
	private void removeDrink(String str) {
		for(int i = 0; i < oldDrinks.size(); i++) {
			if(oldDrinks.get(i).getName().equals(str)) {
				oldDrinks.remove(i);
			}
		}
	}

	public void cancelDrink(ActionEvent e) {
		drinkObservableList.clear();
		drinkListView.getItems().clear();
		goToOrderScreen(e);
	}

	public void removeDrink(ActionEvent e) {
		int index = drinkListView.getSelectionModel().getSelectedIndex();
		if(drinkArrayList.size() > index) {
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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancelBtn);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		drinkIdArrayList.clear();
		drinkObservableList.clear();
		drinkArrayList.clear();
		
		drinkIdArrayList.addAll(CurrentOrderUI.getDrinks());
		
		Order order = CurrentOrderUI.getOrder();
		drinkArrayList.addAll(order.getDrink());
		
		drinkObservableList.addAll(drinkIdArrayList);
		drinkListView.setItems(drinkObservableList);

		if (!drinkIdArrayList.isEmpty()) {
			for (String str : drinkIdArrayList) {
				InventoryDb.changeQuantity(str, 1, "increase");
			}
		}
	}

}
