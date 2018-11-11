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
public class CreateRecipeUI extends Application {
	
	
    
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
	@FXML
	private String name;
 

public void addTopping(ActionEvent e) {
	Button btn = (Button) e.getSource();
	String id = btn.getId();
	System.out.println(id);
	
	// Now, add 'id' item to the recipe.
	
}
public void nameRecipe(ActionEvent e) {
	
	//Pulls the string from the text field, and creates the empty list or hash for ingredients 
}

public void confirmRecipe(ActionEvent e) {
	//This adds the recipe to the database
	
	/*		This will also need to add a button for the new recipe on the
	 * 		specialty page's gridpane.
	*/
	 
}
public void discardRecipe(ActionEvent e) {
	//Go back to manager menu
}
public void logout(ActionEvent e) {
	
}

	@Override
	public void start(Stage stage) throws Exception {
		
		
	}
	
}
