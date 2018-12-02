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

	public void refreshOrder(ActionEvent e) {
		
	}
	
	public void goToMainMenu(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	public Order getOrder() {
		
		return selectedOrder;
	}

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

	public void showOrder(ActionEvent e) {
		
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

	public void payOrder(ActionEvent e) {
		
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
		goToPaymentPage();
	}

	public void editOrder(ActionEvent e) {
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

	public void deleteOrder(ActionEvent e) {
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

    public void goToPaymentPage() {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaymentPageUI.fxml"));
    	NextStage.goTo(fxmlLoader, payBtn);
    }
    
    public void goToOrderScreen() {
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
    	NextStage.goTo(fxmlLoader, editBtn);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("total"));
		orderObservableList = FXCollections.observableArrayList();
		orderTableView.setItems(orderObservableList);
		displayAllOrder();
	}
}
