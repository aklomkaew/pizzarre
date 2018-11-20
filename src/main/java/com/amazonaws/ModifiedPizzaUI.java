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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class ModifiedPizzaUI implements Initializable {

	@FXML
	private Button confirm;
	@FXML
	private Button back;

	private String id = null; // names used for database access
	@FXML
	private TextField sizeTF;
	@FXML
	private Button pepperoni;
	@FXML
	private Button sausage;
	@FXML
	private Button groundbeef;
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
	private Button greenpepper;
	@FXML
	private Button tomato;
	@FXML
	private Button olive;
	@FXML
	private Button pineapple;
	@FXML
	private ListView<String> toppingListView = new ListView<String>();

	private ObservableList<String> toppingObservableList = FXCollections.observableArrayList();

	private ArrayList<String> toppingIdArrayList = new ArrayList<String>();

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

	public void confirmPizza(ActionEvent e) { // passes specialty data back to NewOrderUI.java, do not NextStage.goTo
		// enumeration statement for specialtySize
		Order order = NewOrderUI.getOrder();
		int index = NewOrderUI.getModifiedIndex();
		Pizza oldPizza = order.getPizzas().get(index);
		ArrayList<String> emptyList = new ArrayList<String>(); // emptyList is necessary

		Pizza modifiedPizza = new Pizza(oldPizza.getName(), oldPizza.getSize(), emptyList);

		for (int i = 0; i < toppingIdArrayList.size(); i++) { // loop that adds and increments pizza's price
			String topping = toppingIdArrayList.get(i);
			modifiedPizza.addTopping(topping);
		}

		order.getPizzas().set(index, modifiedPizza);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, confirm);

//		try {
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
//			Parent root = (Parent) fxmlLoader.load();
//			Stage nextStage = new Stage();
//			nextStage.setScene(new Scene(root, 600, 600));
//			nextStage.setResizable(false);
//			
//			NewOrderUI display = fxmlLoader.getController();
//			display.modifiedPizza();
//	        
//			nextStage.show();
//	        Stage currentStage = (Stage) confirm.getScene().getWindow();
//	        currentStage.close();
//	        
//	    } catch(Exception exception) {
//	    	exception.printStackTrace();
//	      }
	}

	public void start(Stage arg0) throws Exception {

	}

	public void goToOrderScreen(ActionEvent e) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
		NextStage.goTo(fxmlLoader, back);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void getPizzaInfo(ArrayList<String> toppings, int size) { // gets recipe name and size from previous controller (SpecialtyPizzaUI.java)
		toppingIdArrayList = toppings;
		toppingObservableList.clear();
		toppingObservableList.addAll(toppings);
		toppingListView.setItems(toppingObservableList);
		
		 	if (size == 1) {
		 	sizeTF.setText("small");
		 	} else if (size == 2) {
		 	sizeTF.setText("medium");
		 	} else {
		 	sizeTF.setText("large");
		 	}
	}

}
