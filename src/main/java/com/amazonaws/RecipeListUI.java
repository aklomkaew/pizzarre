package com.amazonaws;


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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class RecipeListUI extends Application {
	
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

public void deleteRecipe(ActionEvent e) {
	//String recipeName = recipeListView.getSelectionModel().getSelectedItem();
	//recipeItem = RecipeDB.getRecipe(recipeName)
	//RecipeDB.delete(recipeItem);
	//recipeListView.setItems(recipeObservableList);
}
public void viewRecipe(ActionEvent e) {
	
	Alert.displayMethodNotSet("viewRecipe");
}
public void goToManagerUtilities(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
	NextStage.goTo(fxmlLoader, backBtn);
}

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
}
