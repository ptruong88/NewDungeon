package com.example.longdungeon.character;

public class Character {

	private String name;
	private int XP;
	private int gold;
	private int maxHp;
	private int curHp;
	private int def;
	private int atk;

	public Character() {
		name = "Invisible";
		defaultStats();
	}

	public Character(String nameNew) {
		name = nameNew;
		defaultStats();
	}
	
	private void defaultStats() {
		// TODO Auto-generated method stub
		XP = 0;
		gold = 100;
		maxHp = 120;
		curHp = 120;
		def = 40;
		atk = 35;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getXP() {
		return XP;
	}

	public void setXP(int xP) {
		XP = xP;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getCurHp() {
		return curHp;
	}

	public void setCurHp(int curHp) {
		this.curHp = curHp;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

}
