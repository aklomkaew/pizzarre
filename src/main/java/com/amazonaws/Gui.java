package com.amazonaws;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.*;

@SuppressWarnings({ "restriction", "unused" })
public class Gui extends Application {

	private static int orderNum = 1;
	private Cart order;
	private HashMap<Integer, Cart> orders; // key = orderNumber

	@Override
	public void start(Stage stage) throws Exception {
		InventoryDb inventoryDb = new InventoryDb();
		RecipeDb recipeDb = new RecipeDb();
		UserDb userDb = new UserDb();
		CartDb cartDb = new CartDb();

		addMockData();
		
		User u = new User();
		u.setUserId(12);
		u.setName("Patrick");

		Label nameLbl = new Label("Mark your selection: ");
		ArrayList<CheckBox> cbList = setCheckBoxes();
		Button submitBtn = new Button("Submit");
		Button exitBtn = new Button("Exit");

		//ArrayList<Order> activeOrders = new ArrayList<Order>();
		String pizzaName = "basePizza";
		orders = new HashMap<Integer, Cart>();
		// size: S = 1, M = 2, L = 3
		int size = 1;

		submitBtn.setOnAction(e -> {
			// check if creating customized or premade pizza
			Pizza p = createCustomizedPizza(pizzaName, size, cbList);
			ArrayList<Pizza> pizzaList = new ArrayList<Pizza>();
			pizzaList.add(p);
//			System.out.println("\nAfter");
//			inventoryDb.printTable("my-inventory-table");
//			System.out.println();

			order = new Cart(orderNum, pizzaList);
			// order.addPizza(p);
			orders.put(orderNum, order);

			orderNum++;

			for (int k : orders.keySet()) {
				System.out.println(orders.get(k).toString());
				// orders.get(k).printOrder();
			}

			ArrayList<Cart> activeOrders = updateActiveOrders(orders);
			// display the activeOrders somewhere
			// should we store activeOrders in db?
		});

		exitBtn.setOnAction(e -> Platform.exit());

//        // not editing mode
//        if(!orders.containsKey(orderNum)) {
//        	orders.put(orderNum, order);
//        }
//        else {
//        	System.out.println("Error: Attempting to replace existing order");
//        }
//        orderNum++;		// can be reset at the "end of the day"
//        

		VBox root = new VBox();

		root.setSpacing(5);
		root.getChildren().add(nameLbl);
		for (CheckBox cb : cbList) {
			root.getChildren().add(cb);
		}
		root.getChildren().addAll(submitBtn, exitBtn);

		Scene scene = new Scene(root, 350, 200);
		stage.setScene(scene);
		stage.show();
	}

	private ArrayList<CheckBox> setCheckBoxes() {
		Tooltip tooltip = new Tooltip("$ tooltip");
		tooltip.setFont(new Font("Arial", 16));

		final CheckBox crustCb = new CheckBox("Crust");
		crustCb.setId("crust");
		crustCb.setTooltip(tooltip);

		final CheckBox sauceCb = new CheckBox("Sauce");
		sauceCb.setId("sauce");
		sauceCb.setTooltip(tooltip);

		final CheckBox cheeseCb = new CheckBox("Cheese");
		cheeseCb.setId("cheese");
		cheeseCb.setTooltip(tooltip);

		final CheckBox pepperoniCb = new CheckBox("Pepperoni");
		pepperoniCb.setId("pepperoni");
		pepperoniCb.setTooltip(tooltip);

		final CheckBox greenPepperCb = new CheckBox("Green Pepper");
		greenPepperCb.setId("greenPepper");
		greenPepperCb.setTooltip(tooltip);

		ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();
		cbList.add(crustCb);
		cbList.add(sauceCb);
		cbList.add(cheeseCb);
		cbList.add(pepperoniCb);
		cbList.add(greenPepperCb);

		return cbList;
	}

	private ArrayList<Cart> updateActiveOrders(HashMap<Integer, Cart> orders) {
		ArrayList<Cart> ret = new ArrayList<Cart>();
		for (int k : orders.keySet()) {
			if (orders.get(k).getState()) {
				ret.add(orders.get(k));
			}
		}
		return ret;
	}

	private Pizza createCustomizedPizza(String pizzaName, int size, ArrayList<CheckBox> cbList) {
		Pizza pizza = new Pizza();
		HashMap<String, Integer> toppings = new HashMap<String, Integer>();
		pizza.setSize(size);
		pizza.setName(pizzaName);
		int quantity = 1;

		for (CheckBox cb : cbList) {
			if (cb.isSelected()) {
				InventoryDb.decreaseQuantity(cb.getId(), quantity);
				pizza.addTopping(cb.getId(), quantity);
			}
		}

		return pizza;
	}

	private Pizza createPremadePizza(String pizzaName) {
		Pizza pizza = new Pizza();
		// call recipe
		return pizza;
	}

	private Pizza addMoreToppingToPremade(String pizzaName) {
		Pizza pizza = new Pizza();

		return pizza;
	}
	
	private void addMockData() {
		InventoryDb.initTable();
		RecipeDb.initTable();
		CartDb.initTable();
		UserDb.initTable();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
