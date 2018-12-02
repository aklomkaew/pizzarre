package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Represents an interface an Order object's contents and ways to modify the contents
 * @author Christopher
 *
 */
@SuppressWarnings("restriction")
public class CurrentOrderUI implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button drinkBtn;
	@FXML
	private Button specialBtn;
	@FXML
	private Button customBtn;
	@FXML
	private Button discountBtn;
	@FXML
	private Button confirmBtn;
	@FXML
	private Button removeBtn;
	@FXML
	private Button modifyPizzaBtn;
	@FXML
	private Button viewToppingsBtn;
	@FXML
	private Label costLbl;

	private static Order order;

	private static boolean modOrder;
	
	private static int oldDiscount;

	private static int modifiedIndex; 

	private ArrayList<Drink> drinkArrayList = new ArrayList<Drink>();
	private ArrayList<Pizza> pizzaArrayList = new ArrayList<Pizza>(); // for pizza objects
	private static ArrayList<String> pizzaNameArrayList = new ArrayList<String>(); // to display pizzas on listview
	private static ArrayList<String> drinkNameArrayList = new ArrayList<String>(); // for drink INGREDIENTS

	@FXML
	private TableView<OrderContents> orderTableView;
	@FXML
	private TableColumn<OrderContents, String> itemColumn;
	@FXML
	private TableColumn<OrderContents, Double> priceColumn;
	private ObservableList<OrderContents> orderContentsObservableList;
	
	/**
	 * Represents an object used to place an Order in tableview
	 * @author Christopher
	 *
	 */
	public class OrderContents { //used for tableview
		String itemName;
    	double itemPrice;
    	
    	/**
    	 * Class constructor
    	 */
    	public OrderContents(){
    		String itemName = "";
    		double itemPrice = 0.0;
    	}

    	/**
    	 * Creates an OrderContents with the specified name and price
    	 * @param name A string representing the item's name
    	 * @param price A double represnting he item's cost
    	 */
    	public OrderContents(String name, double price){
    		this.itemName = name;
    		this.itemPrice = price;
    	}
    	
    	/**
    	 * Returns the item's name
    	 * @return A string representing the item's name
    	 */
    	public String getItemName() {
    		return itemName;
    	}
    	
    	/**
    	 * Returns the item's cost
    	 * @return A double representing the item's cost
    	 */
    	public double getItemPrice() {
    		return itemPrice;
    	}
    }
	
	private static HashMap<String, Integer> allIngredients;

	/**
	 * Displays the total cost of everything added together within the Order
	 */
	public void setCostLabel() {
		String price = String.format("%.2f", order.getTotal());
		costLbl.setText("Total Price: $" + price);
	}
	
	/**
	 * Displays the selected pizza's toppings
	 */
	public void viewToppings() {
		int index = orderTableView.getSelectionModel().getSelectedIndex();
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

	/**
	 * Returns the drink's name of the order
	 * @return An ArrayList<String> of names representing drinks on an order
	 */
	public static ArrayList<String> getDrinks() {
		return drinkNameArrayList;
	}
	
	/**
	 * Returns the tableview's selected item's index in the list
	 * @return An int representing the currently selected index on the CurrentOrderUI tableview
	 */
	public static int getModifiedIndex() {
		return modifiedIndex;
	}

	/**
	 * Takes the pizza in the selected tableview index and calls {@link #goToCustom()} with that pizza's contents
	 * @return An int representing the currently selected index on the CurrentOrderUI tableview
	 */
	public void modifyPizza() {
		int index = orderTableView.getSelectionModel().getSelectedIndex();
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

		CustomPizzaUI.setOldPizza(order.getPizzas());
		CustomPizzaUI.setPizza(p);
		
		for(String str : p.getToppings()) {
			InventoryDb.changeQuantity(str, p.getSize(), "increase");
		}

		pizzaArrayList.remove(modifiedIndex);
		pizzaNameArrayList.remove(modifiedIndex);

		goToCustom();
	}

	/**
	 * Applies a percentile reduction to the Order variable: total
	 * @throws Exception if no Integer input
	 */
	public void setDiscount() {
		TextInputDialog dialog = new TextInputDialog(Integer.toString(order.getDiscount()));
		dialog.setTitle("Discount");
		dialog.setHeaderText("Set Discount for Order #" + order.getOrderNumber());
		dialog.setContentText("Enter percent discount: ");

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		
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
		
		oldDiscount = order.getDiscount();
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

	/**
	 * Adds the Order object to the Order database
	 */
	public void confirmOrder() {
		order.setTotal(0);
		for (int i = 0; i < order.getPizzas().size(); i++) {
			Pizza currentPizza = order.getPizzas().get(i);
			order.setTotal(order.getTotal() + currentPizza.getPrice());
		}
		for (int i = 0; i < order.getDrink().size(); i++) {
			Drink currentDrink = order.getDrink().get(i);
			order.setTotal(order.getTotal() + currentDrink.getPrice());
		}
		
		if(order.getTotal() == 0.0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Empty order.");
			alert.showAndWait();
			return;
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
			d.setIsNew(0);
		}
		for (Pizza p : order.getPizzas()) {
			p.setIsNew(0);
		}

		OrderDb.updateOrder(order);

		alert.setHeaderText("Your order " + order.getOrderNumber() + " has been placed! Total is $" + order.getTotal());
		alert.showAndWait();

		pizzaArrayList.clear();
		pizzaNameArrayList.clear();
		drinkArrayList.clear();
		drinkNameArrayList.clear();
		orderContentsObservableList.clear();
		orderTableView.getItems().clear();
		goToMainMenu();
	}

	/**
	 * Deletes the Order object and undoes any inventory database changes caused by the current Order then returns to the MainMenuUI stage
	 */
	public void discardOrder() {
		if(order.getTotal() == 0.0) {
			goToMainMenu();
			return;
		}
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
				if(order != null) {
					order.setDiscount(oldDiscount);
					updateCost();
					removeAllIngredients();
				}
			} else {
				for (int i = 0; i < order.getPizzas().size(); i++) {
					Pizza p = order.getPizzas().get(i);
					if (p.getIsNew() == 1) {
						for(String topping : p.getToppings()) {
							InventoryDb.changeQuantity(topping, p.getSize(), "increase");
						}
						order.getPizzas().remove(i);
					}
				}
				for (int i = 0; i < order.getDrink().size(); i++) {
					Drink d = order.getDrink().get(i);
					if (d.getIsNew() == 1) {
						InventoryDb.changeQuantity(d.getName(), 1, "increase");
						order.getDrink().remove(i);
					}
				}
				order.setDiscount(oldDiscount);
				updateCost();
				OrderDb.updateOrder(order);

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
			if (orderContentsObservableList != null) {
				orderContentsObservableList.clear();
			}
			orderTableView.getItems().clear();

			goToMainMenu();
		}
	}

	/**
	 * Returns to the MainMenuUI stage and closes the current (CurrentOrderUI) stage
	 */
	public void goBack() {
		if(order.getTotal() == 0.0) {
			goToMainMenu();
			return;
		}
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
			if(order != null) {
				removeAllIngredients();
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, backBtn);
		}
	}

	/**
	 * Increases the inventory item quantity in the database when item is removed from the Order
	 */
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
		
		pizzaArrayList.clear();
		pizzaNameArrayList.clear();
		drinkArrayList.clear();
		drinkNameArrayList.clear();
		orderContentsObservableList.clear();
		order = null;
	}

	/**
	 * Removes an item from the Order
	 */
	public void removeItem() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		int index = orderTableView.getSelectionModel().getSelectedIndex();

		if (index == -1 || index <= pizzaNameArrayList.size() - 1) { // -1 since .size() is 1 greater than index
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
		orderContentsObservableList.remove(index);
		updateCost();
	}
	
	/**
	 * Updates the total cost of the Order
	 */
	public void updateCost() {
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

	/**
	 * Notification of successful stage transversal
	 * @param arg0 A Stage representation, unused
	 * @throws Exception, will not throw an exception
	 */
	public void start(Stage arg0) throws Exception {
		System.out.println("In start");
	}

	/**
	 * Signals the current Order is not a new Order
	 * @param Order An Order representing an Order that already exists in the database
	 */
	public static void setOrder(Order c) {
		order = c;
		modOrder = true;
	}

	/**
	 * Returns the current Order
	 * @return an Order representing what is displayed on CurrentOrderUI
	 */
	public static Order getOrder() {
		return order;
	}

	/**
	 * Returns the current Order
	 * @param str A string representing the ingredient's name
	 * @param quantity An int representing the ingredient's amount
	 * @return a boolean
	 */
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
	
	/**
	 * Sets DrinksUI drink list, display DrinksUI stage and closes the current (CurrentOrderUI) stage
	 */
	public void goToDrinks() {
		DrinksUI.setDrinks(order.getDrink());
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrinksUI.fxml"));
		NextStage.goTo(fxmlLoader, drinkBtn);
	}

	/**
	 * Display SpecialtyPizzaUI stage and closes the current (CurrentOrderUI) stage
	 */
	public void goToSpecialty() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, specialBtn);
	}

	/**
	 * Display CustomPizzaUI stage and closes the current (CurrentOrderUI) stage
	 */
	public void goToCustom() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, customBtn);
	}
	
	/**
	 * Display MainMenuUI stage and closes the current (CurrentOrderUI) stage
	 */
	public void goToMainMenu() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, confirmBtn);
	}
	
	/**
	 * Creates a two-column table displaying an Order's item and that item's total
	 * Loads the table with Order data if not a new Order
	 * @param location Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderContentsObservableList = FXCollections.observableArrayList();
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
		orderContentsObservableList.clear();

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

		itemColumn.setCellValueFactory(new PropertyValueFactory<OrderContents, String>("itemName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<OrderContents, Double>("itemPrice"));
		orderContentsObservableList = FXCollections.observableArrayList();
		for(int i = 0; i < pizzaArrayList.size(); i++) {
			
			String itemName = pizzaArrayList.get(i).getName();
			double itemPrice = pizzaArrayList.get(i).getPrice();
			orderContentsObservableList.add(new OrderContents(itemName, itemPrice));
		}
		
		for(int i = 0; i < drinkArrayList.size(); i++) {
				
			String itemName = drinkArrayList.get(i).getName();
			double itemPrice = drinkArrayList.get(i).getPrice();
			orderContentsObservableList.add(new OrderContents(itemName, itemPrice));
		}
		orderTableView.setItems(orderContentsObservableList);
		updateCost();
	}
}
