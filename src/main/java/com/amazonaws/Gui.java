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
	
	private Inventory inventory;
	private static int orderNum = 1;
	
	@Override
	public void start(Stage stage) throws Exception {
		inventory = new Inventory();
		
		System.out.println("\nBefore: ");
		inventory.printTable("my-inventory-table");
		
		Label nameLbl = new Label("Mark your selection: ");
		
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
        
        Button submitBtn = new Button("Submit");
        Button exitBtn = new Button("Exit");
        
        ArrayList<Pizza> pizzaList = new ArrayList<Pizza>();
        ArrayList<CheckBox> cbList = new ArrayList<CheckBox>();
        String pizzaName = "testPizza";
        
        cbList.add(crustCb);
        cbList.add(sauceCb);
        cbList.add(cheeseCb);
        cbList.add(pepperoniCb);
        cbList.add(greenPepperCb);
        
        submitBtn.setOnAction(e -> { 
        	Pizza p = createPizza(pizzaName, cbList);
        	pizzaList.add(p);
        	System.out.println("\nAfter");
        	inventory.printTable("my-inventory-table");
        });
        
        exitBtn.setOnAction(e -> Platform.exit());
        
        Cart cart = new Cart(orderNum, pizzaList);
        HashMap<Integer, Cart> orders = new HashMap<Integer, Cart>();	// key = orderNumber
        
        // not editing mode
        if(!orders.containsKey(orderNum)) {
        	orders.put(orderNum, cart);
        }
        else {
        	System.out.println("Error: Attempting to replace existing order");
        }
        orderNum++;		// can be reset at the "end of the day"
        
        updateActiveOrders(orders);
        
        VBox root = new VBox();
        
        root.setSpacing(5);
        root.getChildren().addAll(nameLbl, crustCb, sauceCb, 
        		cheeseCb, pepperoniCb, greenPepperCb, submitBtn, exitBtn);

        Scene scene = new Scene(root, 350, 200);
        stage.setScene(scene);
        stage.show();
	}
	
	private void updateActiveOrders(HashMap<Integer, Cart> orders) {
		// iterate through and if status is active, add to active page
		
		// if inactive
	}
	
	private Pizza createPizza(String pizzaName, ArrayList<CheckBox> cbList) {
		Pizza pizza = new Pizza();
		ArrayList<String> toppings = new ArrayList<String>();
		int quantity = 1;
		
		for(CheckBox cb : cbList) {
			if(cb.isSelected()) {
				inventory.decreaseQuantity(cb.getId(), quantity);
				toppings.add(cb.getId());
			}
		}
		
		pizza.addAllToppings(toppings);
		
		return pizza;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
