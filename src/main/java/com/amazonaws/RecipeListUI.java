package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class RecipeListUI extends Application implements Initializable {

	@FXML
	private Button addRecipeBtn;
	@FXML
	private Button deleteRecipeBtn;
	@FXML
	private Button viewRecipeBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TableView<RecipeItem> recipeTableView;
	@FXML
	private TableColumn<RecipeItem, String> nameColumn;
	@FXML
	//private TableColumn<RecipeItem, String> ingredientsColumn;
	private TableColumn<RecipeItem, ArrayList<String>> ingredientsColumn;

	private ObservableList<RecipeItem> recipeObservableList;

	public void addRecipe(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateRecipeUI.fxml"));
		NextStage.goTo(fxmlLoader, addRecipeBtn);
	}

	public void deleteRecipe(ActionEvent e) {
		RecipeItem itemToDelete = recipeTableView.getSelectionModel().getSelectedItem();
		if(itemToDelete == null) {
			Alert.Display("Error", "Select a recipe to delete.");
		}
		
		recipeObservableList.remove(itemToDelete);
		recipeTableView.setItems(recipeObservableList);

		RecipeDb.deleteItem(itemToDelete.getName());
		displayAllRecipe();
	}

	public void viewRecipe(ActionEvent e) {

		Alert.displayMethodNotSet("viewRecipe");
	}

	public void displayAllRecipe() {
		List<RecipeItem> list = RecipeDb.retrieveAllItem();

		if (list == null || list.size() < 1) {
			return;
		}

		recipeObservableList.clear();
		recipeObservableList.addAll(list);
		System.out.println("\nPrint from observableList");
		for (RecipeItem item : recipeObservableList) {
			System.out.println("Recipe name = " + item.getName());
		}
	}

	public void goToManagerUtilities(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	@Override
	public void start(Stage arg0) throws Exception {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { // initializes populates ist with current users
		nameColumn.setCellValueFactory(new PropertyValueFactory<RecipeItem, String>("name"));
		//ingredientsColumn.setCellValueFactory(new PropertyValueFactory<RecipeItem, String>("name"));
		ingredientsColumn.setCellValueFactory(new PropertyValueFactory<RecipeItem, ArrayList<String>>("ingredients"));
		recipeObservableList = FXCollections.observableArrayList();
		recipeTableView.setItems(recipeObservableList);
		displayAllRecipe();
	}

}
