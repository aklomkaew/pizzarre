package com.amazonaws;

import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
	private Button modifyPizza;
	@FXML
	private Button viewToppingsBtn;
	@FXML
	private ListView<String> orderListView;
	@FXML
	private TableView<String> itemTableView;
	@FXML
	private TableColumn<String, String> nameColumn;
	@FXML
	private TableColumn<String, Integer> priceColumn;
	@FXML
	private Label costLbl;

	private static Order order;

	private static boolean modOrder;

	private ObservableList<String> orderObservableList = FXCollections.observableArrayList();

	private static int modifiedIndex; // used to keep track of index of piza being modified

	private ArrayList<Drink> drinkArrayList = new ArrayList<Drink>();
	private ArrayList<Pizza> pizzaArrayList = new ArrayList<Pizza>(); // for pizza objects
	private static ArrayList<String> pizzaNameArrayList = new ArrayList<String>(); // to display pizzas on listview
	private static ArrayList<String> drinkNameArrayList = new ArrayList<String>(); // for drink INGREDIENTS
	// private static ObservableList<String> orderObservableList =
	// FXCollections.observableArrayList();

	private static HashMap<String, Integer> allIngredients;

	public void setCostLabel() {
		String price = String.format("%.2f", order.getTotal());
		costLbl.setText("Total Price: $" + price);
	}
	
	public void viewToppings(ActionEvent e) {
		int index = orderListView.getSelectionModel().getSelectedIndex();
		//MultipleSelectionModel<String> obj = orderListView.getSelectionModel();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		Alert alertInfo = new Alert(AlertType.INFORMATION);
		alertInfo.setTitle("Information");

		if (index == -1 || index > pizzaNameArrayList.size() - 1) { // -1 since .size() is 1 greater than index
			alert.setHeaderText("Select a pizza to view topping.");
			alert.showAndWait();
			return;
		} else {
			alertInfo.setHeaderText(pizzaNameArrayList.get(index) + " has toppings:");
			alertInfo.setContentText(pizzaArrayList.get(index).toString());
			alertInfo.showAndWait();
		}
	}

	public static ArrayList<String> getDrinks() {
		return drinkNameArrayList;
	}
	
	public static int getModifiedIndex() {
		return modifiedIndex;
	}

	public void modifyPizza(ActionEvent e) {
		int index = orderListView.getSelectionModel().getSelectedIndex();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		
		if (index == -1 || index > pizzaNameArrayList.size() - 1) { // -1 since .size() is 1 greater than index
			alert.setHeaderText("Select a pizza to view topping.");
			alert.showAndWait();
			return;
		}
		
		Pizza p = pizzaArrayList.get(index);

		if (p.getIsNew() == 0) {
			alert.setHeaderText("Cannot modify.");
			alert.setContentText("This item has already been processed.");
			alert.showAndWait();
			return;
		}

		CustomPizzaUI.setPizza(p);
		
		for(String str : p.getToppings()) {
			InventoryDb.changeQuantity(str, p.getSize(), "increase");
		}

		pizzaArrayList.remove(modifiedIndex);
		pizzaNameArrayList.remove(modifiedIndex);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, modifyPizza);
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
		TextInputDialog dialog = new TextInputDialog("20");
		dialog.setTitle("Discount");
		dialog.setHeaderText("Set Discount for Order #" + order.getOrderNumber());
		dialog.setContentText("Enter percent discount: ");

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		
		// Traditional way to get the response value.
		String result = "";
		try {
			result = dialog.showAndWait().get();
		}
		catch(Exception ex) {
			return;
		}
		
		int discount = 0;
		try {
			discount = Integer.parseInt(result);
		}
		catch(Exception ex) {
			alert.setHeaderText("Value must be an integer.");
			alert.showAndWait();
			return;
		}
		
		order.setDiscount(discount);
		order.applyDiscount();
		updateCost();
		OrderDb.updateOrder(order);
		
		Alert alertInfo = new Alert(AlertType.INFORMATION);
		alertInfo.setTitle("Success");
		String price = String.format("%.2f", order.getTotal());
		alertInfo.setHeaderText(order.getDiscount() + "% discount applied to $" + price + " total.");
		alertInfo.showAndWait();
	}

	public void confirmOrder(ActionEvent e) {
		order.setTotal(0);
		double price = 0.0;
		for (int i = 0; i < order.getPizzas().size(); i++) {
			Pizza currentPizza = order.getPizzas().get(i);
			order.setTotal(order.getTotal() + currentPizza.getPrice());
		}
		for (int i = 0; i < order.getDrink().size(); i++) {
			Drink currentDrink = order.getDrink().get(i);
			order.setTotal(order.getTotal() + currentDrink.getPrice());
		}
		if(order.getDiscount() > 0) {
			order.applyDiscount();
		}
		int num = order.getOrderNumber();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");

		if (!modOrder) {
			while (!OrderDb.addOrder(order)) {
				num++;
				order.setOrderNumber(num);
			}
		}
		for (Drink d : order.getDrink()) {
			d.setIsNew();
		}
		for (Pizza p : order.getPizzas()) {
			p.setIsNew();
		}

		OrderDb.updateOrder(order);

		alert.setHeaderText("Your order " + order.getOrderNumber() + " has been placed! Total is $" + order.getTotal());

		alert.showAndWait();

		pizzaArrayList.clear();
		pizzaNameArrayList.clear();
		drinkArrayList.clear();
		drinkNameArrayList.clear();
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
			if (!modOrder) {
				removeAllIngredients();
			} else {
				for (int i = 0; i < order.getPizzas().size(); i++) {
					if (order.getPizzas().get(i).getIsNew() == 1) {
						order.getPizzas().remove(i);
					}
				}
				for (int i = 0; i < order.getDrink().size(); i++) {
					if (order.getDrink().get(i).getIsNew() == 1) {
						order.getDrink().remove(i);
					}
				}
				OrderDb.updateOrder(order);
//				User u = LoginUI.getUser();
//				try {
//					u.getOrderList().add(order);
//				} catch (Exception err) {
//					System.out.println("Error cannot add order to user");
//					System.err.println(err.getMessage());
//				}
//				UserDb.updateUser(u);

				order = null;
				modOrder = false;
			}

			if (pizzaArrayList != null) {
				pizzaArrayList.clear();
			}
			if (pizzaNameArrayList != null) {
				pizzaNameArrayList.clear();
			}
			if (drinkArrayList != null) {
				drinkArrayList.clear();
			}
			if (drinkNameArrayList != null) {
				drinkNameArrayList.clear();
			}
			if (orderObservableList != null) {
				orderObservableList.clear();
			}
			orderListView.getItems().clear();

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
		for (Pizza p : order.getPizzas()) {
			if (p.getIsNew() == 1) {
				for (String str : p.getToppings()) {
					InventoryDb.changeQuantity(str, 1, "increase");
				}
			}
		}
		for (Drink d : order.getDrink()) {
			if (d.getIsNew() == 1) {
				InventoryDb.changeQuantity(d.getName(), 1, "increase");
			}
		}
//		if (allIngredients == null || allIngredients.isEmpty()) {
//			return;
//		}
//		Iterator itr = allIngredients.entrySet().iterator();
//		while (itr.hasNext()) {
//			Map.Entry pair = (Map.Entry) itr.next();
//			InventoryDb.changeQuantity((String) pair.getKey(), (Integer) pair.getValue(), "increase");
//			itr.remove();
//		}
		pizzaArrayList.clear();
		pizzaNameArrayList.clear();
		drinkArrayList.clear();
		drinkNameArrayList.clear();
		orderObservableList.clear();
		order = null;
	}

	public void removeItem(ActionEvent e) {
		MultipleSelectionModel<String> obj = orderListView.getSelectionModel();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");

		if (obj == null) {
			alert.setHeaderText("Select an item to remove.");
			alert.showAndWait();
			return;
		}

		int index = orderListView.getSelectionModel().getSelectedIndex();

		if (index <= pizzaNameArrayList.size() - 1) { // -1 since .size() is 1 greater than index
			if (pizzaArrayList.get(index).getIsNew() == 0) { // not new
				alert.setHeaderText("This item cannot be removed.");
				alert.setContentText("This item has already been processed");
				alert.showAndWait();
				return;
			}
			pizzaNameArrayList.remove(index);
			for (String str : pizzaArrayList.get(index).getToppings()) {
				InventoryDb.changeQuantity(str, 1, "increase");
			}
			pizzaArrayList.remove(index);
		} else {
			if (drinkArrayList.get(index - pizzaNameArrayList.size()).getIsNew() == 0) { // not new
				alert.setHeaderText("This item cannot be removed.");
				alert.setContentText("This item has already been processed");
				alert.showAndWait();
				return;
			}
			InventoryDb.changeQuantity(drinkNameArrayList.get(index - pizzaNameArrayList.size()), 1, "increase");
			drinkNameArrayList.remove(index - pizzaNameArrayList.size());
			drinkArrayList.remove(index - pizzaNameArrayList.size());
		}
		orderObservableList.remove(index);
		updateCost();
	}
	
	private void updateCost() {
		order.setTotal(0);
		for (int i = 0; i < order.getPizzas().size(); i++) {
			Pizza currentPizza = order.getPizzas().get(i);
			order.setTotal(order.getTotal() + currentPizza.getPrice());
		}
		for (int i = 0; i < order.getDrink().size(); i++) {
			Drink currentDrink = order.getDrink().get(i);
			order.setTotal(order.getTotal() + currentDrink.getPrice());
		}
		order.applyDiscount();
		
		setCostLabel();
	}

	public void start(Stage arg0) throws Exception {
		System.out.println("In start");
	}

	public static void setOrder(Order c) {
		order = c;
		modOrder = true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderObservableList = FXCollections.observableArrayList();
		User u = LoginUI.getUser();
		if (order == null) {
			order = new Order();

			ArrayList<Order> allOrder = OrderDb.retrieveAllItem();
			int num = allOrder.size(); // order number
			order.setOrderNumber(num);
			order.setServerId(u.getUserId());
			order.setServerName(u.getName());
		}
		pizzaArrayList.clear();
		drinkArrayList.clear();
		pizzaNameArrayList.clear();
		drinkNameArrayList.clear();
		orderObservableList.clear();

		pizzaArrayList = order.getPizzas();
		if (pizzaArrayList != null) {
			for (int i = 0; i < pizzaArrayList.size(); i++) {
				pizzaNameArrayList.add(pizzaArrayList.get(i).getName());
				System.out.println(pizzaArrayList.get(i).getName());
			}
		}
		drinkArrayList = order.getDrink();
		if (drinkArrayList != null) {
			for (Drink d : drinkArrayList) {
				drinkNameArrayList.add(d.getName());
			}
		}
		/*
		 * drinkArrayList = order.getDrink(); for (Drink d : drinkArrayList) {
		 * drinkNameArrayList.add(d.getName()); }
		 */

		combineLists();
		updateCost();
	}

	public void combineLists() {
		orderObservableList.addAll(pizzaNameArrayList);
		orderObservableList.addAll(drinkNameArrayList);
		orderListView.setItems(orderObservableList);
	}

	public static Order getOrder() {
		return order;
	}

//	public static void addIngredient(String str, int quantity) {
//		if (allIngredients == null) {
//			allIngredients = new HashMap<String, Integer>();
//		}
//		if (!allIngredients.containsKey(str)) {
//			allIngredients.put(str, quantity);
//		} else {
//			allIngredients.put(str, allIngredients.get(str) + quantity);
//		}
//	}

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
