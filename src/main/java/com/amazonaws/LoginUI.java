package com.amazonaws;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * Represents an interface for a password protection of the program
 * @author Christopher
 *
 */

@SuppressWarnings("restriction")
public class LoginUI extends Application {

	@FXML
	private Button shutdownBtn;
	@FXML
	private Button b1;
	@FXML
	private Button b2;
	@FXML
	private Button b3;
	@FXML
	private Button b4;
	@FXML
	private Button b5;
	@FXML
	private Button b6;
	@FXML
	private Button b7;
	@FXML
	private Button b8;
	@FXML
	private Button b9;
	@FXML
	private Button b0;
	@FXML
	private Button confirmBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private PasswordField passwordPF;

	private static boolean isManager;
	private static User user;
	private static Manager manager;

	private ArrayList<String> idNum = new ArrayList<String>();

	public String getID(String id) {
		System.out.println(id);
		return id;
	}

	/*
	 * @FXML public void displayInput() { for(int i = 0; i<3; i++) {
	 * if(password.getLength() <=4) { if(idNum.get(i) != null) {
	 * password.appendText("*"); } } } }
	 */
	

	/**
	 * Appends the selected number to end of the password string
	 */
	
	public void pressedButton(ActionEvent onClick) {
		String number = ((Button) onClick.getSource()).getText();
		idNum.add(number);
		System.out.println(number);
		passwordPF.appendText("*");
	}

	/**
	 * Checks if the current passcode matches any user's password
	 * Proceeds to the next stage if it does
	 */
	
	public void confirmInput(ActionEvent e) {
		String inputId = getPasscode(idNum);
		User u = UserDb.getUser(inputId);
		if (idNum.size() != 4) {
			Alert.Display("ERROR", "This appears if the input is wrong.");
			passwordPF.clear();
			idNum.clear();
		} else if (u.getUserId() == -1) {
			Alert.Display("ERROR", "User not found.");
			passwordPF.clear();
			idNum.clear();
		} else {
			if (u.isManager()) {
				manager = UserDb.getUser(inputId);
				isManager = true;
			} else {
				user = u;
				isManager = false;
			}

			idNum.clear();
			passwordPF.clear();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, confirmBtn);
		}
	}

	/**
	 * Returns a string of the passcode from an ArrayList<String> of characters
	 * @param list An ArrayList<String> of letters representing a passcode
	 * @return A string representing the passcode
	 */
	
	private String getPasscode(ArrayList<String> list) {
		String ret = "";

		for (String s : list) {
			ret += s;
		}

		return ret;
	}

	/**
	 * Returns a manager class if the successfully logged in user is a manager
	 * Returns a user otherwise
	 * @return A manager representing an actor in the program
	 * @return A user representing an actor in the program
	 */
	
	public static <T extends User> T getUser() {
		if (isManager) {
			return (T) manager;
		}
		return (T) user;
	}

	/**
	 * Clears the current passcode data
	 */
	
	public void clearInput() {
		idNum.clear();
		passwordPF.clear();
	}

	/**
	 * Terminates the program
	 */
	
	public void shutdown() {
		Stage loginStage = (Stage) shutdownBtn.getScene().getWindow();
		loginStage.close();
	}

	/**
	 * Creates the GUI page and various databases for the program
	 * @param stage A stage variable representing the GUI interface of LoginUI
	 * @throws IOException if FXML file (LoginUI.fxml) not found
	 */
	
	@Override
	public void start(Stage stage) throws Exception {
		UserDb userDb = new UserDb();
		RecipeDb recipeDb = new RecipeDb();
		InventoryDb inventoryDb = new InventoryDb();
		OrderDb orderDb = new OrderDb();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginUI.fxml"));
			Scene scene = new Scene(root,600,600);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Starts the program
	 * @param args A String[] of supplied command line arguments
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
