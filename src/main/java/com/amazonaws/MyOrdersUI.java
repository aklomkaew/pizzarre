package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Represents interface displaying the user's orders
 * @author Christopher
 *
 */
@SuppressWarnings("restriction")
public class MyOrdersUI implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button payBtn;
	@FXML
	private Button editBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button showBtn;
	@FXML
	private Button refreshBtn;
	@FXML
	private TableView<Order> orderTableView;
	@FXML
	private TableColumn<Order, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Order, Double> totalColumn;

	private ObservableList<Order> orderObservableList;

	private Order selectedOrder;

	/**
	 *Button that when clicked automatically refreshes the list 
	 */
	public void refreshOrder() {
		displayAllOrder();
	}
	
	/**
	 * Display MainMenuUI stage and closes the current (MyOrdersUI) stage
	 */
	public void goToMainMenu() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	 /**
	  *Returns the selected order
	  *@return an order representing the Order selected on the list
	  */
	public Order getOrder() {
		
		return selectedOrder;
	}

	/**
	  *Displays a list of the user's current orders
	  */
	public void displayAllOrder() {
		
		List<Order> list = OrderDb.retrieveFilteredItem(LoginUI.getUser().getUserId());
		if (list == null || list.size() < 1) {
			return;
		}
		
		ArrayList<Order> activeOrder = new ArrayList<Order>();
		for(Order o : list) {
			if(o.getState()) {
				activeOrder.add(o);
			}
		}

		orderObservableList.clear();
		orderObservableList.addAll(activeOrder);
	}

	 /**
	  *Shows the contents on the selected Order
	  */
	public void showOrder() {
		
		Order item = orderTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to view.");
			alert.showAndWait();
			return;
		}
		String status = "";
		if (item.getState()) {
			status += "active";
		} else {
			status += "not active";
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("View Order");
		alert.setHeaderText("Order " + item.getOrderNumber() + " is " + status + ". It contains:");
		alert.setContentText(item.toString());
		alert.showAndWait();
	}

	/**
	 *Gets the selected Order then calls {@link #goToPaymentPage}  and loads the payment screen with the order's data
	 */
	public void payOrder() {
		
		Order item = orderTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to view.");
			alert.showAndWait();
			return;
		}
		if (!item.getState()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Order is inactive.");
			alert.showAndWait();
			return;
		}

		PaymentPageUI.setOrder(item);
		PaymentPageUI.setBackPage("MyOrdersUI.fxml");
		goToPaymentPage();
	}

	/**
	 * Takes the selected Order, sets it as a modified order, then calls {@link #goToOrderScreen} and loads the order screen with the order's data
	 * Modified orders cannot have previously added items removed or modified, total excluded
	 */
	public void editOrder() {
		Order orderToEdit = orderTableView.getSelectionModel().getSelectedItem();
		if (orderToEdit == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to edit.");
			alert.showAndWait();
			return;
		}
		if (!orderToEdit.getState()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An order you selected is inactive. Cannot modify.");
			alert.showAndWait();
			return;
		}

		CurrentOrderUI.setOrder(orderToEdit);
		goToOrderScreen();
	}

	/**
	 * Removes the selected Order from the list and user's order list
	 */
	public void deleteOrder() {
		if (!LoginUI.getUser().isManager()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Not authorized.");
			alert.showAndWait();
			return;
		}
		Order itemToDelete = orderTableView.getSelectionModel().getSelectedItem();
		if (itemToDelete == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to delete.");
			alert.showAndWait();
			return;
		}

		orderObservableList.remove(itemToDelete);
		orderTableView.setItems(orderObservableList);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Order " + itemToDelete.getOrderNumber() + " has been deleted.");
		alert.showAndWait();
		OrderDb.deleteItem(itemToDelete.getOrderNumber());
		displayAllOrder();
	}

	/**
	 * Display PaymentPageUI stage and closes the current (MyOrdersUI) stage
	 */
    public void goToPaymentPage() {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaymentPageUI.fxml"));
    	NextStage.goTo(fxmlLoader, payBtn);
    }
    
    /**
	 * Display CurrentOrderUI stage and closes the current (MyOrdersUI) stage
	 */
    public void goToOrderScreen() {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
    	NextStage.goTo(fxmlLoader, editBtn);
    }
    
	/**
	 * Creates a two-column table displaying an Order's number and that Order's total then calls {@link #displayAllOrder()}
	 * @param location Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("total"));
		orderObservableList = FXCollections.observableArrayList();
		orderTableView.setItems(orderObservableList);
		displayAllOrder();
	}
}
