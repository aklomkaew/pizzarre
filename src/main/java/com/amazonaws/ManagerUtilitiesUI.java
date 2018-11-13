package com.amazonaws;


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
public class ManagerUtilitiesUI extends Application {
	
	
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
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllOrdersUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage allOrdersStage = new Stage();
	            allOrdersStage.setScene(new Scene(root));
	            allOrdersStage.setTitle("Main Menu");
	            allOrdersStage.show();
	            Stage managerUtilitiesStage = (Stage) recipeBtn.getScene().getWindow();
	            managerUtilitiesStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
}
public void goToEmployeeList(ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllUsersUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage allUsersStage = new Stage();
	            allUsersStage.setScene(new Scene(root));
	            allUsersStage.setTitle("Main Menu");
	            allUsersStage.show();
	            Stage managerUtilitiesStage = (Stage) recipeBtn.getScene().getWindow();
	            managerUtilitiesStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
}
public void goToRecipeList(ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RecipeListUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage recipeListUI = new Stage();
	            recipeListUI.setScene(new Scene(root));
	            recipeListUI.setTitle("Main Menu");
	            recipeListUI.show();
	            Stage managerUtilitiesStage = (Stage) recipeBtn.getScene().getWindow();
	            managerUtilitiesStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
}
public void goToInventory(ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage inventory = new Stage();
	            inventory.setScene(new Scene(root));
	            inventory.setTitle("Main Menu");
	            inventory.show();
	            Stage managerUtilitiesStage = (Stage) recipeBtn.getScene().getWindow();
	            managerUtilitiesStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
}
public void goToMainMenu (ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage mainMenuStage = new Stage();
	            mainMenuStage.setScene(new Scene(root));
	            mainMenuStage.setTitle("Main Menu");
	            mainMenuStage.show();
	            Stage managerUtilitiesStage = (Stage) backBtn.getScene().getWindow();
	            managerUtilitiesStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
}
	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
}
