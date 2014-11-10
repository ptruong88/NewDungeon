package com.example.longdungeon.character;

import java.util.ArrayList;

import com.example.longdungeon.item.Item;

public class Mob extends Character {

	// the array list holds items quality based on normal mobs or boss.
	private ArrayList<Item> loots;
	
	private char type; //Normal mob or boss.

	public Mob() {
		// TODO Auto-generated constructor stub
		super();
		loots = new ArrayList<Item>();
		type = 'n'; // n for normal mob, b for boss.
	}

	public Mob(String nameNew) {
		super(nameNew);
		loots = new ArrayList<Item>();
		type = 'n';
	}
	
	public ArrayList<Item> getLoots() {
		return loots;
	}

	public void setLoots(ArrayList<Item> loots) {
		this.loots = loots;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

}
