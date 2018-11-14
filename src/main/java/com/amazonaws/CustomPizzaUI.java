package com.amazonaws;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class CustomPizzaUI {
	
	@FXML
	private Button confirmBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private String pizzaSize = null;
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
	
	private ObservableList<String> toppingObservableList = FXCollections.observableArrayList();
	
	private ArrayList<String> toppingIdArrayList = new ArrayList<String>();
	
    private static final int SMALL = 1;
    private static final int MEDIUM = 2;
    private static final int LARGE = 3;
    
	
	public void selectSize (ActionEvent e) {
		pizzaSize = ((Button)e.getSource()).getId();
	}
	
	public void addTopping (ActionEvent e) {
		
		String id = ((Button)e.getSource()).getId();
		String toppingName = ((Button)e.getSource()).getText();
		
		if(toppingIdArrayList.contains(id) == false) { //if statements adds topping to the list
			
			System.out.println(id + " added");
			toppingIdArrayList.add(id); // USE THIS LIST FOR INVENTORY NAMES (i.e. greenPepper, NOT Green Pepper)
			toppingObservableList.add(toppingName); //list used to display topping names
		
		} else { // else statement removes topping from the list
			
			System.out.println(id + " removed");
			toppingIdArrayList.remove(id); // USE THIS LIST FOR INVENTORY NAMES (i.e. greenPepper, NOT Green Pepper)
			toppingObservableList.remove(toppingName); //list used to display topping names
		
		}
		
		toppingListView.setItems(toppingObservableList); //displays toppings in the list
	}
	
	public void confirmPizza (ActionEvent e) {
		//add pizza to order here
		if(pizzaSize == null) {
			Alert.Display("ERROR", "Select a size.");
		} else {	
			System.out.println("You chose a " + pizzaSize + " pizza with the following toppings: " + toppingObservableList);
			System.out.println("User id = " + LoginUI.getUserId());
			
			for(int i = 0; i < toppingObservableList.size(); i++) {
				String str = toppingObservableList.get(i).toLowerCase();
				int pSize = getpSize(pizzaSize);
				if(InventoryDb.getQuantityOfItem(str) < pSize) {
					// print out error, cannot get pizza
				}
				else {
					// can get pizza, add it to list
				}
			}
			
			
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, confirmBtn);
		}
	}
	
	private int getpSize(String str) {
		if(str == "small") {
			return SMALL;
		}
		else if(str == "medium") {
			return MEDIUM;
		}
		else if(str == "large") {
			return LARGE;
		}
		return -1;	// should never get here
	}
	
	public void clearPizza(ActionEvent e) {
		toppingObservableList.clear();
		toppingIdArrayList.clear();
		toppingListView.getItems().clear();
	}
	
public void cancelPizza (ActionEvent e) {
	
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
	NextStage.goTo(fxmlLoader, cancelBtn);
	}

public void start(Stage arg0) throws Exception {
		}
	
}
