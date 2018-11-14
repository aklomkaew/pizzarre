package com.amazonaws;


import java.io.IOException;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class CreateRecipeUI extends Application {
	
	
    @FXML
    private Button backBtn;
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
    private Button confirmBtn;
    @FXML
    private Button clearBtn;
	@FXML
	private TextField recipeNameTF;
	@FXML
	private ListView<String> toppingListView = new ListView<String>();
	
	private ObservableList<String> toppingObservableList = FXCollections.observableArrayList();
	
	private ArrayList<String> toppingIdArrayList = new ArrayList<String>();
	
 

	public void addRemoveTopping(ActionEvent e) {
	
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

public void confirmRecipe(ActionEvent e) {
	//This adds the recipe to the database
	
	/*		This will also need to add a button for the new recipe on the
	 * 		specialty page's gridpane.
	*/
	String recipeName = recipeNameTF.getText();
	System.out.println("The recipe name is " + recipeName + " and the toppings are: " + toppingObservableList);
	recipeNameTF.clear();
	toppingIdArrayList.clear();
	toppingObservableList.clear();
	toppingListView.setItems(toppingObservableList);
	// make a recipeItem with recipeName and toppingIdArrayList
	// RecipeDB.addRecipe(recipeItem);
}

public void clearRecipe(ActionEvent e) {
	recipeNameTF.clear();
	toppingObservableList.clear();
	toppingListView.getItems().clear();
}

public void goToRecipeList(ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecipeListUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage recipeListStage = new Stage();
	            recipeListStage.setScene(new Scene(root));
	            recipeListStage.show();
	            Stage createRecipeStage = (Stage) backBtn.getScene().getWindow();
	            createRecipeStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
}

	@Override
	public void start(Stage stage) throws Exception {
		
		
	}
	
}
