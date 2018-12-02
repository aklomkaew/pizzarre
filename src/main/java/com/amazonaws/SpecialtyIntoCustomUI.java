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

/**
 * Represents interface to modify a specialty pizza's toppings for an Order item
 * @author Christopher
 *
 */

@SuppressWarnings("restriction")
public class SpecialtyIntoCustomUI {

	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Button pepperoni;
	@FXML
	private Button sausage;
	@FXML
	private Button groundbeef;
	@FXML
	private Button ham;
	@FXML
	private Button chicken;
	@FXML
	private Button steak;
	@FXML
	private Button anchovy;
	@FXML
	private Button shrimp;
	@FXML
	private Button tofu;
	@FXML
	private Button mushroom;
	@FXML
	private Button onion;
	@FXML
	private Button greenpepper;
	@FXML
	private Button tomato;
	@FXML
	private Button olive;
	@FXML
	private Button pineapple;
	@FXML
	private ListView<String> toppingListView = new ListView<String>();

	private ObservableList<String> toppingObservableList = FXCollections.observableArrayList();

	private ArrayList<String> toppingIdArrayList = new ArrayList<String>();

	private static final int SMALL = 1;
	private static final int MEDIUM = 2;
	private static final int LARGE = 3;

	String specialtyName;
	String specialtySize;

	/**
	 * Cancel the making of the specialty pizza
	 */
	
	public void cancelSpecialty() {
		
	}
	
	/**
	 * Adds or removes toppings from pizza, depending on if topping is on the current pizza
	 * @param onClick An ActionEvent that adds the button clicked to the pizza, or removes it from the pizza
	 */
	
	public void addRemoveTopping(ActionEvent onClick) {

		String id = ((Button) onClick.getSource()).getId();

		if (toppingIdArrayList.contains(id) == false) { // if statements adds topping to the list

			System.out.println(id + " added");
			toppingIdArrayList.add(id);
			toppingObservableList.add(id);

		} else { // else statement removes topping from the list

			System.out.println(id + " removed");
			toppingIdArrayList.remove(id);
			toppingObservableList.remove(id);
		}

		toppingListView.setItems(toppingObservableList); // displays toppings in the list
	}

	/**
	 * Takes a size String representation and enumerates it into a size integer
	 * @param str A string representing the size of the pizza
	 * @return An int of the current pizza's size
	 */
	
	private static int getpSize(String str) {
		if (str.equals("small")) {
			return SMALL;
		} else if (str.equals("medium")) {
			return MEDIUM;
		} else if (str.equals("large")) {
			return LARGE;
		}
		return -1; // should never get here
	}

	/**
	 * Adds the current pizza to the Order, subtracts ingredients from the inventory
	 */
	
	public void confirmSpecialty() { 
		
		int pSize = getpSize(specialtySize);

		ArrayList<String> list = new ArrayList<String>();
		for (String item : toppingObservableList) {
			list.add(item.toLowerCase());
		}

		int count = 0;
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			int num = InventoryDb.getQuantityOfItem(list.get(i));
			if (num == -1) {
				Alert.Display("Error", "Item " + list.get(0) + " not in inventory.");
				flag = true;
				break;
			}
			if (num < pSize) {
				Alert.Display("Error",
						"Not enough " + list.get(i) + " in the inventory. Ask your manager to restock the inventory");
				flag = true;
				break;
			} else {
				InventoryDb.changeQuantity(list.get(i), pSize, "decrease");
				count++;
			}
		}

		if (flag) {
			for (int i = 0; i < count; i++) {
				InventoryDb.changeQuantity(list.get(i), pSize, "increase");
				CurrentOrderUI.removeIngredient(list.get(i), pSize);
			}
			return;
		}

		if (pSize == 1) {
			specialtyName = "small, " + specialtyName;
		} else if (pSize == 2) {
			specialtyName = "medium, " + specialtyName;
		} else {
			specialtyName = "large, " + specialtyName;
		}
		
		ArrayList<String> pList = new ArrayList<String>();
		Pizza p = new Pizza(specialtyName, pSize, pList);
		for(int i = 0; i < list.size(); i++) { //loop that adds and increments pizza's price
			String topping = list.get(i);
			p.addTopping(topping);
		};

		Order order = CurrentOrderUI.getOrder();
		order.addPizza(p);

		Alert.Display("Success", "Pizza " + specialtyName + " is added to your order!");

		toppingObservableList.clear();
		toppingIdArrayList.clear();
		toppingListView.getItems().clear();

		goToOrderScreen();
	}

	/**
	 * Display SpecialtyPizzaUI stage and closes the current (SpecialtyIntoCustomUI) stage
	 */
    
	public void goToSpecialty() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}
	
	/**
	 * Display CurrentOrderUI stage and closes the current (SpecialtyIntoCustomUI) stage
	 */
    
	public void goToOrderScreen() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, confirmBtn);
	}

	/**
	 * Gets the recipe name and size from the previous stage and loads the current stage with it
	 * @param recipeName A string representing the name of the selected recipe
	 * @param size A string representing the size of the current pizza
	 */
	
	public void getSpecialtyInfo(String recipeName, String size) { // gets recipe name and size from previous controller
																	// (SpecialtyPizzaUI.java)
		this.specialtyName = recipeName;
		this.specialtySize = size;
		toppingIdArrayList = RecipeDb.getIngredients(specialtyName);
		toppingObservableList = FXCollections.observableArrayList(toppingIdArrayList);
		toppingListView.setItems(toppingObservableList);
	}

}
