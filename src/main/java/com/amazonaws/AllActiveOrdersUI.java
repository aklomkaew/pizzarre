package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
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

@SuppressWarnings({ "unused" })
public class AllActiveOrdersUI extends Application implements Initializable {
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
	
	public static Order getOrder() {
		return selectedOrder;
	}

	public void goToManagerUtilities(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllOrdersUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}

	public void editOrder(ActionEvent e) {
		Order item = orderTableView.getSelectionModel().getSelectedItem();
		if (item == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Select an order to modify.");
			alert.showAndWait();
			return;
		}
		NewOrderUI.setOrder(item);
		deleteOrder(item);
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, editOrderBtn);
	}

	private void deleteOrder(Order o) {
		orderObservableList.remove(o);
		orderTableView.setItems(orderObservableList);
		OrderDb.deleteItem(o.getOrderNumber());
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

		deleteOrder(itemToDelete);
		displayAllActiveOrder();
	}

	public void deleteAllOrder(ActionEvent e) {
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

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaymentPageUI.fxml"));
		NextStage.goTo(fxmlLoader, payOrderBtn);
	}
	
	private List<Order> getActiveOrders() {
		List<Order> list = OrderDb.retrieveAllItem();

		if (list == null || list.size() < 1) {
			return null;
		}
		List<Order> activeList = new ArrayList<Order>();
		for(Order item : list) {
			if(item.getState()) {
				activeList.add(item);
			}
		}
		return activeList;
	}

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

	@Override
	public void start(Stage arg0) throws Exception {

	}

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