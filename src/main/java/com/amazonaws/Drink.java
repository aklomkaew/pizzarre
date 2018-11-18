package com.amazonaws;

import java.util.*;

public class Drink {
	private String name;
	private double price;
	
	public Drink() {
		this.name = "";
		this.price = 0.0;
	}
	
	public Drink(String n, double p) {
		this.name = n;
		this.price = p;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPrice(double p) {
		this.price = p;
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
