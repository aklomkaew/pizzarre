package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private Button backBtn;
	@FXML
	private Button cancelBtn;
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
	private static ListView<String> orderListView;
	@FXML
	private TableView<String> itemTableView;
	@FXML
	private TableColumn<String, String> nameColumn;
	@FXML
	private TableColumn<String, Integer> priceColumn;

	private static Order order;
	
	private static ObservableList<String> orderObservableList;
	
	private static int modifiedIndex; //used to keep track of index of piza being modified

//	private ArrayList<Pizza> pizzaArrayList = new ArrayList<Pizza>(); // for pizza objects
//	private static ArrayList<String> pizzaNameArrayList = new ArrayList<String>(); // to display pizzas on listview
	private static ArrayList<String> drinksArrayList = new ArrayList<String>(); // for drink INGREDIENTS
//	private static ObservableList<String> orderObservableList = FXCollections.observableArrayList();

	private static HashMap<String, Integer> allIngredients;

	public static int getmodifiedIndex() {
		return modifiedIndex;
	}
	
	public void modifyPizza(ActionEvent e) {
		int modifiedIndex = orderListView.getSelectionModel().getSelectedIndex();
//		if (modifiedIndex > pizzaNameArrayList.size() - 1) { // -1 since .size() is 1 greater than index
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Error");
//			alert.setHeaderText("Drinks cannot be modified.");
//			alert.showAndWait();
//		} else {
//			try {
//				ArrayList<String> currentToppings = pizzaArrayList.get(modifiedIndex).getToppings();
//				
//
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifiedPizzaUI.fxml"));
//				Parent root = (Parent) fxmlLoader.load();
//				Stage nextStage = new Stage();
//				nextStage.setScene(new Scene(root, 600, 600));
//				nextStage.setResizable(false);
//				
//				 ModifiedPizzaUI display = fxmlLoader.getController();
//				 display.getPizzaInfo(currentToppings);
//				
//				nextStage.show();
//				Stage currentStage = (Stage) modifyCustom.getScene().getWindow();
//				currentStage.close();
//
//			} catch (Exception exception) {
//				exception.printStackTrace();
//			}
//		}
	}

	public void modifiedPizza() {
		combineLists();
	}

	public void removeItem(ActionEvent e) {
		MultipleSelectionModel<String> obj = orderListView.getSelectionModel();
		if (obj == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an item to remove.");
			alert.showAndWait();
			return;
		}

		int index = orderListView.getSelectionModel().getSelectedIndex();
		orderObservableList.remove(index);
//		if (index <= pizzaNameArrayList.size() - 1) { // -1 since .size() is 1 greater than index
//			pizzaNameArrayList.remove(index);
//			pizzaArrayList.remove(index);
//		} else {
//			drinksArrayList.remove(index - pizzaNameArrayList.size());
//		}
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
		double priceTotal = 0.00;
		for (int i = 0; i < order.getPizzas().size(); i++ ) {
			Pizza currentPizza = order.getPizzas().get(i);
			priceTotal = priceTotal + currentPizza.getPrice();
		}
		order.setTotal(priceTotal);
		
		int num = order.getOrderNumber();
		while (!OrderDb.addOrder(order)) {
			num++;
			order.setOrderNumber(num);
		}

		OrderDb.updateOrder(order);
		User u = LoginUI.getUser();
		try {
			u.getOrderList().add(order);
		}
		catch(Exception err) {
			System.out.println("Error cannot add order to user");
			System.err.println(err.getMessage());
		}
		UserDb.updateUser(u);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Your order has been placed! Total is $" + order.getTotal());
		alert.showAndWait();

//		pizzaArrayList.clear(); // clear all list contents after order placed, for next order
//		pizzaNameArrayList.clear();
//		drinksArrayList.clear();
		orderObservableList.clear();
		orderListView.getItems().clear();
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
			removeAllIngredients();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, cancelBtn);
		}
	}
	
	public void goBack(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to return without saving order?");
		alert.showAndWait();

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			removeAllIngredients();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, backBtn);
		}
	}
	
	public void removeAllIngredients() {
		if(allIngredients == null || allIngredients.isEmpty()) {
			return;
		}
		Iterator itr = allIngredients.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry pair = (Map.Entry) itr.next();
			InventoryDb.changeQuantity((String)pair.getKey(), (Integer)pair.getValue(), "increase");
			itr.remove();
		}
//		pizzaArrayList.clear();
//		pizzaNameArrayList.clear();
		orderObservableList.clear();
	}

	public void start(Stage arg0) throws Exception {
		System.out.println("In start");
	}
	
	public static void setOrder(Order c) {
		order = c;
	}

	public void displayItem() {
		orderObservableList.clear();
		orderObservableList.addAll(DrinksUI.getDrinkList());
		if(orderListView == null) {
			orderListView = new ListView();
		}
		orderListView.setItems(orderObservableList);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderObservableList = FXCollections.observableArrayList();
//		if(orderListView == null) {
//			orderListView = new ListView();
//		}
//		orderListView.setItems(orderObservableList);
		//displayItem();
		
//		orderObservableList.addAll(BuildSpecialtyUI.getSpecialtyList());
//		orderObservableList.addAll(DrinksUI.getDrinkList());
//		System.out.println("orderObservableList has: ");
//		for(int i = 0; i < orderObservableList.size(); i++) {
//			System.out.println(orderObservableList.get(0));
//		}
		User u = LoginUI.getUser();
		if (order == null) {
			order = new Order();

			ArrayList<Order> allOrder = OrderDb.retrieveAllItem();
			int num = allOrder.size(); // order number
			order.setOrderNumber(num);
			order.setServerId(u.getUserId());
			order.setServerName(u.getName());
		}
////		pizzaArrayList.clear();
////		pizzaNameArrayList.clear();
////		orderObservableList.clear();
//		
//		pizzaArrayList = order.getPizzas();
//		for (int i = 0; i < pizzaArrayList.size(); i++) {
//			pizzaNameArrayList.add(pizzaArrayList.get(i).getName());
//			System.out.println(pizzaArrayList.get(i).getName());
//			
//		}
//		//orderObservableList.addAll(pizzaNameArrayList);
//		//orderListView.setItems(orderObservableList);
//		combineLists();
//		System.out.println("orderObservableList has: ");
//		for(int i = 0; i < orderObservableList.size(); i++) {
//			System.out.println(orderObservableList.get(0));
//		}
		combineLists();
	}

	public static void addDrinks(ArrayList<String> itemList) {
//		drinksArrayList.clear();
//		drinksArrayList.addAll(itemList);
		combineLists();
	}
	
	public static ArrayList<String> getDrinks() {
		return drinksArrayList;
	}

	public static void combineLists() {
//		orderObservableList.addAll(pizzaNameArrayList);
		orderObservableList.addAll(drinksArrayList);
		if(orderListView == null) {
			orderListView = new ListView<String>();
		}
		orderListView.setItems(orderObservableList);
	}

	public static Order getOrder() {
		return order;
	}

	public static void addIngredient(String str, int quantity) {
		if (allIngredients == null) {
			allIngredients = new HashMap<String, Integer>();
		}
		if (!allIngredients.containsKey(str)) {
			allIngredients.put(str, quantity);
		} else {
			allIngredients.put(str, allIngredients.get(str) + quantity);
		}
	}

	public static boolean removeIngredient(String str, int quantity) {
		boolean status = false;

		if (allIngredients == null || !allIngredients.containsKey(str)) {
			status = false;
		} else {
			if (allIngredients.get(str) < quantity) {
				status = false;
				System.out.println("Ingredient less than amount wanting to remove");
			} else if (allIngredients.get(str) == quantity) {
				allIngredients.remove(str);
				status = true;
			} else {
				allIngredients.put(str, allIngredients.get(str) - quantity);
				status = true;
			}
		}

		return status;
	}
}
