package com.amazonaws;

import java.util.ArrayList;

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
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class BuildSpecialtyUI {
	
	@FXML
	private Button confirm;
	@FXML
	private Button cancel;
	@FXML
	private Button clear;
	@FXML
	private String specialtyName = null; //names used for display purposes
	
	private String id = null; //names used for database access
	@FXML
	private String specialtySize = null;
	@FXML
	private Button small;
	@FXML
	private Button medium;
	@FXML
	private Button large;
	@FXML
    private Button dailySpecial;
    @FXML
    private Button meatzza;
    @FXML
    private Button hawaiian;
    @FXML
    private Button classic;
    @FXML
    private Button veggie;
    @FXML
    private Button sicilian;
    @FXML
    private ListView<String> specialtyListView = new ListView<String>();
	
	private ObservableList<String> specialtyObservableList = FXCollections.observableArrayList();
	
	private ArrayList<String> specialIdArrayList = new ArrayList<String>();
    

	
	public void selectSpecialty (ActionEvent e) {
		specialtyName = ((Button)e.getSource()).getText(); // sets specialty name equal to text on a button
		id = ((Button)e.getSource()).getId(); //sets id name equal to button's fx:id
	}
	
	public void selectSize (ActionEvent e) {
		specialtySize = ((Button)e.getSource()).getId();
	}
	
	public void addSpecialty (ActionEvent e) {
		
		if(specialtyName == null || id == null || specialtySize == null) {
			
			Alert.Display("ERROR", "Select a special and size.");
		
			id = null;
			specialtyName = null;
			specialtySize = null;
		} else {
			
			String specialtyListViewString = specialtyName + ": " + specialtySize;
			
			specialIdArrayList.add(id);
			specialtyObservableList.add(specialtyListViewString);
			specialtyListView.setItems(specialtyObservableList);;
			
			id = null;
			specialtyName = null;
			specialtySize = null;
			}
		}
	
	public void clearSpecialty(ActionEvent e) {
		specialtyObservableList.clear();
		specialtyListView.getItems().clear();
	}
	
	public void confirmSpecialty (ActionEvent e) {
		Alert.displayIntegration("BuildSpecialtyUI.confirmSpecialty(ActionEvent e)");
		/*
		drinkIdArrayList <- contains the inventory id's of the selected drinks
		erase Order's drink ArrayList if new drinks confirmed
		something like below?
		Order.drinks.clear(); // only remove this line if method added to populate listview from placed specialties from newOrderUI
		for(Integer drinkInList = 0; drinkInList < drinkIdArrayList.size(); drinkInList ++)
		{
			Order.drinks[drinkInList] = drinkIdArrayList[drinkInList];
		}
		*/
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, confirm);
	}
	
	
	public void cancelSpecialty (ActionEvent e) {
		goToOrderScreen(e);
	}


	public void goToOrderScreen (ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancel);
	}

	public void start(Stage arg0) throws Exception {
		
	}
	
}