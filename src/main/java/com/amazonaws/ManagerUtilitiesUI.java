package com.amazonaws;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

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

    public void goToAllOrders(ActionEvent e) {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllOrdersUI.fxml"));
    	NextStage.goTo(fxmlLoader, allOrderBtn);
    }

    public void goToEmployeeList(ActionEvent e) {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllUsersUI.fxml"));
    	NextStage.goTo(fxmlLoader, employeeBtn);
    }

    public void goToRecipeList(ActionEvent e) {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecipeListUI.fxml"));
    	NextStage.goTo(fxmlLoader, recipeBtn);
    }

    public void goToInventory(ActionEvent e) {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryUI.fxml"));
    	NextStage.goTo(fxmlLoader, inventoryBtn);
    }

    public void goToMainMenu (ActionEvent e) {
	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
    	NextStage.goTo(fxmlLoader, backBtn);
    }
}
