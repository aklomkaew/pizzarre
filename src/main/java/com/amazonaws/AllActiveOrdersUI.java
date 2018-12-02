package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Represents interface to display a list of all unpaid tabs/orders within the
 * system Orders displayed on this interface only if (Order.getState() == true)
 * 
 * @author Christopher
 *
 */
@SuppressWarnings({ "unused", "restriction" })
public class AllActiveOrdersUI implements Initializable {
	@FXML
	private Button backBtn;
	@FXML
	private Button editOrderBtn;
	@FXML
	private Button deleteOrderBtn;
	@FXML
	private Button deleteAllOrderBtn;
	@FXML
	private Button payOrderBtn;
	@FXML
	private TableView<Order> orderTableView;
	@FXML
	private TableColumn<Order, Integer> serverColumn;
	@FXML
	private TableColumn<Order, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Order, Double> totalColumn;

	private ObservableList<Order> orderObservableList;

	private static Order selectedOrder;

	/**
	 * Gets the selected Order object
	 * 
	 * @return An Order object representing a selected order
	 */
	public static Order getOrder() {
		return selectedOrder;
	}

	/**
	 * Display AllOrdersUI stage and closes the current (AllActiveOrdersUI) stage
	 */
	public void goToManagerUtilities() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllOrdersUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	/**
	 * Takes selected Order from list and displays CurrentOrderUI stage with its
	 * contents and closes the current (AllActiveOrdersUI) stage
	 */
	public void editOrder() {
		Order item = orderTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to modify.");
			alert.showAndWait();
			return;
		}
		CurrentOrderUI.setOrder(item);
		deleteOrder(item);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, editOrderBtn);
	}

	/**
	 * Takes selected Order from list and removes it from the Order database
	 * 
	 * @param An Order object representing the Order being deleted
	 */
	private void deleteOrder(Order o) {
		orderObservableList.remove(o);
		orderTableView.setItems(orderObservableList);
		OrderDb.deleteItem(o.getOrderNumber());
	}

	/**
	 * Confirms if an Order was selected before calling {@link #deleteOrder(Order)}
	 * and updating the Order list
	 */
	public void deleteOrder() {
		Order itemToDelete = orderTableView.getSelectionModel().getSelectedItem();
		if (itemToDelete == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to delete.");
			alert.showAndWait();
			return;
		}

		deleteOrder(itemToDelete);
		displayAllActiveOrder();
	}

	/**
	 * A method to remove all unpaid orders from the database
	 */
	public void deleteAllOrder() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete all active order?");

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			List<Order> list = getActiveOrders();
			for (Order item : list) {
				OrderDb.deleteItem(item.getOrderNumber());
			}
			displayAllActiveOrder();
		}
	}

	/**
	 * Takes selected Order and loads PaymentPageUI stage with it, closes current
	 * (AllActiveOrdersUI) stage CustomOrderUI stage with its contents and closes
	 * the current (AllActiveOrdersUI) stage
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
		PaymentPageUI.setBackPage("AllActiveOrdersUI.fxml");

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaymentPageUI.fxml"));
		NextStage.goTo(fxmlLoader, payOrderBtn);
	}

	/**
	 * Loads a list with active Orders
	 * 
	 * @return a List displaying all Orders where (Order.getState() == true);
	 */
	private List<Order> getActiveOrders() {
		List<Order> list = OrderDb.retrieveAllItem();

		if (list == null || list.size() < 1) {
			return Collections.emptyList();
		}
		List<Order> activeList = new ArrayList<Order>();
		for (Order item : list) {
			if (item.getState()) {
				activeList.add(item);
			}
		}
		return activeList;
	}

	/**
	 * Displays all active orders to the screen
	 */
	public void displayAllActiveOrder() {
		List<Order> activeList = getActiveOrders();

		orderObservableList.clear();
		orderObservableList.addAll(activeList);
		System.out.println("\nPrint from observableList");
		for (Order item : orderObservableList) {
			System.out.println("Id = " + item.getOrderNumber() + " server name = " + item.getServerName() + " total = "
					+ item.getTotal());
		}
	}

	/**
	 * Creates a two-column table displaying an Order's number and that Order's
	 * total then loads it with all active Orders
	 * 
	 * @param location  Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		serverColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("serverId"));
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("total"));
		orderObservableList = FXCollections.observableArrayList();
		orderTableView.setItems(orderObservableList);
		displayAllActiveOrder();
	}

}