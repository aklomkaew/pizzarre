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
public class MyOrdersUI extends Application {
	
	

    @FXML
    private Button backBtn;
	
 


	
public void goToMainMenu(ActionEvent e) {
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
	            Parent root = (Parent) fxmlLoader.load();
	            Stage mainMenuStage = new Stage();
	            mainMenuStage.setScene(new Scene(root));
	            mainMenuStage.setTitle("Order Screen");
	            mainMenuStage.show();
	            Stage myOrdersStage = (Stage) backBtn.getScene().getWindow();
	            myOrdersStage.close();
	    } catch(Exception exception) {
	       exception.printStackTrace();
	      }
}


	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
}
