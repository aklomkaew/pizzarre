package com.amazonaws;


import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Node;
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
	
	GUInterface controller = new GUInterface();
	
	
	public  BorderPane makeMenu(BorderPane bp, VBox vb){
		bp.setPrefSize(controller.getHeight(), controller.getWidth());
		vb = new VBox(25);
		vb.setPrefSize(150, 345);
		vb.setMinSize(150, 345);
		vb.setMaxSize(150, 345);
		
		
		//Buttons//
		Button newOrd = new Button("New Order");
		newOrd.setMinSize(150, 100);
		newOrd.setMaxSize(150, 100);
		
		newOrd.setOnAction(e->{
			
		});
		
		Button myOrds = new Button("My Orders");
		myOrds.setMinSize(80, 80);
		myOrds.setMaxSize(80, 80);
		myOrds.setOnAction(e->{
			
		});
		
		Button mUtil = new Button("Manager Utilities");
		mUtil.setMinSize(80, 80);
		mUtil.setMaxSize(80, 80);
		mUtil.setWrapText(true);
		mUtil.setOnAction(e->{
			
		});
		
		Button logOut = new Button("Logout");
		logOut.setMinSize(60, 30);
		logOut.setMaxSize(60, 30);
		
		logOut.setOnAction(e->{
			
		});
		
		vb.getChildren().addAll(newOrd,myOrds,mUtil);
		
		
		

		bp = new BorderPane(null);
		bp.getChildren().addAll(vb,logOut);
		//vb.setAlignment(Pos.CENTER);
		bp.setAlignment(logOut,Pos.TOP_RIGHT);
		return bp;
	}

	
	
	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	
}
