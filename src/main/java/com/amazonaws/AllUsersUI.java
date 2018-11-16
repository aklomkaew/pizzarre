package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class AllUsersUI extends Application implements Initializable {
  
  @FXML
	private Button backBtn;
	@FXML
	private Button addUserBtn;
	@FXML
	private Button deleteUserBtn;
  @FXML
  private Button deleteEveryUserBtn;
	@FXML
	private TableView<User> userTableView;
	@FXML
	private TableColumn<User, String> nameColumn;
	@FXML
	private TableColumn<User, Integer> idColumn;

	private ObservableList<User> userObservableList;

	public void addUser(ActionEvent e) {
		Alert.displayMethodNotSet("addUser");
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddUserUI.fxml"));
    NextStage.goTo(fxmlLoader, addUserBtn);
		// take to a page to enter user information
		// in that page, have submit button and make sure they enter everything before submission
	}

	public void deleteUser(ActionEvent e) {
		User userToDelete = userTableView.getSelectionModel().getSelectedItem();
		if(userToDelete == null) {
			Alert.Display("Error", "Select a user to delete.");
			return;
		}
		if(userToDelete.getUserId() == LoginUI.getUser().getUserId()) {
			Alert.Display("Error", "You cannot delete yourself!");
			return;
		}
		
		userObservableList.remove(userToDelete);
		userTableView.setItems(userObservableList);

		UserDb.deleteUser(userToDelete.getUserId());
		// after delete, do retrieve all users again
		displayAllUser();
	}
    
  public void deleteEveryUser(ActionEvent e) {

  }

	public void goToManagerUtilities(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	@Override
	public void start(Stage arg0) throws Exception {

	}
	
	public void displayAllUser() {
		List<User> list = UserDb.retrieveAllItem();
		
		if(list == null || list.size() < 1) {
			return;
		}
		
		userObservableList.clear();
		userObservableList.addAll(list);
		System.out.println("\nPrint from observableList");
		for (User item : userObservableList) {
			System.out.println("Id = " + item.getUserId() + " name = " + item.getName());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { // initializes populates ist with current users
		nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
		userObservableList = FXCollections.observableArrayList();
		userTableView.setItems(userObservableList);
		displayAllUser();
	}

}
