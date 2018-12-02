package com.amazonaws;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Represents interface to select the specialty pizza requested
 * 
 * @author Christopher
 *
 */
@SuppressWarnings("restriction")
public class SpecialtyPizzaUI implements Initializable {

	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private String specialtyName;
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

	private static String oldName;
	private static String oldSize;

	/**
	 * Selects the specialty's name
	 * 
	 * @param onClick An ActionEvent that sets the pizza's name as the button's name
	 */
	public void selectSpecialty(ActionEvent onClick) {
		specialtyName = ((Button) onClick.getSource()).getId();
		showSpecialty();
	}

	/**
	 * Selects the specialty's size
	 * 
	 * @param onClick An ActionEvent that sets the size as the button's name
	 */
	public void selectSize(ActionEvent onClick) {
		specialtySize = ((Button) onClick.getSource()).getId();
		showSpecialty();
	}

	/**
	 * Displays selected specialty name and size
	 */
	private void showSpecialty() {
		String str = "";
		if (specialtyName != null) {
			str += specialtyName;
		}
		if (specialtySize != null) {
			str += " " + specialtySize;
		}
		specialtyTF.setText(str);
	}

	/**
	 * Sets the old specialty pizza's name and size
	 * 
	 * @param name A string representing the old specialty pizza's name
	 * @param size A string representing the old specialty pizza's size
	 */
	public static void setName(String name, String size) {
		oldName = name;
		oldSize = size;
	}

	/**
	 * Clears the selected specialty pizza name and size
	 */
	public void clearSpecialty() {
		specialtyTF.clear();
		specialtyName = null;
		specialtySize = null;
	}

	/**
	 * Applies the selected specialty name and toppings to the pizza and calls
	 * {@link #goToSpecialtyIntoCustom}
	 */
	public void confirmSpecialty() {

		if (specialtyName != null && specialtySize != null) {
			oldName = "";
			oldSize = "";
			goToSpecialtyIntoCustom();
		} else {
			Alert.Display("Error", "Please select a special and size.");
		}
	}

	/**
	 * Passes the current specialty pizza's name and toppings to the next
	 * (SpecialtyIntoCustomUI) page
	 * 
	 * @throws Exception if FXML file not found
	 */
	public void goToSpecialtyIntoCustom() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyIntoCustomUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.setScene(new Scene(root, 600, 600));
			nextStage.setResizable(false);

			SpecialtyIntoCustomUI display = fxmlLoader.getController();
			display.getSpecialtyInfo(specialtyName, specialtySize);

			nextStage.show();
			Stage currentStage = (Stage) confirmBtn.getScene().getWindow();
			currentStage.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Display CurrentOrderUI stage and closes the current (SpecialtyPizzaUI) stage
	 */
	public void goToOrderScreen() {
		oldName = "";
		oldSize = "";
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CurrentOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, cancelBtn);
	}

	/**
	 * Sets the previously selected name and size if applicable
	 * 
	 * @param location  Required for initialize method, unused
	 * @param resources Required for initialize method, unused
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (oldName != null && oldSize != null && oldName != "" && oldSize != "") {
			specialtyName = oldName;
			specialtySize = oldSize;
			specialtyTF.setText(oldName + " " + oldSize);
		}
	}
}
