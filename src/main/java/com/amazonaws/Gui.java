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

@SuppressWarnings({ "restriction", "unused" })
public class Gui extends Application {
	
	//private Inventory_old inventory;
	
	private Inventory inventory;
	
	@Override
	public void start(Stage stage) throws Exception {
		inventory = new Inventory();
		
		System.out.println("\nBefore: ");
		inventory.printTable();
		
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
        
        submitBtn.setOnAction(e -> { 
        	handleCheckBoxes(crustCb, sauceCb, cheeseCb, pepperoniCb, greenPepperCb);
        	System.out.println("\nAfter");
        	inventory.printTable();
        });
        
        exitBtn.setOnAction(e -> Platform.exit());
                
        VBox root = new VBox();
        
        root.setSpacing(5);
        root.getChildren().addAll(nameLbl, crustCb, sauceCb, 
        		cheeseCb, pepperoniCb, greenPepperCb, submitBtn, exitBtn);

        Scene scene = new Scene(root, 350, 200);
        stage.setScene(scene);
        stage.show();
	}
	
	private void handleCheckBoxes(CheckBox crust, CheckBox sauce, CheckBox cheese, 
			CheckBox pepperoni, CheckBox greenPepper) {
		
		int quantity = 1;
		
		if(crust.isSelected()) {
			inventory.decreaseQuantity(crust.getId(), quantity);
		}
		if(sauce.isSelected()) {
			inventory.decreaseQuantity(sauce.getId(), quantity);
		}
		if(cheese.isSelected()) {
			inventory.decreaseQuantity(cheese.getId(), quantity);
		}
		if(pepperoni.isSelected()) {
			inventory.decreaseQuantity(pepperoni.getId(), quantity);
		}
		if(greenPepper.isSelected()) {
			inventory.decreaseQuantity(greenPepper.getId(), quantity);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
