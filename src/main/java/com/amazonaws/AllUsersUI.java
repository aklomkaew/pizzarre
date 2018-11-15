package com.amazonaws;


import java.net.URL;
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
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, Integer> idColumn;
    
    private ObservableList<User> userObservableList;
	
    
    public void addUser(ActionEvent e) {
    	Alert.displayMethodNotSet("addUser");
    }
    
    public void deleteUser(ActionEvent e) {
    	User userToDelete = userTableView.getSelectionModel().getSelectedItem();
    	userObservableList.remove(userToDelete);
    	userTableView.setItems(userObservableList);
    	
    	UserDb.deleteUser(userToDelete.getUserId());
    	// after delete, do retrieve all users again
    }
	

public void goToManagerUtilities(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
	NextStage.goTo(fxmlLoader, backBtn);
}

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { // initializes populates ist with current users
		nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
		userObservableList = FXCollections.observableArrayList();
		//userObservableList = FXCollections.observableArrayList(userDB.getAllUsers())
		//userObservableList.add(new User(12, "Mikey"));
		userTableView.setItems(userObservableList);
		
	}
	
}
