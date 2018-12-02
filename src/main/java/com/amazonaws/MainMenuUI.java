package com.amazonaws;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class MainMenuUI {
	
    @FXML
    private Button newOrd;
    @FXML
    private Button myOrds;
    @FXML
    private Button mUtil;
    @FXML
    private Button logOutBtn;
	
    public void goToNewOrder(ActionEvent e) {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, newOrd);
	}

    public void goToMyOrders(ActionEvent e) {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyOrdersUI.fxml"));
    	NextStage.goTo(fxmlLoader, myOrds);      
    }
	
    public void goToManagerUtilities(ActionEvent e) {
    	
    	if(LoginUI.getUser().isManager()) {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
    		NextStage.goTo(fxmlLoader, mUtil);
    	} else {
    		Alert.Display("Error", "Manager credentials required.");
    	}
    }

    public void logout (ActionEvent event){
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginUI.fxml"));
    	NextStage.goTo(fxmlLoader, logOutBtn);	
    }	
}
