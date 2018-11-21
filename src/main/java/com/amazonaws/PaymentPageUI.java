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
import javafx.stage.Stage;

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

	public class OrderContents {
		String itemName;
		double itemPrice;
		
		public OrderContents(){
    		String itemName = "";
    		double itemPrice = 0.0;
    	}

		public OrderContents(String name, double price) {
			this.itemName = name;
			this.itemPrice = price;
		}

		public String getItemName() {
			return itemName;
		}

		public double getItemPrice() {
			return itemPrice;
		}
	}

	public void checkPayment(ActionEvent e) {
		try {
			payment = Double.parseDouble(paymentTF.getText());
		} catch (NumberFormatException nfe) {
			Alert.Display("Error", "Payment must be a number.");
			return;
		}
		confirmPayment();
	}

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

	public static void setOrder(Order o) {
		paymentOrder = o;
	}

	public void goToMainMenu(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	public void goBack(ActionEvent e) {

	}

	public void start(Stage arg0) throws Exception {

	}

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