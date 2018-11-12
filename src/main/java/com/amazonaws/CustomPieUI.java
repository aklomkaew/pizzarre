package com.amazonaws;


import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class CustomPieUI extends Application {
	
	
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
    private Button beef;
    @FXML
    private Button ham;
    @FXML
    private Button bacon;
    @FXML
    private Button chicken;
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
    private Button greenPeppr;
    @FXML
    private Button tomato;
    @FXML
    private Button olive;
    @FXML
    private Button pineapple;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private Button logOut;
	
 

public void addTopping(ActionEvent e) {
	Button btn = (Button) e.getSource();
	String id = btn.getId();
	System.out.println(id);
	
	// Now, add 'id' item to the pizza.
	
}

public void startPizza(ActionEvent e) {
	Button btn = (Button) e.getSource();
	String id = btn.getId();
	System.out.println(id);
	
	switch(id){
		case("small"):
			//add small to order
			break;
		case("medium"):
			//add medium to order
			break;
		default:
			//add large to order
	}
	
}
public void confirmOrder(ActionEvent e) {
	
}
public void discardOrder(ActionEvent e) {
	//Go back to main menu
}
public void logout(ActionEvent e) {
	
}

	@Override
	public void start(Stage stage) throws Exception {
		
		
	}
	
}
