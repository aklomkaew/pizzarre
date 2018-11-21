package com.amazonaws;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SpecialtyPizzaUI {

	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private String specialtyName; // names used for display purposes
	@FXML
	private String specialtySize;
	@FXML
	private Button small;
	@FXML
	private Button medium;
	@FXML
	private Button large;
	@FXML
	private Button dailySpecialPizza;
	@FXML
	private Button meatzzaPizza;
	@FXML
	private Button hawaiianPizza;
	@FXML
	private Button classicPizza;
	@FXML
	private Button veggiePizza;
	@FXML
	private Button sicilianPizza;
	@FXML
	private TextField specialtyTF;

	public void selectSpecialty(ActionEvent e) {
		specialtyName = ((Button) e.getSource()).getId(); // sets specialty name equal to text on a button
		showSpecialty(e);
	}

	public void selectSize(ActionEvent e) {
		specialtySize = ((Button) e.getSource()).getId();
		showSpecialty(e);
	}

	public void showSpecialty(ActionEvent e) {
		String str = "";
		if (specialtyName != null) {
			str += specialtyName;
		}
		if (specialtySize != null) {
			str += " " + specialtySize;
		}
		specialtyTF.setText(str);
	}

	public void clearSpecialty(ActionEvent e) {
		specialtyTF.clear();
		specialtyName = null;
		specialtySize = null;
	}

	public void confirmSpecialty(ActionEvent e) {

		if (specialtyName != null && specialtySize != null) {
			goToSpecialtyIntoCustom(e);
		} else {
			Alert.Display("Error", "Please select a special and size.");
		}
	}

	public void goToSpecialtyIntoCustom(ActionEvent e) { // do not NextStage.goTo this one
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyIntoCustomUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.setScene(new Scene(root, 600, 600));
			nextStage.setResizable(false);
			SpecialtyIntoCustomUI display = fxmlLoader.getController();
			display.getSpecialtyInfo(specialtyName, specialtySize); // passes specialty recipe onto
																	// BuildSpeciatyIntoCustom.java
			nextStage.show();
			Stage currentStage = (Stage) confirmBtn.getScene().getWindow();
			currentStage.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void goToOrderScreen(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancelBtn);
	}

	public void start(Stage arg0) throws Exception {

	}

}
