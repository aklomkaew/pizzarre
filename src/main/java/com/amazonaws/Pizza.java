package com.amazonaws;

import java.util.*;

public class Pizza {
	private String name;
	private HashMap<String, Integer> toppings;
	private int size;	// S = 1, M = 2, L = 3
	
	public Pizza() {
		name = "";
		size = 0;
		toppings = new HashMap<String, Integer>();
	}
	
	public Pizza(String n, int s, HashMap<String, Integer> map) {
		name = n;
		size = s;
		toppings = map;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSize(int s) {
		size = s;
	}
	
	public int getSize() {
		return size;
	}
	
	public void addTopping(String item, int quantity) {
		if(!toppings.containsKey(item)) {
			toppings.put(item,  quantity * size);
		}
		else {
			toppings.put(item, toppings.get(item) + (quantity * size));
		}
	}
	
	public void clearToppings() {
		toppings.clear();
	}
	
	public HashMap<String, Integer> getToppings(){
		return toppings;
	}
	
	public void deleteTopping(String item, int quantityToDelete) {
		if(toppings.containsKey(item)) {
			if(toppings.get(item) < quantityToDelete) {
				System.out.println("Error: Quantity to delete is greater than quantity in stock");
				// should we just make it 0?
			}
			else{
				toppings.put(item, toppings.get(item) - quantityToDelete);
			}
		}
	}
	
	public String toString() {
		String ret = name + ":";
		for(String k : toppings.keySet()) {
			ret += " " + toppings.get(k) + " " + k;
		}
		return ret;
	}
}
