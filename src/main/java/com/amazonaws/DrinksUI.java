package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class DrinksUI implements Initializable {
	
	@FXML
	private Button cancel;
	@FXML
	private Button clear;
	@FXML
	private Button add;
	@FXML
	private String id;
	@FXML
	private Button small;
	@FXML
	private Button medium;
	@FXML
	private Button large;
	@FXML
    private Button soda;
    @FXML
    private Button icedtea;
    @FXML
    private Button juice;
    @FXML
    private Button hottea;
    @FXML
    private Button beer;
    @FXML
    private Button wine;
    @FXML
    private TextField drinkTF;
    @FXML
	
	public void selectDrink (ActionEvent e) {
		id = ((Button)e.getSource()).getId();
		drinkTF.setText(id);
	}
	
public void confirmDrink (ActionEvent e) {
		
		if (id == null) {
		Alert.Display("Error",  "Please selet a drink.");
		} else {
			addDrink(e);
		}
	}
    
	public void addDrink(ActionEvent e) { // do not NextStage.goTo
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.setScene(new Scene(root, 600, 600));
			nextStage.setResizable(false);
			NewOrderUI display = fxmlLoader.getController();
			display.addDrink(id);
	        nextStage.show();
	        Stage currentStage = (Stage) add.getScene().getWindow();
	        currentStage.close();
	        
	    } catch(Exception exception) {
	    	exception.printStackTrace();
	      }
	}
	
	public void clearDrink(ActionEvent e) {
		drinkTF.clear();
		id = null;
	}

	public void goToOrderScreen (ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancel);
	}
	
public void start(Stage arg0) throws Exception {
		}

@Override
public void initialize(URL location, ResourceBundle resources) {
	
}
	
}
