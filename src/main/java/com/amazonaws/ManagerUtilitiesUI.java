package com.amazonaws;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Represents interface to navigate to specific program manager functionality
 * 
 * @author Christopher
 *
 */
@SuppressWarnings("restriction")
public class ManagerUtilitiesUI {

	@FXML
	private Button allOrderBtn;
	@FXML
	private Button employeeBtn;
	@FXML
	private Button recipeBtn;
	@FXML
	private Button inventoryBtn;
	@FXML
	private Button backBtn;

	/**
	 * Display AllOrdersUI stage and closes the current (ManagerUtilitiesUI) stage
	 */
	public void goToAllOrders() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllOrdersUI.fxml"));
		NextStage.goTo(fxmlLoader, allOrderBtn);
	}

	/**
	 * Display AllUsersUI stage and closes the current (ManagerUtilitiesUI) stage
	 */
	public void goToEmployeeList() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllUsersUI.fxml"));
		NextStage.goTo(fxmlLoader, employeeBtn);
	}

	/**
	 * Display RecipeListUI stage and closes the current (ManagerUtilitiesUI) stage
	 */
	public void goToRecipeList() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecipeListUI.fxml"));
		NextStage.goTo(fxmlLoader, recipeBtn);
	}

	/**
	 * Display InventoryUI stage and closes the current (ManagerUtilitiesUI) stage
	 */
	public void goToInventory() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryUI.fxml"));
		NextStage.goTo(fxmlLoader, inventoryBtn);
	}

	/**
	 * Display MainMenuUI stage and closes the current (ManagerUtilitiesUI) stage
	 */
	public void goToMainMenu() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}
}
