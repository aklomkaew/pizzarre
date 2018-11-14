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
    private Button mainMenu;
    @FXML
    private Button drink;
    @FXML
    private Button special;
    @FXML
    private Button custom;
    @FXML
    private Button discount;
	
public void goToMainMenu (ActionEvent e) {
    	
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
    NextStage.goTo(fxmlLoader, mainMenu);
}

public void goToDrinks(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrinksUI.fxml"));
	NextStage.goTo(fxmlLoader, drink);
}

public void goToSpecialty(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BuildSpecialtyUI.fxml"));
	NextStage.goTo(fxmlLoader, special);
}

public void goToCustom(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomPizzaUI.fxml"));
	NextStage.goTo(fxmlLoader, custom);
}

public void setDiscount(ActionEvent e) {
	Alert.displayMethodNotSet("setDiscount");
}

public void confirmOrder (ActionEvent e) {
	Alert.displayMethodNotSet("confirmOrder");
}

public void discardOrder (ActionEvent e) {
	Alert.displayMethodNotSet("discardOrder");
}

public void start(Stage arg0) throws Exception {
		
		}
	
}
