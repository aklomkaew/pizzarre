package com.amazonaws;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AllUsersUI implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button addUserBtn;
	@FXML
	private Button deleteUserBtn;
	@FXML
	private Button deleteAllUserBtn;
	@FXML
	private Button refreshBtn;
	@FXML
	private TableView<User> userTableView;
	@FXML
	private TableColumn<User, String> nameColumn;
	@FXML
	private TableColumn<User, Integer> idColumn;

	private ObservableList<User> userObservableList;

	public void deleteUser(ActionEvent e) {
		
		User userToDelete = userTableView.getSelectionModel().getSelectedItem();
		if (userToDelete == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select a user to delete.");
			alert.showAndWait();
			return;
		}
		
		if (userToDelete.getUserId() == LoginUI.getUser().getUserId()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("You cannot delete yourself!");
			alert.showAndWait();
			return;
		}

		userObservableList.remove(userToDelete);
		userTableView.setItems(userObservableList);

		UserDb.deleteUser(userToDelete.getUserId());
		displayAllUser();
	}

	public void deleteAllUser(ActionEvent e) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete all user?");

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			List<User> list = UserDb.retrieveAllItem();
			for (User item : list) {
				if (item.getUserId() != LoginUI.getUser().getUserId()) {
					UserDb.deleteUser(item.getUserId());
				}
			}
			displayAllUser();
		}
	}

	public void displayAllUser() {
		
		List<User> list = UserDb.retrieveAllItem();
		if (list == null || list.size() < 1) {
			return;
		}

		userObservableList.clear();
		userObservableList.addAll(list);
		
		System.out.println("\nPrint from observableList");
		for (User item : userObservableList) {
			System.out.println("Id = " + item.getUserId() + " name = " + item.getName());
		}
	}

	public void goToAddUser() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddUserUI.fxml"));
		NextStage.goTo(fxmlLoader, addUserBtn);
	}

	public void goToManagerUtilities() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
		userObservableList = FXCollections.observableArrayList();
		userTableView.setItems(userObservableList);
		
		displayAllUser();
	}

}
