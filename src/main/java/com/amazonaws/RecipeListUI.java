package com.amazonaws;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class RecipeListUI extends Application implements Initializable{
	
	@FXML
    private Button addRecipeBtn;
	@FXML
    private Button deleteRecipeBtn;
    @FXML
    private Button viewRecipeBtn;
	@FXML
	private Button backBtn;
	@FXML
	private ListView<String> recipeListView = new ListView<String>();
	
	private ObservableList<String> recipeObservableList = FXCollections.observableArrayList();
 



public void addRecipe(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateRecipeUI.fxml"));
	NextStage.goTo(fxmlLoader, addRecipeBtn);
}

public void deleteRecipe(ActionEvent e) { //index in Observable List will match index in RecipeDb
	
	Alert.displayIntegration("deleteRecipe");
	
	String recipeName = recipeListView.getSelectionModel().getSelectedItem();
	int index = -1;
	index = recipeListView.getSelectionModel().getSelectedIndex();
	
	//RecipeItem recipeToDelete = RecipeDB.get(index)
	//RecipeDB.delete(index);
	
	System.out.println(index);
	
	recipeObservableList.remove(recipeName);
	recipeListView.setItems(recipeObservableList);
}

public void viewRecipe(ActionEvent e) { //index in Observable List will match index in RecipeDb
	int index = -1;
	index = recipeListView.getSelectionModel().getSelectedIndex();
	
	System.out.println(index);
	
	//RecipeItem recipeItem = RecipeDb.get(index)
	//Alert.displayToppings(recipeItem.getName(), recipeItem.getToppings();
	
	Alert.displayIntegration("viewRecipe");
}

public void goToManagerUtilities(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
	NextStage.goTo(fxmlLoader, backBtn);
}

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
//		 for (int i = 0; i < RecipeDb.size(); i++)
//		 {
//			 recipeObservableList.add(RecipeDb.get(i).getRecipeName();
//		 }

		recipeListView.setItems(recipeObservableList);
		
	}
	
}
