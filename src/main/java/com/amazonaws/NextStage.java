package com.amazonaws;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Represents interface go to another stage and close the current stage
 * @author Christopher
 *
 */

@SuppressWarnings("restriction")
public class NextStage {
	
	/**
	 * Goes to another stage and closes the current stage
	 * @param fxmlLoader An FXMLLoader containing fxml file data to open a new stage
	 * @param closeStageBtn A button representing an element on the current stage as a reference point to close the current stage
	 * @throws Exception if either parameter is not valid
	 */
	
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