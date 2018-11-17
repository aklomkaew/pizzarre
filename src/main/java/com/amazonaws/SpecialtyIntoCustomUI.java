package com.amazonaws;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ListView;
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
public class SpecialtyIntoCustomUI implements Initializable {
	
	@FXML
	private Button confirm;
	@FXML
	private Button back;
	
	private String id = null; //names used for database access
	@FXML
	private Button pepperoni;
	@FXML
	private Button sausage;
	@FXML
	private Button groundBeef;
	@FXML
	private Button ham;
	@FXML
	private Button chicken;
	@FXML
	private Button steak;
	@FXML
	private Button anchovy;
	@FXML
	private Button shrimp;
	@FXML
	private Button tofu;
	@FXML
	private Button mushroom;
	@FXML
	private Button onion;
	@FXML
	private Button greenPepper;
	@FXML
	private Button tomato;
	@FXML
	private Button olive;
	@FXML
	private Button pineapple;
	@FXML
	private String toppingName;
	@FXML
	private ListView<String> toppingListView = new ListView<String>();

	private ObservableList<String> toppingObservableList = FXCollections.observableArrayList();

	private ArrayList<String> toppingIdArrayList = new ArrayList<String>();

	private static final int SMALL = 1;
	private static final int MEDIUM = 2;
	private static final int LARGE = 3;
    
	String specialtyName;
	String specialtySize;

	
	public void addRemoveTopping(ActionEvent e) {

		String id = ((Button) e.getSource()).getId();

		if (toppingIdArrayList.contains(id) == false) { // if statements adds topping to the list

			System.out.println(id + " added");
			toppingIdArrayList.add(id);
			toppingObservableList.add(id);

		} else { // else statement removes topping from the list

			System.out.println(id + " removed");
			toppingIdArrayList.remove(id);
			toppingObservableList.remove(id);
		}

		toppingListView.setItems(toppingObservableList); // displays toppings in the list
	}
	
	public void confirmSpecialty (ActionEvent e) { // passes specialty data back to NewOrderUI.java, do not NextStage.goTo
		specialtySize = specialtySize.toUpperCase();
		int size = 2;
		//enumeration statement for specialtySize
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage nextStage = new Stage();
			nextStage.setScene(new Scene(root, 600, 600));
			nextStage.setResizable(false);
			NewOrderUI display = fxmlLoader.getController();
			display.makeSpecialtyPizzaObject(specialtyName, toppingIdArrayList, size);
	        nextStage.show();
	        Stage currentStage = (Stage) confirm.getScene().getWindow();
	        currentStage.close();
	        
	    } catch(Exception exception) {
	    	exception.printStackTrace();
	      }
	}


	public void goToSpecialty (ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpecialtyPizzaUI.fxml"));
		NextStage.goTo(fxmlLoader, back);
	}

	public void start(Stage arg0) throws Exception {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void getSpecialtyInfo(String recipeName, String size) { // gets recipe name and size from previous controller (SpecialtyPizzaUI.java)
		this.specialtyName = recipeName;
		this.specialtySize = size;
		toppingIdArrayList = RecipeDb.getIngredients(specialtyName);
		toppingObservableList = FXCollections.observableArrayList(toppingIdArrayList);
		toppingListView.setItems(toppingObservableList);
	}
	
}
