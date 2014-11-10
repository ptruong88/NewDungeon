package com.example.longdungeon.character;

import java.util.ArrayList;

import com.example.longdungeon.item.Consumable;
import com.example.longdungeon.item.Equipment;
import com.example.longdungeon.item.Spell;
import com.example.longdungeon.item.Weapon;

public class Player extends Character {

	private ArrayList<Consumable> potions;
	// these are the magic, stamina and health potions,
	// which restore points to the cur values for those stats
	private int score;
	private int maxStm;
	private int curStm;
	private int maxMgk;
	private int curMgk;
	private ArrayList<Equipment> armsAndArmor;// this is all the extra equipment
												// you own
	private Equipment curArmor;// equipped armor
	private Weapon curWeapon;// the weapon equipped determines physical attacks
	private Equipment curShield;
	private Spell[] spells ;// you have 3 available spell slots, each gives an
							// attack, heal or buff
	
	private Equipment curRing;

	public Player() {
		super();
		defaultStats();
	}

	public Player(String nameNew) {
		super(nameNew);
		defaultStats();
		
	}

	private void defaultStats() {
		score = 0;
		maxStm = 100;
		curStm = 100;
		maxMgk = 60;
		curMgk = 60;
		curArmor = new Equipment("Knight's Armor",
				"The full suit of armor of a chivalrous knight", 50, 0, 10);
		curWeapon = new Weapon("Short Sword", "The basic adventuring sword", 0,
				0, 0, "Slash", 15, 10, "Thrust", 20, 15, "Helm Splitter", 60,
				50);
		curShield = new Equipment(
				"Star Shield",
				"The shining star on the front is said to bring luck to adventurers",
				15, 0, 0);
		spells = new Spell[3];
		spells[0] = new Spell("fireball",
				"a mystical ball of flame that burns one's foes", 25, 0, 0, 15);
		curRing = new Equipment("Old Copper Ring",
				"a worn old copper ring that is warm to the touch", 4, 0, 15);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	

	public int getMaxStm() {
		return maxStm;
	}

	public void setMaxStm(int maxStm) {
		this.maxStm = maxStm;
	}

	public int getCurStm() {
		return curStm;
	}

	public void setCurStm(int curStm) {
		this.curStm = curStm;
	}

	public int getMaxMgk() {
		return maxMgk;
	}

	public void setMaxMgk(int maxMgk) {
		this.maxMgk = maxMgk;
	}

	public int getCurMgk() {
		return curMgk;
	}

	public void setCurMgk(int curMgk) {
		this.curMgk = curMgk;
	}

	

	public ArrayList<Equipment> getArmsAndArmor() {
		return armsAndArmor;
	}

	public void setArmsAndArmor(ArrayList<Equipment> armsAndArmor) {
		this.armsAndArmor = armsAndArmor;
	}

	public Equipment getCurArmor() {
		return curArmor;
	}

	public void setCurArmor(Equipment curArmor) {
		this.curArmor = curArmor;
	}

	public Weapon getCurWeapon() {
		return curWeapon;
	}

	public void setCurWeapon(Weapon curWeapon) {
		this.curWeapon = curWeapon;
	}

	public Equipment getCurShield() {
		return curShield;
	}

	public void setCurShield(Equipment curShield) {
		this.curShield = curShield;
	}

	/**
	 * Get spell base on spell position, start from 1 to 3.
	 * @param pos
	 * @return
	 */
	public Spell getSpell(int pos) {
		return spells[pos-1];
	}

	/**
	 * Set spell based on spell position to set, start from 1 to 3.
	 * @param spell
	 * @param pos
	 */
	public void setSpell(Spell spell, int pos) {
		this.spells[pos-1] = spell;
	}
	
	public Equipment getCurRing() {
		return curRing;
	}

	public void setCurRing(Equipment curRing) {
		this.curRing = curRing;
	}

	public ArrayList<Consumable> getPotions() {
		return potions;
	}

	public void setPotions(ArrayList<Consumable> potions) {
		this.potions = potions;
	}
	

}
