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
public class SpecialtyPizzaUI{
	
	@FXML
	private Button confirm;
	@FXML
	private Button cancel;
	@FXML
	private Button clear;
	@FXML
	private String specialtyName; //names used for display purposes
	@FXML
	private String specialtySize;
	@FXML
	private Button small;
	@FXML
	private Button medium;
	@FXML
	private Button large;
	@FXML
    private Button dailySpecialPizza;
    @FXML
    private Button meatzzaPizza;
    @FXML
    private Button hawaiianPizza;
    @FXML
    private Button classicPizza;
    @FXML
    private Button veggiePizza;
    @FXML
    private Button sicilianPizza;
    @FXML
    private TextField specialtyTF;
    

	
	public void selectSpecialty (ActionEvent e) {
		specialtyName = ((Button)e.getSource()).getId(); // sets specialty name equal to text on a button
		showSpecialty(e);
	}
	
	public void selectSize (ActionEvent e) {
		specialtySize = ((Button)e.getSource()).getId();
		showSpecialty(e);
	}
	
	public void showSpecialty (ActionEvent e) {
		String str = "";
		if(specialtyName != null) {
			str += specialtyName;
		}
		if(specialtySize != null) {
			str += " " + specialtySize;
		}
		specialtyTF.setText(str);
	}
	
	public void clearSpecialty(ActionEvent e) {
		specialtyTF.clear();
		specialtyName = null;
		specialtySize = null;
	}
	
	public void confirmSpecialty (ActionEvent e) {
		
		if (specialtyName != null && specialtySize != null) {
		goToSpecialtyIntoCustom(e);
		} else {
			Alert.Display("Error", "Please select a special and size.");
		}
	}

	public void goToSpecialtyIntoCustom (ActionEvent e) { // do not NextStage.goTo this one
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyIntoCustomUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.setScene(new Scene(root, 600, 600));
			nextStage.setResizable(false);
			SpecialtyIntoCustomUI display = fxmlLoader.getController();
			display.getSpecialtyInfo(specialtyName, specialtySize); //passes specialty recipe onto BuildSpeciatyIntoCustom.java
	        nextStage.show();
	        Stage currentStage = (Stage) confirm.getScene().getWindow();
	        currentStage.close();
	        
	    } catch(Exception exception) {
	    	exception.printStackTrace();
	      }
	}

	public void goToOrderScreen (ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancel);
	}

	public void start(Stage arg0) throws Exception {
		
	}
	
}
