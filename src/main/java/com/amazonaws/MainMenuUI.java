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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings({ "unused" })
public class MainMenuUI extends Application{
	
	
    @FXML
    private Button newOrderBtn;
    @FXML
    private Button myOrdersBtn;
    @FXML
    private Button mUtilBtn;
    @FXML
    private Button logoutBtn;
	

public void goToNewOrder(ActionEvent e) {
		
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewOrderUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage newOrderStage = new Stage();
	            newOrderStage.setScene(new Scene(root));
	            newOrderStage.setTitle("Order Screen");
	            newOrderStage.show();
	            Stage mainMenuStage = (Stage) newOrderBtn.getScene().getWindow();
	            mainMenuStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
	}

public void goToMyOrders(ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyOrdersUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage newOrderStage = new Stage();
	            newOrderStage.setScene(new Scene(root));
	            newOrderStage.show();
	            Stage mainMenuStage = (Stage) myOrdersBtn.getScene().getWindow();
	            mainMenuStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	    }
	      
}
	
public void goToManagerUtilities(ActionEvent e) {
	
	//if (manager) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerUtilitiesUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage managerUtilitiesStage = new Stage();
	            managerUtilitiesStage.setScene(new Scene(root));
	            managerUtilitiesStage.show();
	            Stage mainMenuStage = (Stage) mUtilBtn.getScene().getWindow();
	            mainMenuStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
	//}else{
	//Alert.Display("Error", "Manager credentials required.");
	//}
	
}

public void logout (ActionEvent event){
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage loginStage = new Stage();
	            loginStage.setScene(new Scene(root));
	            loginStage.setTitle("Login");
	            loginStage.show();
	            Stage mainMenuStage = (Stage) logoutBtn.getScene().getWindow();
	            mainMenuStage.close();
	    } catch(Exception e) {
	       e.printStackTrace();
	      }
}

	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
}
