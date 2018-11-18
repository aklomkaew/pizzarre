package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class NewOrderUI implements Initializable {

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
	private Button confirm;
	@FXML
	private Button modifyCustom;
	@FXML
	private ListView<String> orderListView;

	private static Order order;

private static ArrayList<Pizza> pizzaArrayList = new ArrayList<Pizza>(); // for pizza objects
    private static ArrayList<String> pizzaNameArrayList = new ArrayList<String>(); // to display pizzas on listview
    private static ArrayList<String> drinksArrayList = new ArrayList<String>(); // for drink INGREDIENTS
	private ObservableList<String> pizzas = FXCollections.observableArrayList();
	private ObservableList<String> recipes = FXCollections.observableArrayList();
	private ObservableList<String> drinks = FXCollections.observableArrayList();
	private ObservableList<String> orderItems = FXCollections.observableArrayList();
	private ObservableList<String> orderObservableList = FXCollections.observableArrayList();

	public void goToMainMenu(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to return without saving order?");

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, mainMenu);
		}
 }

	public void modifyPizza(ActionEvent e) { //selects a pizza to change its toppings
		int index = orderListView.getSelectionModel().getSelectedIndex();
		if (index > pizzaNameArrayList.size() - 1) { //-1 since .size() is 1 greater than index
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Drinks cannot be modified.");
			alert.showAndWait();
		} else {
			try {
				
				ArrayList<String> currentToppings = pizzaArrayList.get(index).getToppings();
				
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifiedPizzaUI.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				Stage nextStage = new Stage();
				nextStage.setScene(new Scene(root, 600, 600));			
				nextStage.setResizable(false);
				ModifiedPizzaUI display = fxmlLoader.getController();				
				display.getPizzaInfo(currentToppings);
		        nextStage.show();
		        Stage currentStage = (Stage) modifyCustom.getScene().getWindow();
		        currentStage.close();
		        
		    } catch(Exception exception) {
		    	exception.printStackTrace();
		      }
		}
	}
	
public void modifiedPizza () { //
		combineLists();
	}

	public void removeItem(ActionEvent e) {
		int index = orderListView.getSelectionModel().getSelectedIndex();
		orderObservableList.remove(index);
		if (index <= pizzaNameArrayList.size() - 1) { //-1 since .size() is 1 greater than index
			pizzaNameArrayList.remove(index);
			pizzaArrayList.remove(index);
		} else {
			drinksArrayList.remove(index - pizzaNameArrayList.size());
		}
	}

	public void goToDrinks(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrinksUI.fxml"));
		NextStage.goTo(fxmlLoader, drink);
	}

	public void goToSpecialty(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, special);
	}
	
	public void goToCustom(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, custom);
	}

	public void setDiscount(ActionEvent e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Method not set for setDiscount()");
		alert.showAndWait();
	}

	public void confirmOrder(ActionEvent e) {
		int num = order.getOrderNumber();
		while (!OrderDb.addOrder(order)) {
			num++;
			order.setOrderNumber(num);
		}

		OrderDb.updateOrder(order);
		User u = LoginUI.getUser();
		u.getOrderList().add(order);
		UserDb.updateUser(u);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Your order has been placed!");
		alert.showAndWait();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, confirm);
	}

	public void discardOrder(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to cancel your order?");
		alert.showAndWait();

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, mainMenu);
		}
	}

	public void start(Stage arg0) throws Exception {
		System.out.println("In start");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		User u = LoginUI.getUser();
		if (order == null) {
			order = new Order();

			ArrayList<Order> allOrder = OrderDb.retrieveAllItem();
			int num = allOrder.size(); // order number
			order.setOrderNumber(num);
			order.setServerId(u.getUserId());
			order.setServerName(u.getName());
		}
	}
	
	public void makeSpecialtyPizzaObject (String specialtyName, ArrayList<String> specialtyToppings, int specialtySize) { // called in BuildSpeciltyIntoCustomUI
		String sizeString = "size";
		switch(specialtySize) {
		case 1:
			sizeString = "Small";
			break;
		case 2:
			sizeString = "Medium";
			break;
		case 3:
			sizeString = "Large";
			break;
		}
		String pizzaName = sizeString + " " + specialtyName; //the name used in the list
		Pizza pizza = new Pizza(pizzaName, specialtySize, specialtyToppings);
		pizzaArrayList.add(pizza);
		pizzaNameArrayList.add(pizza.getName());
		combineLists();
	}
	
	public void makeCustomPizzaObject (ArrayList<String> toppings, int size) { // called by confirmPizza in CustomPizzaUI, adds the new pizza to the list of pizzas on order
		String sizeString = "size";
		switch(size) {
		case 1:
			sizeString = "Small";
			break;
		case 2:
			sizeString = "Medium";
			break;
		case 3:
			sizeString = "Large";
			break;
		}
		String pizzaName = sizeString + " Custom"; //the name used in the list
		Pizza pizza = new Pizza(pizzaName, size, toppings);
		pizzaArrayList.add(pizza);
		pizzaNameArrayList.add(pizza.getName());
		combineLists();
	}
	
	public void addDrink (String drinkName) { // called by addDrink in DrinksUI, adds the drinkName to the list of drinks on order
		//InventoryDb.getIngredient(drinkName);
		drinksArrayList.add(drinkName);
		combineLists();
	}

	public void combineLists() { // combines the drink and pizza list to display them
		orderObservableList.addAll(pizzaNameArrayList);
		orderObservableList.addAll(drinksArrayList);
		orderListView.setItems(orderObservableList);
	}
	
	public static Order getOrder() {
		return order;
	}
}
