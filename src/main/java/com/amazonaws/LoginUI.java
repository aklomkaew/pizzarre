package com.amazonaws;

import java.awt.TextField;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


@SuppressWarnings({ "restriction", "unused" })
public class LoginUI extends Application{
	
	
	private String idInput;
	private GridPane pad;
	
	public String getID(String id) {
	    System.out.println(id);
		return id;
	}
	
	
	
	public GridPane makeNumPad(GridPane numPad) {
		
		ArrayList<String> idNum = new ArrayList<String>();
		
		pad = numPad;
		numPad.setMaxSize(225, 300);
		
		Button b1 = new Button("1");
		b1.setMinSize(75, 75);
		numPad.add(b1, 0, 0);
		b1.setOnAction(e->{
			idNum.add("1");
			
		});
		
		Button b2 = new Button("2");
		b2.setMinSize(75, 75);
		numPad.add(b2, 1, 0);
		b2.setOnAction(e->{
			idNum.add("2");
		});
		
		Button b3 = new Button("3");
		b3.setMinSize(75, 75);
		numPad.add(b3, 2, 0);
		b3.setOnAction(e->{
			idNum.add("3");
		});
		
		Button b4 = new Button("4");
		b4.setMinSize(75, 75);
		numPad.add(b4, 0, 1);
		b4.setOnAction(e->{
			idNum.add("4");
		});
		
		Button b5 = new Button("5");
		b5.setMinSize(75, 75);
		numPad.add(b5, 1, 1);
		b5.setOnAction(e->{
			idNum.add("5");
		});
		
		Button b6 = new Button("6");
		b6.setMinSize(75, 75);
		numPad.add(b6, 2, 1);
		b6.setOnAction(e->{
			idNum.add("6");
		});
		
		Button b7 = new Button("7");
		b7.setMinSize(75, 75);
		numPad.add(b7, 0, 2);
		b7.setOnAction(e->{
			idNum.add("7");
		});
		
		Button b8 = new Button("8");
		b8.setMinSize(75, 75);
		numPad.add(b8, 1, 2);
		b8.setOnAction(e->{
			idNum.add("8");
		});
		
		Button b9 = new Button("9");
		b9.setMinSize(75, 75);
		numPad.add(b9, 2, 2);
		b9.setOnAction(e->{
			idNum.add("9");
		});
		
		Button ok = new Button("Confirm");
		ok.setMinSize(75, 75);
		numPad.add(ok, 0, 3);
		ok.setOnAction(e->{
			//idInput = idNum.toString();
			//System.out.println(idInput);
			if(idNum.size()!=4) {
				Alert.Display("ERROR", "This appears if the input is wrong.");
			} else {
				idInput = idNum.toString();
				getID(idInput);
				// Needs a check against DB
			}
			idNum.clear(); //Clears the array so the next attempt starts with an empty array.
		});
		
		
		Button b0 = new Button("0");
		b0.setMinSize(75, 75);
		numPad.add(b0, 1, 3);
		b0.setOnAction(e->{
			idNum.add("0");
		});
		
		Button no = new Button("Clear");
		no.setMinSize(75, 75);
		numPad.add(no, 2, 3);
		no.setOnAction(e->{
			idNum.clear();
		});
		
		
		double hSize = numPad.getWidth();
		double vSize = numPad.getHeight();
		
		return numPad;
		
	}

	
	
	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}
