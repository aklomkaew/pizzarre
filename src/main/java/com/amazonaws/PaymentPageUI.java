package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
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

public class PaymentPageUI extends Application implements Initializable {

	@FXML
	private Button confirmPaymentBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TextField paymentTF;
	@FXML
	private TextField totalCostTF;
	@FXML
	private TextField changeTF;
    @FXML
    private TableView<OrderContents> orderTableView;
    @FXML
    private TableColumn<OrderContents, String> itemColumn;
    @FXML
    private TableColumn<OrderContents, Double> priceColumn;
    
    private ObservableList<Order> orderObservableList;
    
    private ObservableList<OrderContents> orderContentsObservableList = FXCollections.observableArrayList();
    
    private static Order paymentOrder;
    
    private ArrayList<Pizza> pizzaArrayList = new ArrayList<Pizza>();
    
    //private ArrayList<Drink> drinkArrayList = new ArrayList<Drink>();
    
    private class OrderContents {
    	String itemName;
    	double itemPrice;
    }
	
	public void checkPayment(ActionEvent e) {
		try {
			Double.parseDouble(paymentTF.getText());
			displayChange(e);
		} catch (NumberFormatException nfe ) {
			Alert.Display("Error", "Payment must be a number.");
		}
	}
	
	public void displayChange (ActionEvent e) {
		double total =  Double.parseDouble(totalCostTF.getText());
		double payment = Double.parseDouble(paymentTF.getText());
		double change = (-1)*(total - payment);
		
		String changeString = Double.toString(change);
		changeTF.setText("$" + changeString);
		//Order.setInactive()
		
	}
	
	public void goToMainMenu(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}


	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

	public void displayOrderContents() {
		orderContentsObservableList.clear();
		//if (pizzaArrayList == null || pizzaArrayList.size() < 1) {
			//return;
		//}
		
		for(int i = 0; i < pizzaArrayList.size(); i++) {
			OrderContents orderContents = new OrderContents();
			orderContents.itemName = pizzaArrayList.get(i).getName();
			orderContents.itemPrice = pizzaArrayList.get(i).getPrice();
			orderContentsObservableList.add(orderContents);
			System.out.println(orderContents.itemName + " " + orderContents.itemPrice);
		}
		/*
			for(int i = 0; i <= drinkArrayList.size()-1; i++) {
				OrderContents orderContents = new OrderContents();
				orderContents.itemName = pizzaArrayList.get(i).getName();
				orderContents.itemPrice = pizzaArrayList.get(i).getPrice();
				orderContentsObservableList.add(orderContents);
				System.out.println(orderContents.itemName + " " + orderContents.itemPrice);
		}
		 */
		orderTableView.setItems(orderContentsObservableList);
		orderTableView.getColumns().addAll(itemColumn, priceColumn);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void initializeMyOrder (Order currentOrder) {
		paymentOrder = currentOrder;
		pizzaArrayList = paymentOrder.getPizzas();
		//drinkArrayList = paymentOrder.getDrinks();
		//orderTableView.setEditable(true);
		
		itemColumn.setCellValueFactory(new PropertyValueFactory<OrderContents, String>("itemName"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<OrderContents, Double>("itemPrice"));
		displayOrderContents();
	}
}