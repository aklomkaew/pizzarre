package com.amazonaws;

import java.net.URL;
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
import javafx.scene.control.ListView;
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
	private TableColumn<Order, String> serverColumn;
	@FXML
	private TableColumn<Order, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Order, Double> totalColumn;

	private ObservableList<Order> orderObservableList;

	public void goToManagerUtilities(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllOrdersUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}
  
  public void editOrder(ActionEvent e) {
    	
  }

  public void deleteOrder(ActionEvent e) {

  }

  public void deleteAllOrder(ActionEvent e) {

  }
  
  public void payOrder(ActionEvent e) {
	  //get order
	  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PaymentPageUI.fxml"));
		NextStage.goTo(fxmlLoader, payOrderBtn);
  }
  
	public void displayAllActiveOrder() {
		List<Order> list = OrderDb.retrieveAllItem();

		if(list == null || list.size() < 1) {
			return;
		}
		List<Order> activeList = list;
		for(int i = 0; i < activeList.size(); i++)
		{
			if(activeList.get(i).getState() == false)
			{
				activeList.remove(i);
			}
		}
		
		orderObservableList.clear();
		orderObservableList.addAll(activeList);
		System.out.println("\nPrint from observableList");
		for (Order item : orderObservableList) {
			System.out.println("Id = " + item.getOrderNumber() + " server name = " + item.getServerName()
			+ " total = " + item.getTotal());
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		serverColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("server"));
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Order, Double>("total"));
		orderObservableList = FXCollections.observableArrayList();
		orderTableView.setItems(orderObservableList);
		displayAllActiveOrder();
	}

}