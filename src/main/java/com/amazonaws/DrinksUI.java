package com.amazonaws;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class DrinksUI {
	
	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private String drinkName = null;
	@FXML
	private String drinkSize = null;
	@FXML
	private ToggleButton smallBtn;
	@FXML
	private ToggleButton mediumBtn;
	@FXML
	private ToggleButton largeBtn;
	@FXML
    private ToggleButton pepsi;
    @FXML
    private ToggleButton sevenUp;
    @FXML
    private ToggleButton grapico;
    @FXML
    private ToggleButton tea;
    @FXML
    private ToggleButton beer;
    @FXML
    private ToggleButton wine;
    

	
	public void selectDrink (ActionEvent e) {
		drinkName = ((ToggleButton)e.getSource()).getText(); // sets drink name equal to text on a button
	}
	
	public void selectSize (ActionEvent e) {
		drinkSize = ((ToggleButton)e.getSource()).getText();
	}
	
	public void confirmDrink (ActionEvent e) {
		//add drink to order here //drink = inventory.getDrink(drinkName)
		if(drinkName == null || drinkSize == null) {
			Alert.Display("ERROR", "Select a drink and size.");
		} else {
		System.out.println("You chose " + drinkName + " of size " + drinkSize);
		
		try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		            Parent root = (Parent) fxmlLoader.load();
		            Stage newOrderStage = new Stage();
		            newOrderStage.setScene(new Scene(root));
		            newOrderStage.setTitle("Order Screen");
		            newOrderStage.show();
		            Stage drinkStage = (Stage) confirmBtn.getScene().getWindow();
		            drinkStage.close();
		    } catch(Exception exception) {
		       exception.printStackTrace();
		      }
		}
	}
	
public void cancelDrink (ActionEvent e) {
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage newOrderStage = new Stage();
	            newOrderStage.setScene(new Scene(root));
	            newOrderStage.setTitle("Order Screen");
	            newOrderStage.show();
	            Stage drinkStage = (Stage) cancelBtn.getScene().getWindow();
	            drinkStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
	}

public void start(Stage arg0) throws Exception {
		}
	
}
