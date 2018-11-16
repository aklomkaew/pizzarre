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
	private TableView<Cart> orderTableView;
	@FXML
	private TableColumn<Cart, Integer> orderNumberColumn;
	@FXML
	private TableColumn<Cart, Double> totalColumn;

	private ObservableList<Cart> orderObservableList;

	public void goToMainMenu(ActionEvent e) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage mainMenuStage = new Stage();
			mainMenuStage.setScene(new Scene(root));
			mainMenuStage.setTitle("Order Screen");
			mainMenuStage.show();
			Stage myOrdersStage = (Stage) backBtn.getScene().getWindow();
			myOrdersStage.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void displayAllOrder() {
		List<Cart> list = CartDb.retrieveFilteredItem(LoginUI.getUser().getUserId());

		if(list == null || list.size() < 1) {
			return;
		}
		
		orderObservableList.clear();
		orderObservableList.addAll(list);
		System.out.println("\nPrint from observableList");
		for (Cart item : orderObservableList) {
			System.out.println("Id = " + item.getOrderNumber() + " server name = " + item.getServerName()
			+ " total = " + item.getTotal());
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberColumn.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("orderNumber"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Cart, Double>("total"));
		orderObservableList = FXCollections.observableArrayList();
		orderTableView.setItems(orderObservableList);
		displayAllOrder();
	}
}
