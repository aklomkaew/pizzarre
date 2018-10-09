package com.amazonaws;

import java.util.*;

public class Pizza {
	private String name;
	private ArrayList<String> toppings;
	
	public Pizza() {
		name = "";
		toppings = new ArrayList<String>();
	}
	
	public Pizza(String n, ArrayList<String> list) {
		name = n;
		toppings.addAll(list);
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void addTopping(String item) {
		toppings.add(item);
	}
	
	public void addAllToppings(ArrayList<String> list) {
		toppings.addAll(list);
	}
	
	public void clearToppings() {
		toppings.clear();
	}
	
	public ArrayList<String> getToppings(){
		return toppings;
	}
}
