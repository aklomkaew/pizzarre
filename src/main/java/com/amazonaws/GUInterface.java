package com.amazonaws;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.geometry.*;

@SuppressWarnings({ "restriction", "unused" })
public class GUInterface extends Application{

	//Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Stage window = primaryStage;
		window.setTitle("PIZZARRE");
		
		
		VBox root = new VBox(10);
		LoginUI login = new LoginUI();
		GridPane pad = new GridPane();
		pad = login.makeNumPad(pad);
		
		
		Scene scene = new Scene(root,720,656);
		root.getChildren().add(pad);
		root.setAlignment(Pos.CENTER);
		root.setCenterShape(true);
		
		
		window.setScene(scene);
		window.setResizable(false);
		window.show();
		
	}
}

		


