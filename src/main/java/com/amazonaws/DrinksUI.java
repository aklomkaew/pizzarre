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
	private Button remove;
	
	private String id = null; //string used to get drink from database
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
    private ListView<String> drinkListView = new ListView<String>();
	
	private ObservableList<String> drinkObservableList = FXCollections.observableArrayList();
	
	private static ArrayList<String> drinkIdArrayList = new ArrayList<String>();
    
	public static ArrayList<String> getDrinkList() {
		return drinkIdArrayList;
	}
	
	public void selectDrink (ActionEvent e) {
		id = ((Button)e.getSource()).getId();
		System.out.println(id + " added");
		drinkIdArrayList.add(id);
		drinkObservableList.add(id);

		drinkListView.setItems(drinkObservableList); // displays toppings in the list
	}
	
	public void confirmDrinks (ActionEvent e) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.setScene(new Scene(root, 600, 600));
			nextStage.setResizable(false);
			
			NewOrderUI display = fxmlLoader.getController();
			display.addDrinks(drinkIdArrayList); 
																	
			nextStage.show();
			Stage currentStage = (Stage) confirm.getScene().getWindow();
			currentStage.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	
	public void cancelDrink (ActionEvent e) {
		drinkObservableList.clear();
		drinkListView.getItems().clear();
		goToOrderScreen(e);
	}
	
	public void removeDrink (ActionEvent e) {
		int index = drinkListView.getSelectionModel().getSelectedIndex();
		drinkIdArrayList.remove(index);
		drinkObservableList.remove(index);
		drinkListView.setItems(drinkObservableList);
	}

	public void goToOrderScreen (ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancel);
	}
	
public void start(Stage arg0) throws Exception {
		}

@Override
public void initialize(URL location, ResourceBundle resources) {
	drinkIdArrayList.clear();
	drinkObservableList.clear();
	drinkIdArrayList.addAll(NewOrderUI.getDrinks());
	drinkObservableList.addAll(drinkIdArrayList);
	drinkListView.setItems(drinkObservableList);
	
}
	
}
