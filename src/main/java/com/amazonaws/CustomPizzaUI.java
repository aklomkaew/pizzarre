package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
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
	
	private boolean modified = false;

	private static final int SMALL = 1;
	private static final int MEDIUM = 2;
	private static final int LARGE = 3;

	public void selectSize(ActionEvent e) {
		pizzaSize = ((Button) e.getSource()).getId();
	}

	public void addTopping(ActionEvent e) {

		String id = ((Button) e.getSource()).getId();

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

	public void confirmPizza(ActionEvent e) {
		// add pizza to order here
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
			list.addAll(RecipeDb.getIngredients(pizzaName));

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
					NewOrderUI.addIngredient(list.get(i), pSize);
					count++;
				}
			}

			if (flag) {
				for (int i = 0; i < count; i++) {
					InventoryDb.changeQuantity(list.get(i), pSize, "increase");
					NewOrderUI.removeIngredient(list.get(i), pSize);
				}
				return;
			}

			pizzaName = "Custom";
			ArrayList<String> pList = new ArrayList<String>();
			Pizza p = new Pizza(pizzaName, pSize, pList);
			for (int i = 0; i < list.size(); i++) { // loop that adds and increments pizza's price
				String topping = list.get(i);
				p.addTopping(topping);
			}

			Order order = NewOrderUI.getOrder();
			order.addPizza(p);

			Alert.Display("Success", "Custom Pizza is added to your order!");

			toppingObservableList.clear();
			toppingIdArrayList.clear();
			toppingListView.getItems().clear();

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
			NextStage.goTo(fxmlLoader, confirmBtn);
		}
	}

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

	public void clearPizza(ActionEvent e) {
		toppingObservableList.clear();
		toppingIdArrayList.clear();
		toppingListView.getItems().clear();
	}

	public void cancelPizza(ActionEvent e) {
		if (!modified) {
			removePizza();
		}
		else {
			Order order = NewOrderUI.getOrder();
			order.addPizza(modPizza);
			modPizza = null;
			modified = false;
		}

		toppingObservableList.clear();
		toppingIdArrayList.clear();
		toppingListView.getItems().clear();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancelBtn);
	}

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
			NewOrderUI.removeIngredient(list.get(i), pSize);
		}
	}

	public void start(Stage arg0) throws Exception {
	}

	public static void setPizza(Pizza p) {
		modPizza = p;
		toppingIdArrayList = p.getToppings();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (!toppingIdArrayList.isEmpty()) {
			// modified pizza
			toppingObservableList.addAll(toppingIdArrayList);
			toppingListView.setItems(toppingObservableList);
			modified = true;
		}
	}
}
