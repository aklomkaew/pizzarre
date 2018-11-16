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
	private Button confirm;
	@FXML
	private Button cancel;
	@FXML
	private Button clear;
	@FXML
	private Button add;
	@FXML
	private String drinkName = null; //string used to display drink on order tab
	
	private String id = null; //string used to get drink from database
	@FXML
	private String drinkSize = null;
	@FXML
	private Button small;
	@FXML
	private Button medium;
	@FXML
	private Button large;
	@FXML
    private Button soda;
    @FXML
    private Button icedTea;
    @FXML
    private Button juice;
    @FXML
    private Button hotTea;
    @FXML
    private Button beer;
    @FXML
    private Button wine;
    @FXML
    private ListView<String> drinkListView = new ListView<String>();
	
	private static ObservableList<String> drinkObservableList = FXCollections.observableArrayList();
	
	private ArrayList<String> drinkIdArrayList = new ArrayList<String>();
    
	public static ObservableList<String> getDrinkList() {
		return drinkObservableList;
	}
	
	public void selectDrink (ActionEvent e) {
		drinkName = ((Button)e.getSource()).getText(); // sets drink name equal to text on a button
		id = ((Button)e.getSource()).getId(); //sets id name equal to button's fx:id
	}
	
	public void selectSize (ActionEvent e) {
		drinkSize = ((Button)e.getSource()).getId();
	}
	
	public void addDrink (ActionEvent e) {
		
		if(drinkName == null || id == null || drinkSize == null) {
			
			Alert.Display("ERROR", "Select a drink and size.");
			
			id = null;
			drinkName = null;
			drinkSize = null;
		} else {
			
			String drinkListViewString = drinkName + ": " + drinkSize;
			
			drinkIdArrayList.add(id);
			drinkObservableList.add(drinkListViewString);
			drinkListView.setItems(drinkObservableList);;
			
			id = null;
			drinkName = null;
			drinkSize = null;
		}
	}
	
	public void confirmDrink (ActionEvent e) {
		Alert.displayIntegration("DrinksUI.confirmDrink(ActionEvent e)");
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
	
	
	
	public void cancelDrink (ActionEvent e) {
		drinkObservableList.clear();
		drinkListView.getItems().clear();
		goToOrderScreen(e);
	}
	
	public void clearDrinks (ActionEvent e) {
		drinkIdArrayList.clear();
		drinkObservableList.clear();
		drinkListView.getItems().clear();
	}

	public void goToOrderScreen (ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancel);
	}
	
public void start(Stage arg0) throws Exception {
		}

@Override
public void initialize(URL location, ResourceBundle resources) {
	drinkListView.setItems(drinkObservableList);
	
}
	
}
