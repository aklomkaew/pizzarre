package com.amazonaws;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.util.ArrayList;

import javafx.geometry.*;

public class Alert {
	
	
	public static void Display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e->window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,closeButton);
		layout.setAlignment(Pos.CENTER);
		
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait(); //This means the window has to be closed to continue.
	}
	
	public static void displayMethodNotSet(String methodName)
	{
		Alert.Display(methodName, "Method not set.");
	}
	
	public static void displayIntegration(String methodName)
	{
		Alert.Display("Integration required", methodName + " requires integration.");
	}
	
	public static void displayToppings(String pizzaName, ArrayList<String> toppings) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(pizzaName);
		window.setMinWidth(400);
		
		Label label = new Label();
		String toppingString = null;
		
		for(int i = 0; i < toppings.size()-1; i++)
		{
			toppingString = toppingString + toppings.get(i);
			toppingString = toppingString + ", ";
		}
		toppingString = toppingString + toppings.get(toppings.size()-1); // toppingString displays as follows this example "pepperoni, ham, greenPepper, onion"
		
		label.setText(toppingString);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e->window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,closeButton);
		layout.setAlignment(Pos.CENTER);
		
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait(); //This means the window has to be closed to continue.
	}
}
