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
public class SpecialtyPizzaUI {
	
	@FXML
	private Button confirmBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private String specialtyName = null;
	@FXML
	private String specialtySize = null;
	@FXML
	private ToggleButton smallBtn;
	@FXML
	private ToggleButton mediumBtn;
	@FXML
	private ToggleButton largeBtn;
	@FXML
    private ToggleButton houseSpecial;
    @FXML
    private ToggleButton meatzza;
    @FXML
    private ToggleButton hawaiian;
    @FXML
    private ToggleButton bbq;
    @FXML
    private ToggleButton vegetarian;
    @FXML
    private ToggleButton margherita;
    

	
	public void selectSpecialty (ActionEvent e) {
		specialtyName = ((ToggleButton)e.getSource()).getText(); // sets specialty name equal to text on a button
	}
	
	public void selectSize (ActionEvent e) {
		specialtySize = ((ToggleButton)e.getSource()).getText();
	}
	
	public void confirmSpecialty (ActionEvent e) {
		//add specialty to order here //specialty = recipeDB.getRecipe(specialtyName)
		if(specialtyName == null || specialtySize == null) {
			Alert.Display("ERROR", "Select a specialty and size.");
		} else {
		System.out.println("You chose " + specialtyName + " of size " + specialtySize);
		
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
	
public void cancelSpecialty (ActionEvent e) {
	
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
