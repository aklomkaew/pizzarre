package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

public class Drink {
	private String name;
	private double price;
	private int isNew;		// 0 is false, 1 is true
	
	public Drink() {
		this.name = "";
		this.price = 0.0;
		isNew = 1;
	}
	
	public Drink(String n, double p) {
		this.name = n;
		this.price = p;
		isNew = 1;
	}
	
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setIsNew() {
		this.isNew = 0;
	}
	
	public int getIsNew() {
		return this.isNew;
	}
	
	/*public void setPrice(double p) {
		this.price = p;
	}*/
	public void setPrice() {
		this.price = 2;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void addToPrice(double p) {
		this.price += p;
	}
	
	public String toString() {
		return name;
	}
}
