package com.amazonaws;

import java.util.*;

/**
 * Represents a pizza
 * @author Atchima
 *
 */
public class Pizza {
	private String name;
	private ArrayList<String> toppings;
	private int size;	
	private double price;
	private int isNew;		// 0 is false, 1 is true
	
	/**
	 * Class constructor
	 */
	public Pizza() {
		this.name = "";
		this.size = 0;
		this.toppings = new ArrayList<String>();
		this.price = 0.0;
		isNew = 1;
	}
	
	/**
	 * Creates a pizza with the specified name, size, and list of toppings
	 * @param n A string representing the pizza's name
	 * @param s An integer representing the pizza's size
	 * @param list An ArrayList of string representing the pizza's list of toppings
	 */
	public Pizza(String n, int s, ArrayList<String> list) {
		this.name = n;
		this.size = s;
		this.toppings = new ArrayList<String>();
		this.toppings.addAll(list);
		isNew = 1;
		this.price = list.size() + getPriceBySize(size) - 3; //cheese, crust, and sauce are technically toppings that increment price
	}
	
	/**
	 * Gets the pizza's price by its size
	 * @param s
	 * @return
	 */
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
	
	public void setIsNew() {
		this.isNew = 0;
	}
	
	public int getIsNew() {
		return this.isNew;
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
