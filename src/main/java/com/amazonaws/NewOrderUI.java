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
public class NewOrderUI implements Initializable{
	
	
    @FXML
    private Button mainMenu;
    @FXML
    private Button drink;
    @FXML
    private Button special;
    @FXML
    private Button custom;
    @FXML
    private Button discount;
    @FXML
    private Button modifyCustom;
    @FXML
    private ListView<String> orderListView = new ListView<String>();
    
    private ObservableList<String> pizzas = FXCollections.observableArrayList();
    private ObservableList<String> recipes = FXCollections.observableArrayList();
    private ObservableList<String> drinks = FXCollections.observableArrayList();
    private ObservableList<String> orderItems = FXCollections.observableArrayList();
	private ObservableList<String> orderObservableList = FXCollections.observableArrayList();
	
public void goToMainMenu (ActionEvent e) {
    	
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
    NextStage.goTo(fxmlLoader, mainMenu);
}

public void goToDrinks(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrinksUI.fxml"));
	NextStage.goTo(fxmlLoader, drink);
}

public void goToSpecialty(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BuildSpecialtyUI.fxml"));
	NextStage.goTo(fxmlLoader, special);
}

public void goToCustom(ActionEvent e) {
	
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomPizzaUI.fxml"));
	NextStage.goTo(fxmlLoader, custom);
}

public void modifyPizza(ActionEvent e) {
	//Pizza pizza;
	//CustomPizzaUI.initialize(null, null).modfiedPizza = pizza;
	//String pizzaString  = orderListView.getSelectionModel().getSelectedItem();
	goToCustom(e);
}

public void setDiscount(ActionEvent e) {
	Alert.displayMethodNotSet("setDiscount");
}

public void confirmOrder (ActionEvent e) {
	Alert.displayMethodNotSet("confirmOrder");
}

public void discardOrder (ActionEvent e) {
	orderItems.clear();
	orderListView.getItems().clear();
}

public void start(Stage arg0) throws Exception {
		
		}

@Override
public void initialize(URL location, ResourceBundle resources) {
	orderItems = FXCollections.observableArrayList();
	orderItems.addAll(BuildSpecialtyUI.getSpecialtyList());
	orderItems.addAll(DrinksUI.getDrinkList());
	orderListView.setItems(orderItems);
	//ObservableList pizzas = FXCollections.obserableArrayList(RecipeDB.getRecipeNames())
	//orderListView.setItems(recipes);
	
}
	
}
