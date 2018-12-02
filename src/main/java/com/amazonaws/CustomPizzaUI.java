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
import javafx.scene.control.TextField;

/**
 * Represents interface to add and remove toppings from the current pizza
 * @author Christopher
 *
 */

@SuppressWarnings("restriction")
public class CustomPizzaUI implements Initializable {

	@FXML
	private Button confirmBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private static String pizzaSize = null;
	@FXML
	private TextField sizeTF;
	@FXML
	private Button small;
	@FXML
	private Button medium;
	@FXML
	private Button large;
	@FXML
	private Button pepperoni;
	@FXML
	private Button sausage;
	@FXML
	private Button groundBeef;
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
	private Button greenPepper;
	@FXML
	private Button tomato;
	@FXML
	private Button olive;
	@FXML
	private Button pineapple;
	@FXML
	private String toppingName;
	@FXML
	private ListView<String> toppingListView = new ListView<String>();

	private static ObservableList<String> toppingObservableList = FXCollections.observableArrayList();

	private static ArrayList<String> toppingIdArrayList = new ArrayList<String>();

	private static Pizza modPizza = new Pizza();
	private static ArrayList<Pizza> oldPizzas = new ArrayList<Pizza>();
	
	private static ArrayList<String> oldTopping = new ArrayList<String>();

	private static boolean modified = false;

	private static final int SMALL = 1;
	private static final int MEDIUM = 2;
	private static final int LARGE = 3;

	/**
	 * Loads a text label with the current size of the pizza
	 * @param onClick An ActionEvent that sets the label string equal to the button selected's contents
	 */

	public void selectSize(ActionEvent onClick) {
		pizzaSize = ((Button) onClick.getSource()).getId();
		sizeTF.setText(pizzaSize);
	}

	/**
	 * Adds or removes toppings from the current pizza, depending on if the pizza already has the topping on it
	 * @param onClick An ActionEvent that adds or removes the contents of the button pressed
	 */
	
	public void addTopping(ActionEvent onClick) {

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
	 * Adds the current pizza to the Order
	 * Replaces the old pizza if it is a modified pizza
	 */
	
	public void confirmPizza() {
		if (pizzaSize == null) {
			Alert.Display("ERROR", "Select a size.");
			return;
		} else if (toppingObservableList == null || toppingObservableList.size() == 0) {
			Alert.Display("ERROR", "Select at least one topping.");
			return;
		} else {
			System.out.println(
					"You chose a " + pizzaSize + " pizza with the following toppings: " + toppingObservableList);
			System.out.println("User id = " + LoginUI.getUser().getUserId());
			int pSize = getpSize(pizzaSize);

			ArrayList<String> list = new ArrayList<String>();
			for (String item : toppingObservableList) {
				list.add(item.toLowerCase());
			}
			String pizzaName = "basePizza";

			if (!modified) {
				list.addAll(RecipeDb.getIngredients(pizzaName));
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
					Alert.Display("Error", "Not enough " + list.get(i)
							+ " in the inventory. Ask your manager to restock the inventory");
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

			pizzaName = (modified ? modPizza.getName() : "Custom");
			ArrayList<String> pList = new ArrayList<String>();

			if (modified == false) { // stops for example: small, pizza -> small, small, pizza
				if (pSize == 1) {
					pizzaName = "small, " + pizzaName;
				} else if (pSize == 2) {
					pizzaName = "medium, " + pizzaName;
				} else {
					pizzaName = "large, " + pizzaName;
				}
			}

			Pizza p = new Pizza(pizzaName, pSize, pList);
			for (int i = 0; i < list.size(); i++) { // loop that adds and increments pizza's price
				String topping = list.get(i);
				p.addTopping(topping);
			}
			p.setPrice(pSize * p.getToppings().size());

			Order order = CurrentOrderUI.getOrder();
			
			order.addPizza(p);

			Alert.Display("Success", "Custom Pizza is added to your order!");

			toppingObservableList.clear();
			toppingIdArrayList.clear();
			toppingListView.getItems().clear();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
			NextStage.goTo(fxmlLoader, confirmBtn);
		}
	}

	/**
	 * If a modified pizza, gets the size of the pizza
	 * Loads the table with Order data if not a new Order
	 * @param str A string representation of the pizza's size
	 * @return An int representation of the pizza's size
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
	 * Clears list of toppings added to the pizza
	 */
	
	public void clearPizza() {
		toppingObservableList.clear();
		toppingIdArrayList.clear();
		toppingListView.getItems().clear();
	}

	/**
	 * Deletes the pizza from the order then goes to NewOrderUI
	 */
	
	public void cancelPizza() {
		if (!modified) {
			removePizza();
		} else {
			// removePizza();
			Order order = CurrentOrderUI.getOrder();
			Pizza tmp = new Pizza(modPizza.getName(), modPizza.getSize(), oldTopping);
			order.addPizza(tmp);
			modPizza = null;
			modified = false;
		}

		toppingObservableList.clear();
		toppingIdArrayList.clear();
		toppingListView.getItems().clear();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancelBtn);
	}

	/**
	 * Adds ingredients back to the inventory if pizza deleted
	 */
	
	public static void removePizza() {
		if (toppingObservableList.isEmpty()) {
			return;
		}
		ArrayList<String> list = new ArrayList<String>();
		for (String item : toppingObservableList) {
			list.add(item.toLowerCase());
		}
		String pizzaName = "basePizza";
		list.addAll(RecipeDb.getIngredients(pizzaName));

		int pSize = getpSize(pizzaSize);
		for (int i = 0; i < list.size(); i++) {
			InventoryDb.changeQuantity(list.get(i), pSize, "increase");
			CurrentOrderUI.removeIngredient(list.get(i), pSize);
		}
	}

	public static void setOldPizza(ArrayList<Pizza> list) {
		oldPizzas.clear();
		oldPizzas.addAll(list);
	}
	
	/**
	 * Loads a modified pizza's toppings and size into the interface
	 * @param p A Pizza object selected from CurrentOrderUI
	 */
	
	public static void setPizza(Pizza p) {
		modPizza = p;
		toppingIdArrayList = p.getToppings();
		oldTopping.clear();
		oldTopping.addAll(p.getToppings());
		String size = getSize(modPizza.getSize());

		modified = true;
	}

	/**
	 * Loads a text label with the selected size of the pizza if the pizza is being modified
	 * @param size A string representing the size of the pizza
	 */
	
	public void setSizeText(String size) {
		sizeTF.setText(size);
	}
	
	/**
	 * Returns a string representation of the pizza's size for display on CurrentOrderUI
	 * @param num An int that is the pizza's size
	 * @return A string representing the pizza's size
	 */
	
	private static String getSize(int num) {
		String ret = "";
		switch (num) {
		case 1:
			ret = "small";
			break;
		case 2:
			ret = "medium";
			break;
		case 3:
			ret = "large";
			break;
		}
		return ret;
	}

	/**
	 * Loads the viewable list with the pizzas current toppins
	 * Loads the table with Order data if not a new Order
	 * @param location Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (!toppingIdArrayList.isEmpty()) {
			// modified pizza
			toppingObservableList.addAll(toppingIdArrayList);
			toppingListView.setItems(toppingObservableList);
			modified = true;
		}
		sizeTF.setText(getSize(modPizza.getSize()));
	}
}
