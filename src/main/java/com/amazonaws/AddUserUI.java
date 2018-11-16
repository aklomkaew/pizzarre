package com.amazonaws;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddUserUI extends Application {
	
	@FXML
	private Button confirmBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TextField userNameTF;
	@FXML
	private TextField idTF;
	
	public void checkUser(ActionEvent e) {
		try {
			Integer.parseInt(idTF.getText());
			makeUser(e);
		} catch (NumberFormatException ex) {
			Alert.Display("Error", "Id must be a number");
		}
	}
	
	public void makeUser (ActionEvent e) {
		String userName = userNameTF.getText();
		int userId = Integer.parseInt(idTF.getText()); // userName and userId are legitimate at this line
		
		goToAllUsers(e);
	}
	
	public void goToAllUsers (ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllUsersUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}


	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}
