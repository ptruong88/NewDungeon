package com.example.longdungeon.character;

import java.util.ArrayList;

import com.example.longdungeon.item.Consumable;
import com.example.longdungeon.item.Equipment;
import com.example.longdungeon.item.Spell;
import com.example.longdungeon.item.Weapon;

public class Player extends Person {

	protected ArrayList<Consumable> potions;
	// these are the magic, stamina and health potions,
	// which restore points to the cur values for those stats
	protected int score;
	protected int maxStm;
	protected int curStm;
	protected int maxMana;
	protected int curMana;
	protected ArrayList<Equipment> armsAndArmor;// this is all the extra
												// equipment
												// you own
	protected Equipment curArmor;// equipped armor
	protected Weapon curWeapon;// the weapon equipped determines physical
								// attacks
	protected Equipment curShield;
	protected Spell[] spells;// you have 3 available spell slots, each gives an
								// attack, heal or buff

	protected Equipment curRing;

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
		maxHp = 120;
		curHp = maxHp;
		maxStm = 100;
		curStm = maxStm;
		maxMana = 60;
		curMana = maxMana;
		def = 20;
		atk = 15;
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

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getCurMana() {
		return curMana;
	}

	public void setCurMana(int curMana) {
		this.curMana = curMana;
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
	 * 
	 * @param pos
	 * @return
	 */
	public Spell getSpell(int pos) {
		return spells[pos - 1];
	}

	/**
	 * Set spell based on spell position to set, start from 1 to 3.
	 * 
	 * @param spell
	 * @param pos
	 */
	public void setSpell(Spell spell, int pos) {
		this.spells[pos - 1] = spell;
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
