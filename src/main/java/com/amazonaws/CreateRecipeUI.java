package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Represents interface to add a new recipe to the recipe database
 * 
 * @author Christopher
 *
 */
@SuppressWarnings("restriction")
public class CreateRecipeUI implements Initializable {

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
	private ListView<String> toppingListView;

	private ObservableList<String> toppingObservableList;

	private ArrayList<String> toppingIdArrayList = new ArrayList<String>();

	/**
	 * Adds or removes a topping from the recipe, depending on if the recipe was
	 * already in the topping list
	 * 
	 * @param onClick An ActionEvent to retrieve contents of button selected
	 */
	public void addRemoveTopping(ActionEvent onClick) {

		String id = ((Button) onClick.getSource()).getId();
		String toppingName = ((Button) onClick.getSource()).getText();

		if (toppingIdArrayList.contains(id) == false) {

			System.out.println(id + " added");
			toppingIdArrayList.add(id);
			toppingObservableList.add(toppingName);

		} else {

			System.out.println(id + " removed");
			toppingIdArrayList.remove(id);
			toppingObservableList.remove(toppingName);
		}
	}

	/**
	 * Adds the Recipe object to the recipe database
	 */
	public void confirmRecipe() {

		String recipeName = recipeNameTF.getText();
		if (recipeName == null || recipeName.length() == 0) {
			Alert.Display("Error", "Enter a recipe name");
			return;
		}

		if (toppingObservableList.size() == 0) {
			Alert.Display("Error", "Select at least one ingredient for the recipe");
			return;
		}

		ArrayList<String> list = new ArrayList<String>();
		list.addAll(toppingObservableList);
		RecipeItem item = new RecipeItem(recipeName, list);
		if (RecipeDb.addRecipe(item)) {
			Alert.Display("Success", "Recipe " + recipeName + " has been added");
		} else {
			Alert.Display("Error", "Recipe " + recipeName + " already exists.");
			return;
		}

		System.out.println("The recipe name is " + recipeName + " and the toppings are: " + toppingObservableList);
		recipeNameTF.clear();
		toppingIdArrayList.clear();
		toppingObservableList.clear();
		toppingListView.setItems(toppingObservableList);

		goToRecipeList();
	}

	/**
	 * Clears the name and toppings from the new recipe
	 */
	public void clearRecipe() {

		recipeNameTF.clear();
		toppingObservableList.clear();
		toppingListView.getItems().clear();
	}

	/**
	 * Display RecipeListUI stage and closes the current (CreateRecipeUI) stage
	 */
	public void goToRecipeList() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecipeListUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	/**
	 * Creates and display a a list of current toppings selected
	 * 
	 * @param location  Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		toppingObservableList = FXCollections.observableArrayList();
		toppingListView.setItems(toppingObservableList);
	}

}
