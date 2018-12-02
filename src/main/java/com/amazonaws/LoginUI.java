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
	public void pressedOne(ActionEvent e) {

		idNum.add("1");
		System.out.println("1");
		passwordPF.appendText("*");
	}

	public void pressedTwo(ActionEvent e) {

		idNum.add("2");
		System.out.println("2");
		passwordPF.appendText("*");
	}

	public void pressedThree(ActionEvent e) {

		idNum.add("3");
		System.out.println("3");
		passwordPF.appendText("*");
	}

	public void pressedFour(ActionEvent e) {

		idNum.add("4");
		System.out.println("4");
		passwordPF.appendText("*");
	}

	public void pressedFive(ActionEvent e) {

		idNum.add("5");
		System.out.println("5");
		passwordPF.appendText("*");
	}

	public void pressedSix(ActionEvent e) {

		idNum.add("6");
		System.out.println("6");
		passwordPF.appendText("*");
	}

	public void pressedSeven(ActionEvent e) {

		idNum.add("7");
		System.out.println("7");
		passwordPF.appendText("*");
	}

	public void pressedEight(ActionEvent e) {

		idNum.add("8");
		System.out.println("8");
		passwordPF.appendText("*");
	}

	public void pressedNine(ActionEvent e) {

		idNum.add("9");
		System.out.println("9");
		passwordPF.appendText("*");
	}

	public void pressedZero(ActionEvent e) {

		idNum.add("0");
		System.out.println("0");
		passwordPF.appendText("*");
	}

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

	private String getPasscode(ArrayList<String> list) {
		String ret = "";

		for (String s : list) {
			ret += s;
		}

		return ret;
	}

	public static <T extends User> T getUser() {
		if (isManager) {
			return (T) manager;
		}
		return (T) user;
	}

	public void clearInput(ActionEvent e) {
		idNum.clear();
		passwordPF.clear();
	}

	public void shutdown(ActionEvent e) {
		Stage loginStage = (Stage) shutdownBtn.getScene().getWindow();
		loginStage.close();
	}

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

	public static void main(String[] args) {
		launch(args);
	}
}
