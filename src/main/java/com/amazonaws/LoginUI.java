package com.amazonaws;

import java.awt.TextField;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


@SuppressWarnings({ "unused" })
public class LoginUI extends Application{
	
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
    private Button ok;
    @FXML
    private Button b0;
    @FXML
    private Button no;
    @FXML
    private PasswordField password;
	
    
    
	private String idInput;
	//private GridPane pad;
	private ArrayList<String> idNum = new ArrayList<String>();
	
	public String getID(String id) {
	    System.out.println(id);
		return id;
	}
/*	@FXML
	public void displayInput() {
		for(int i = 0; i<3; i++) {
			if(password.getLength() <=4) {
				if(idNum.get(i) != null) {
				password.appendText("*");
				}
			}
		}
	}
*/	
	public void pressedOne(ActionEvent e) {
		
		idNum.add("1");
		System.out.println("1");
		password.appendText("*");
	}
	public void pressedTwo(ActionEvent e) {
		
		idNum.add("2");
		System.out.println("2");
		password.appendText("*");
	}
	public void pressedThree(ActionEvent e) {
		
		idNum.add("3");
		System.out.println("3");
		password.appendText("*");
	}
	public void pressedFour(ActionEvent e) {
		
		idNum.add("4");
		System.out.println("4");
		password.appendText("*");
	}
	public void pressedFive(ActionEvent e) {
		
		idNum.add("5");
		System.out.println("5");
		password.appendText("*");
	}
	public void pressedSix(ActionEvent e) {
		
		idNum.add("6");
		System.out.println("6");
		password.appendText("*");
	}
	public void pressedSeven(ActionEvent e) {
		
		idNum.add("7");
		System.out.println("7");
		password.appendText("*");
	}
	public void pressedEight(ActionEvent e) {
		
		idNum.add("8");
		System.out.println("8");
		password.appendText("*");
	}
	public void pressedNine(ActionEvent e) {
		
		idNum.add("9");
		System.out.println("9");
		password.appendText("*");
	}
	public void pressedZero(ActionEvent e) {
		
		idNum.add("0");
		System.out.println("0");
		password.appendText("*");
	}

	public void confirmInput(ActionEvent e) {
		if(idNum.size()!=4) {
			Alert.Display("ERROR", "This appears if the input is wrong.");
		} else {
			idInput = idNum.toString();
			getID(idInput);
			
			//@FXML
		    //public void goToRecipeSelection (ActionEvent event){
		    	
			idNum.clear();
			password.clear();
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
			NextStage.goTo(fxmlLoader, ok);
		}
	}
		//idNum.clear();
		//password.clear();
		//}
	public void clearInput(ActionEvent e) {
		idNum.clear();
		password.clear();
	}
	
	public void shutdown (ActionEvent e) {
        Stage loginStage = (Stage) shutdownBtn.getScene().getWindow();
        loginStage.close();
	}
	
	@Override
	public void start(Stage stage) {
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
