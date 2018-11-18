package com.amazonaws;

import java.util.*;

public class Pizza {
	private String name;
	private ArrayList<String> toppings;
	private int size;	// S = 1, M = 2, L = 3
	private double price;
	
	public Pizza() {
		this.name = "";
		this.size = 0;
		this.toppings = new ArrayList<String>();
		this.price = 0.0;
	}
	
	public Pizza(String n, int s, ArrayList<String> list) {
		this.name = n;
		this.size = s;
		this.toppings = new ArrayList<String>();
		this.toppings.addAll(list);
		this.price = list.size() + getPriceBySize(size);
	}
	
	private double getPriceBySize(int s) {
		double ret = 5.0;
		switch(s) {
		case 1: 
			ret = 5.0;
			break;
		case 2: 
			ret *= 1.75;
			break;
		case 3: 
			ret *= 2.5;
			break;
		}
		return ret;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setSize(int s) {
		this.size = s;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public ArrayList<String> getToppings(){
		return this.toppings;
	}
	
	public void addTopping(String p) {
		toppings.add(p);
		addToPrice(1.0);
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
		String ret = "";
		
		for(String str : this.toppings) {
			ret += str + ", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}
}
