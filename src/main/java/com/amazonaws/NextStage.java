package com.amazonaws;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class NextStage {
	
	public static void goTo(FXMLLoader fxmlLoader, Button closeStageBtn) {
		try {
			
				Parent root = (Parent) fxmlLoader.load();
				Stage nextStage = new Stage();
				nextStage.setScene(new Scene(root, 600, 600));
				nextStage.setResizable(false);
    	        nextStage.show();
    	        Stage currentStage = (Stage) closeStageBtn.getScene().getWindow();
    	        currentStage.close();
    	        
    	    } catch(Exception exception) {
    	    	exception.printStackTrace();
    	      } 
    }
}