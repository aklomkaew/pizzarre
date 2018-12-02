package com.amazonaws;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Represents interface to navigate to specific program functionality
 * @author Christopher
 *
 */

public class MainMenuUI {
	
    @FXML
    private Button newOrd;
    @FXML
    private Button myOrds;
    @FXML
    private Button mUtil;
    @FXML
    private Button logOutBtn;
	
	/**
	 * Display AllOrdersUI stage and closes the current (MainMenuUI) stage
	 */
    
    public void goToNewOrder() {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, newOrd);
	}

	/**
	 * Display MyOrdersUI stage and closes the current (MainMenuUI) stage
	 */
    
    public void goToMyOrders() {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyOrdersUI.fxml"));
    	NextStage.goTo(fxmlLoader, myOrds);      
    }
	
	/**
	 * Display ManagerUtilitiesUI stage and closes the current (MainMenuUI) stage
	 * Only works if user is a manager
	 */
    
    public void goToManagerUtilities() {
    	
    	if(LoginUI.getUser().isManager()) {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
    		NextStage.goTo(fxmlLoader, mUtil);
    	} else {
    		Alert.Display("Error", "Manager credentials required.");
    	}
    }

	/**
	 * Display loginUI stage and closes the current (MainMenuUI) stage
	 */
    
    public void logout() {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginUI.fxml"));
    	NextStage.goTo(fxmlLoader, logOutBtn);	
    }	
}
