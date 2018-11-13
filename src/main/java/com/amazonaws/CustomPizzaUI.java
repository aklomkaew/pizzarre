package com.amazonaws;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class CustomPizzaUI {
	
	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private String pizzaSize = null;
	@FXML
	private ToggleButton smallBtn;
	@FXML
	private ToggleButton mediumBtn;
	@FXML
	private ToggleButton largeBtn;
	@FXML
	private CheckBox pepperoni;
	@FXML
	private CheckBox sausage;
	@FXML
	private CheckBox groundBeef;
	@FXML
	private CheckBox ham;
	@FXML
	private CheckBox chicken;
	@FXML
	private CheckBox steak;
	@FXML
	private CheckBox anchovy;
	@FXML
	private CheckBox shrimp;
	@FXML
	private CheckBox tofu;
	@FXML
	private CheckBox mushroom;
	@FXML
	private CheckBox onion;
	@FXML
	private CheckBox greenPepper;
	@FXML
	private CheckBox tomato;
	@FXML
	private CheckBox olive;
	@FXML
	private CheckBox pineapple;
	@FXML
	private ArrayList<String> toppings = new ArrayList<String>();
	@FXML
	private String toppingName = null;
    
    
	
	public void selectSize (ActionEvent e) {
		pizzaSize = ((ToggleButton)e.getSource()).getText();
	}
	
	public void addToppings (ActionEvent e) {
		if (((CheckBox)e.getSource()).isSelected())
		{
			toppingName =((CheckBox)e.getSource()).getText();
			toppings.add(toppingName);
		} else {
			toppingName =((CheckBox)e.getSource()).getText();
			toppings.remove(toppingName);
		}
	}
	
	public void confirmPizza (ActionEvent e) {
		//add pizza to order here
		if(pizzaSize == null) {
			Alert.Display("ERROR", "Select a size.");
		} else {
		System.out.println("You chose " + pizzaSize + " size pizza with the following toppings: " + toppings);
		
		try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		            Parent root = (Parent) fxmlLoader.load();
		            Stage newOrderStage = new Stage();
		            newOrderStage.setScene(new Scene(root));
		            newOrderStage.setTitle("Order Screen");
		            newOrderStage.show();
		            Stage pizzaStage = (Stage) confirmBtn.getScene().getWindow();
		            pizzaStage.close();
		    } catch(Exception exception) {
		       exception.printStackTrace();
		      }
		}
	}
	
public void cancelPizza (ActionEvent e) {
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage newOrderStage = new Stage();
	            newOrderStage.setScene(new Scene(root));
	            newOrderStage.setTitle("Order Screen");
	            newOrderStage.show();
	            Stage pizzaStage = (Stage) cancelBtn.getScene().getWindow();
	            pizzaStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
	}

public void start(Stage arg0) throws Exception {
		}
	
}
