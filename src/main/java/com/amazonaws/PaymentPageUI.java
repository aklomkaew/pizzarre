package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Represents interface to pay for an order
 * @author Christopher
 *
 */

@SuppressWarnings("restriction")
public class PaymentPageUI implements Initializable {

	@FXML
	private Button confirmBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Button mainMenuBtn;
	@FXML
	private TextField paymentTF;
	@FXML
	private TextField totalCostTF;
	@FXML
	private TextField changeTF;

	private double payment;

	@FXML
	private TableView<OrderContents> orderTableView;
	@FXML
	private TableColumn<OrderContents, String> itemColumn;
	@FXML
	private TableColumn<OrderContents, Double> priceColumn;

	private ObservableList<OrderContents> orderContentsObservableList;

	private static Order paymentOrder;

	private ArrayList<Pizza> pizzaArrayList = new ArrayList<Pizza>();

	private ArrayList<Drink> drinkArrayList = new ArrayList<Drink>();

	/**
	 * Represents an object used to display an Order in tableview
	 * @author Christopher
	 *
	 */
	
	public class OrderContents {
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
		
		public OrderContents(String name, double price) {
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

	/**
	 * Checks that the input is in a double format
	 * @throws NumberFormatException if input cannot be parsed as a double
	 */
	
	public void checkPayment() {
		
		try {
			payment = Double.parseDouble(paymentTF.getText());
		} catch (NumberFormatException nfe) {
			Alert.Display("Error", "Payment must be a number.");
			return;
		}
		confirmPayment();
	}

	/**
	 * Checks that the payment price is equal to or greater than Order's total
	 * Sets the Order's state equal to false if it is
	 */
	
	private void confirmPayment() {
		
		if (payment < paymentOrder.getTotal()) {
			Alert.Display("Error", "Payment must be paid in full.");
			return;
		} else {
			if (payment > paymentOrder.getTotal()) {
				Alert.Display("Information", "Payment processed. Change = $" + (payment - paymentOrder.getTotal()));
			} else {
				Alert.Display("Information", "Payment processed.");
			}
			paymentOrder.setState(false);
			OrderDb.updateOrder(paymentOrder);
		}
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, confirmBtn);
	}

	/**
	 * Sets the order being paid for as paymentOrder, how the order is referred as in other method in this class
	 */
	
	public static void setOrder(Order o) {
		paymentOrder = o;
	}

	/**
	 * Creates and displays a table of all items on an Order and their prices
	 */
	
	public void displayOrderContents() {

		itemColumn.setCellValueFactory(new PropertyValueFactory<OrderContents, String>("itemName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<OrderContents, Double>("itemPrice"));
		orderContentsObservableList = FXCollections.observableArrayList();

		for (int i = 0; i < pizzaArrayList.size(); i++) {

			String itemName = pizzaArrayList.get(i).getName();
			double itemPrice = pizzaArrayList.get(i).getPrice();
			orderContentsObservableList.add(new OrderContents(itemName, itemPrice));
		}

		for (int i = 0; i < drinkArrayList.size(); i++) {

			String itemName = drinkArrayList.get(i).getName();
			double itemPrice = drinkArrayList.get(i).getPrice();
			orderContentsObservableList.add(new OrderContents(itemName, itemPrice));
		}

		totalCostTF.setText(Double.toString(paymentOrder.getTotal()));
		orderTableView.setItems(orderContentsObservableList);
	}

	/**
	 * Display RecipeListUI stage and closes the current (CreateRecipeUI) stage
	 */
	
	public void goToMainMenu() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}
	
	/**
	 * Display's the current Order's total and its contents
	 * @param location Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		pizzaArrayList.clear();
		drinkArrayList.clear();
		orderTableView.getItems().clear();
		pizzaArrayList = paymentOrder.getPizzas();
		drinkArrayList = paymentOrder.getDrink();

		displayOrderContents();
	}

}