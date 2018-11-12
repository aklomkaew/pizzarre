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
public class NewOrderUI {
	
	
    @FXML
    private Button mainMenuBtn;
    @FXML
    private Button drinkBtn;
    @FXML
    private Button specialtyBtn;
    @FXML
    private Button customBtn;
	
    public void goToMainMenu (ActionEvent e) {
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage mainMenuStage = new Stage();
	            mainMenuStage.setScene(new Scene(root));
	            mainMenuStage.setTitle("Menu");
	            mainMenuStage.show();
	            Stage newOrderStage = (Stage) mainMenuBtn.getScene().getWindow();
	            newOrderStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
}

public void goToDrinks(ActionEvent e) {
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrinksUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage drinkStage = new Stage();
	            drinkStage.setScene(new Scene(root));
	            drinkStage.setTitle("Drinks");
	            drinkStage.show();
	            Stage newOrderStage = (Stage) drinkBtn.getScene().getWindow();
	            newOrderStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
	
}

public void goToSpecialty(ActionEvent e) {
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage drinkStage = new Stage();
	            drinkStage.setScene(new Scene(root));
	            drinkStage.setTitle("Specials");
	            drinkStage.show();
	            Stage newOrderStage = (Stage) specialtyBtn.getScene().getWindow();
	            newOrderStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
	
}

public void goToCustom(ActionEvent e) {
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomPizzaUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage customStage = new Stage();
	            customStage.setScene(new Scene(root));
	            customStage.setTitle("Custom");
	            customStage.show();
	            Stage newOrderStage = (Stage) customBtn.getScene().getWindow();
	            newOrderStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
	
}

public void start(Stage arg0) throws Exception {
		
		}
	
}
