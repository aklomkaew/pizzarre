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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class MyOrdersUI extends Application implements Initializable{

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
	private TableView<Order> orderTableView;
	@FXML
	private TableColumn<Order, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Order, Double> totalColumn;

	private ObservableList<Order> orderObservableList;
	
	private Order selectedOrder;

	public void goToMainMenu(ActionEvent e) {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, backBtn);
	}
	
	public Order getOrder() {
		return selectedOrder;
	}
	
	public void displayAllOrder() {
		List<Order> list = OrderDb.retrieveFilteredItem(LoginUI.getUser().getUserId());

		if(list == null || list.size() < 1) {
			return;
		}
		
		orderObservableList.clear();
		orderObservableList.addAll(list);
		System.out.println("\nPrint from observableList");
		for (Order item : orderObservableList) {
			System.out.println("Id = " + item.getOrderNumber() + " server name = " + item.getServerName()
			+ " total = " + item.getTotal());
		}
	}
	
	public void showOrder(ActionEvent e) {
		
	}
	
	public void payOrder(ActionEvent e) {
		int index = orderTableView.getSelectionModel().getFocusedIndex();
		selectedOrder = orderTableView.getItems().get(index);
		
		try { // im sorry
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaymentPageUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.setScene(new Scene(root, 600, 600));
			nextStage.setResizable(false);
			
			 PaymentPageUI display = fxmlLoader.getController();
			 display.initializeMyOrder(selectedOrder);
			
			nextStage.show();
			Stage currentStage = (Stage) payBtn.getScene().getWindow();
			currentStage.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
	
	public void editOrder(ActionEvent e) {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, editBtn);
	}

	public void deleteOrder(ActionEvent e) {
	
	}

	@Override
	public void start(Stage arg0) throws Exception {

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
