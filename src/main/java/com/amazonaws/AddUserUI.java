package com.amazonaws;

import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

@SuppressWarnings("restriction")
public class AddUserUI {

	@FXML
	private Button confirmBtn;
	@FXML
	private Button backBtn;
	@FXML
	private CheckBox managerCheckBox;
	@FXML
	private TextField userNameTF;

	public void addUser() {
		String userName = userNameTF.getText();
		if (userName == null || userName.length() == 0) {
			Alert.Display("Error", "Please enter the employee name.");
			return;
		}

		List<User> list = UserDb.retrieveAllItem();
		int id = list.size();
		User u = new User(id, userName);
		
		if(managerCheckBox.isSelected()) {
			u.setManager(true);
		}
		
		while (!UserDb.addUser(id, u)) {
			id++;
			u.setUserId(id);
		}

		Alert.Display("Success", (managerCheckBox.isSelected() ? "Manager " : "Employee ") + userName + " has been added. Id is " + u.getUserId());
		goToAllUsers();
	}

	public void cancelNewUser() {
		Alert.Display("Information", "Leaving page without saving");
		goToAllUsers();
	}

	public void goToAllUsers() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllUsersUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}
}
