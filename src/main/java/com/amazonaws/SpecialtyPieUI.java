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
public class SpecialtyPieUI extends Application {
	
	
    @FXML
    private Button small;
    @FXML
    private Button medium;
    @FXML
    private Button large;
    @FXML
    private Button daily;
    @FXML
    private Button meat;
    @FXML
    private Button hawaiian;
    @FXML
    private Button classic;
    @FXML
    private Button veggie;
    @FXML
    private Button sicilian;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private Button logOut;
	
 

public void addToppings(ActionEvent e) {
	Button btn = (Button) e.getSource();
	String id = btn.getId();
	System.out.println(id);
	
	switch(id){
	case("daily"):
		//add toppings to pizza
		break;
	case("meat"):
		//add toppings to pizza
		break;
	case("hawaiian"):
		//add toppings to pizza
		break;
	case("classic"):
		//add toppings to pizza
		break;
	case("veggie"):
		//add toppings to pizza
		break;
	default:
		//add sicilian toppings to pizza
}
	
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
