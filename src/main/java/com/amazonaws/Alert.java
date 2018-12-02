package com.amazonaws;

import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.util.ArrayList;
import javafx.geometry.*;

/**
 * Represents interface to display a notification the user must acknowledge before continuing
 * @author Christopher
 *
 */

public class Alert {

	@SuppressWarnings("restriction")
	public static void Display(String title, String message) {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);

		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait(); // This means the window has to be closed to continue.
	}
}
