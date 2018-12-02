package com.amazonaws;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

@SuppressWarnings("restriction")
public class AllOrdersUI implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button showOrderBtn;
	@FXML
	private Button editOrderBtn;
	@FXML
	private Button deleteOrderBtn;
	@FXML
	private Button allActiveOrdersBtn;
	@FXML
	private Button deleteAllOrderBtn;
	@FXML
	private Button refreshBtn;
	@FXML
	private TableView<Order> orderTableView;
	@FXML
	private TableColumn<Order, Integer> serverColumn;
	@FXML
	private TableColumn<Order, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Order, Double> totalColumn;

	private ObservableList<Order> orderObservableList;

	public void displayAllOrder(ActionEvent e) {
		displayAllOrder();
	}

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

	public void editOrder() {

		Order item = orderTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to modify.");
			alert.showAndWait();
			return;
		}

		if (!item.getState()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An order you selected is inactive. Cannot modify.");
			alert.showAndWait();
			return;
		}

		CurrentOrderUI.setOrder(item);
		goToOrderScreen();
	}

	public void deleteOrder(ActionEvent e) {

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

	public void deleteAllOrder(ActionEvent e) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete all order?");

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			List<Order> list = OrderDb.retrieveAllItem();
			for (Order item : list) {
				OrderDb.deleteItem(item.getOrderNumber());
			}
		}
		displayAllOrder();
	}

	public void displayAllOrder() {

		List<Order> list = OrderDb.retrieveAllItem();
		if (list == null || list.size() < 1) {
			orderObservableList.clear();
			orderObservableList.addAll(list);
			return;
		}

		orderObservableList.clear();
		orderObservableList.addAll(list);
	}

	public void goToAllActiveOrders() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllActiveOrdersUI.fxml"));
		NextStage.goTo(fxmlLoader, allActiveOrdersBtn);
	}

	public void goToManagerUtilities() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	public void goToOrderScreen() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, editOrderBtn);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		serverColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("serverId"));
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("total"));
		orderObservableList = FXCollections.observableArrayList();
		orderTableView.setItems(orderObservableList);
		displayAllOrder();
	}

}
