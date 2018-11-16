package com.amazonaws;

import java.util.List;

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
	
	public void addUser (ActionEvent e) {
		String userName = userNameTF.getText();
		if(userName == null || userName.length() == 0) {
			Alert.Display("Error", "Please enter the employee name.");
			return;
		}
		// if manager is true, then add as a manager
		List<User> list = UserDb.retrieveAllItem();
		int id = list.size();
		User u = new User(id, userName);
		while(!UserDb.addUser(id, u)) {
			id++;
			u.setUserId(id);
		}
		
		Alert.Display("Success", "User " + userName + " has been added. Id is " + u.getUserId());
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllUsersUI.fxml"));
		NextStage.goTo(fxmlLoader, confirmBtn);
	}
	
	public void goToAllUsers (ActionEvent e) {
		Alert.Display("Information", "Leaving page without saving");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllUsersUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}


	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}
