package com.amazonaws;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class AllUsersUI extends Application {
	
	@FXML
    private Button backBtn;
    @FXML
    private Button addUserBtn;
    @FXML
    private Button deleteUserBtn;
    @FXML
    private TableView userTableView = new TableView();
    @FXML
    private TableColumn nameColumn = new TableColumn("Name");
    @FXML
    private TableColumn idColumn = new TableColumn("ID Number");
    @FXML
    private TableColumn orderColumn = new TableColumn("Open Orders");
	
	private ObservableList<String> userObservableList = FXCollections.observableArrayList();
    
    public void addUser(ActionEvent e) {
    	
    }
    
    public void deleteUser(ActionEvent e) {
    	
    }
	

public void goToManagerUtilities(ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage managerUtiliesStage = new Stage();
	            managerUtiliesStage.setScene(new Scene(root));
	            managerUtiliesStage.setTitle("Main Menu");
	            managerUtiliesStage.show();
	            Stage recipeListStage = (Stage) backBtn.getScene().getWindow();
	            recipeListStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
}

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
}
