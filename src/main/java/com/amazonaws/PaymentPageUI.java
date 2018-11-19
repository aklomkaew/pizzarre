package com.amazonaws;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	
	private double payment;
	private static double total;
	
	public void checkPayment(ActionEvent e) {
		try {
			payment = Double.parseDouble(paymentTF.getText());
			displayChange(e);
		} catch (NumberFormatException nfe ) {
			Alert.Display("Error", "Payment must be a number.");
			return;
		}
		confirmPayment();
	}
	
	private void confirmPayment() {
		if(payment == total) {
			// order becomes inactive
		}
		else if(payment < total) {
			// update remainder
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
	
	private static void setPayment(int t) {
		total = t;
	}
	
	public void goToMainMenu(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		NextStage.goTo(fxmlLoader, backBtn);
	}


	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	 //retrieve order
		//totalCostTF.setText("$" + Double.toString(orderItem.getTotal()));
	}
}