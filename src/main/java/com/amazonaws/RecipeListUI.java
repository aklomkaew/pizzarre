package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Represents interface to view the list of specialty pizzas
 * @author Christopher
 *
 */

public class RecipeListUI implements Initializable {

	@FXML
	private Button addRecipeBtn;
	@FXML
	private Button deleteRecipeBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Button refreshBtn;
	@FXML
	private TableView<RecipeItem> recipeTableView;
	@FXML
	private TableColumn<RecipeItem, String> nameColumn;
	@FXML
	private TableColumn<RecipeItem, ArrayList<String>> ingredientsColumn;

	private ObservableList<RecipeItem> recipeObservableList;

	/**
	 * Display CreateRecipeUI stage and closes the current (RecipeListUI) stage
	 */
	
	public void addRecipe() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateRecipeUI.fxml"));
		NextStage.goTo(fxmlLoader, addRecipeBtn);
	}

	/**
	 * Displays notification of the selected specialty pizza and its toppings
	 */
	
	public void viewRecipe() {
		
		RecipeItem item = recipeTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select a user to view.");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("View Recipe");
		alert.setHeaderText("Recipe " + item.getName() + " contains:");
		alert.setContentText(item.toString());
		alert.showAndWait();
	}

	/**
	 * Removes the selected recipe from the list and database
	 */
	
	public void deleteRecipe() {
		
		RecipeItem itemToDelete = recipeTableView.getSelectionModel().getSelectedItem();
		if (itemToDelete == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select a recipe to delete.");
			alert.showAndWait();
			return;
		}

		recipeObservableList.remove(itemToDelete);
		recipeTableView.setItems(recipeObservableList);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Recipe " + itemToDelete.getName() + " has been deleted.");
		alert.showAndWait();
		RecipeDb.deleteItem(itemToDelete.getName());
		displayAllRecipe();
	}

	/**
	 * Displays the list of current recipes
	 */
	
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

	/**
	 * Display ManagerUtilitiesUI stage and closes the current (RecipeListUI) stage
	 */
	
	public void goToManagerUtilities() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}
	
	/**
	 * Creates a two-column table displaying a RecipeItem's name and  list of toppings an calls {@link #displayAllRecipe()}
	 * @param location Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { // initializes populates ist with current users
		nameColumn.setCellValueFactory(new PropertyValueFactory<RecipeItem, String>("name"));
		ingredientsColumn.setCellValueFactory(new PropertyValueFactory<RecipeItem, ArrayList<String>>("ingredients"));
		recipeObservableList = FXCollections.observableArrayList();
		recipeTableView.setItems(recipeObservableList);
		displayAllRecipe();
	}
}
