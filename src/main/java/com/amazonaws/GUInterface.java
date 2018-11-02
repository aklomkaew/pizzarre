package com.amazonaws;
import java.awt.Panel;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.geometry.*;

@SuppressWarnings({ "unused" })
public class GUInterface extends Application{

	private int horizSize = 720;
	private int vertSize = 656;
	
	public int getHeight() {
		int h = vertSize;
		return h;
	}
	public int getWidth() {
		int w = horizSize;
		return w;
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	
	public void start(Stage stage) throws Exception {
		
		
		
		
		stage.setTitle("PIZZARRE");
		
		
		VBox root = new VBox(10);
		LoginUI login = new LoginUI();
		GridPane pad = new GridPane();
		pad = login.makeNumPad(pad);
		
		MainMenuUI mMenu = new MainMenuUI();
		BorderPane menu = new BorderPane();
		VBox menuRoot = new VBox();
		menu = mMenu.makeMenu(menu,menuRoot);
		
		
		menuRoot.getChildren().add(menu);
		menuRoot.setAlignment(Pos.CENTER);
		

		
		
		root.getChildren().add(pad);
		root.setAlignment(Pos.CENTER);
		root.setCenterShape(true);
		
		
		Scene scene1 = new Scene(menuRoot,horizSize,vertSize);
		stage.setScene(scene1);
		stage.setResizable(false);
		stage.show();
		
	}
}

		


