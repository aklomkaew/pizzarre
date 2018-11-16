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
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class AllOrdersUI extends Application implements Initializable {
	
	

    @FXML
    private Button backBtn;
    @FXML
    private Button showOrderBtn;
    @FXML
    private Button editOrderBtn;
    @FXML
    private Button deleteOrderBtn;
    @FXML
    private Button deleteAllOrdersBtn;
	
    public void showOrders(ActionEvent e) {
    	
    }

    public void editOrders(ActionEvent e) {
    	
    }

    public void deleteOrders(ActionEvent e) {
	
    }

    public void deleteAllOrders(ActionEvent e) {
	
    }


	
    public void goToManagerUtilities(ActionEvent e) {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
    	NextStage.goTo(fxmlLoader, backBtn);
    }

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
