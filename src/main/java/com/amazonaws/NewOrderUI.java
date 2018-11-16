package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class NewOrderUI implements Initializable {

	@FXML
	private Button mainMenu;
	@FXML
	private Button drink;
	@FXML
	private Button special;
	@FXML
	private Button custom;
	@FXML
	private Button discount;
	@FXML
	private Button confirm;

	private static Order order;

	public void goToMainMenu(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to return without saving order?");

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, mainMenu);
		}
	}

	public void goToDrinks(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrinksUI.fxml"));
		NextStage.goTo(fxmlLoader, drink);
	}

	public void goToSpecialty(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BuildSpecialtyUI.fxml"));
		NextStage.goTo(fxmlLoader, special);
	}

	public void goToCustom(ActionEvent e) {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, custom);
	}

	public void setDiscount(ActionEvent e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Method not set for setDiscount()");
		alert.showAndWait();
	}

	public void confirmOrder(ActionEvent e) {
		int num = order.getItemNum();
		while (!OrderDb.addOrder(order)) {
			num++;
			order.setOrderNumber(num);
		}

		OrderDb.updateOrder(order);
		User u = LoginUI.getUser();
		u.getOrderList().add(order);
		UserDb.updateUser(u);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Your order has been placed!");
		alert.showAndWait();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, confirm);
	}

	public void discardOrder(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to cancel your order?");
		alert.showAndWait();

		Optional<ButtonType> option = alert.showAndWait();
		if (option.get() == null) {
			return;
		} else if (option.get() == ButtonType.CANCEL) {
			return;
		} else if (option.get() == ButtonType.OK) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, mainMenu);
		}
	}

	public void start(Stage arg0) throws Exception {
		System.out.println("In start");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User u = LoginUI.getUser();
		if (order == null) {
			order = new Order();

			ArrayList<Order> allOrder = OrderDb.retrieveAllItem();
			int num = allOrder.size(); // order number
			order.setOrderNumber(num);
			order.setServerId(u.getUserId());
			order.setServerName(u.getName());
		}

	}

	public static Order getOrder() {
		return order;
	}
}
