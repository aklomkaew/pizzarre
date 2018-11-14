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
public class MainMenuUI extends Application{
	
	
    @FXML
    private Button newOrd;
    @FXML
    private Button myOrds;
    @FXML
    private Button mUtil;
    @FXML
    private Button logOutBtn;
	

public void goToNewOrder(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
	NextStage.goTo(fxmlLoader, newOrd);
	}

public void goToMyOrders(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyOrdersUI.fxml"));
	NextStage.goTo(fxmlLoader, myOrds);      
}
	
public void goToManagerUtilities(ActionEvent e) {
	
	//if (manager) {
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
	NextStage.goTo(fxmlLoader, mUtil);
	//}else{
	//Alert.Display("Error", "Manager credentials required.");
	//}
}

public void logout (ActionEvent event){
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginUI.fxml"));
	NextStage.goTo(fxmlLoader, logOutBtn);
}

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
}
