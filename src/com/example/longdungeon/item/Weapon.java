package com.example.longdungeon.item;

public class Weapon extends Equipment 
{
	
	private String atk1Name;
	private int atk1Damage;//hp removed by hit
	private int atk1Cost;//endurance cost
	private String atk2Name;
	private int atk2Damage;
	private int atk2Cost;//endurance cost
	private String atk3Name;
	private int atk3Damage;
	private int atk3Cost;//endurance cost
	
	public Weapon(){}
	
	public Weapon(String name, String desc, int defRating,int stmRating,int mgkRating)
	{
		super(name,desc,defRating,stmRating,mgkRating);
	}
	public Weapon(String name, String desc, int defRating,int stmRating,int mgkRating,String atk1Nm,int atk1Dmg,int atk1Cst,String atk2Nm,int atk2Dmg,int atk2Cst,String atk3Nm,int atk3Dmg,int atk3Cst)
	{
		super(name,desc,defRating,stmRating,mgkRating);
		setAtk1Name(atk1Nm);
		setAtk1Damage(atk1Dmg);
		setAtk1Cost(atk1Cst);
		setAtk2Name(atk1Nm);
		setAtk2Damage(atk1Dmg);
		setAtk2Cost(atk2Cst);
		setAtk3Name(atk1Nm);
		setAtk3Damage(atk1Dmg);
		setAtk1Cost(atk2Cst);
	}
	
	public String getAtk1Name() {
		return atk1Name;
	}

	public void setAtk1Name(String atk1Name) {
		this.atk1Name = atk1Name;
	}

	public int getAtk1Damage() {
		return atk1Damage;
	}

	public void setAtk1Damage(int atk1Damage) {
		this.atk1Damage = atk1Damage;
	}

	public String getAtk2Name() {
		return atk2Name;
	}

	public void setAtk2Name(String atk2Name) {
		this.atk2Name = atk2Name;
	}

	public int getAtk2Damage() {
		return atk2Damage;
	}

	public void setAtk2Damage(int atk2Damage) {
		this.atk2Damage = atk2Damage;
	}

	public String getAtk3Name() {
		return atk3Name;
	}

	public void setAtk3Name(String atk3Name) {
		this.atk3Name = atk3Name;
	}

	public int getAtk3Damage() {
		return atk3Damage;
	}

	public void setAtk3Damage(int atk3Damage) {
		this.atk3Damage = atk3Damage;
	}

	public int getAtk1Cost() {
		return atk1Cost;
	}

	public void setAtk1Cost(int atk1Cost) {
		this.atk1Cost = atk1Cost;
	}

	public int getAtk2Cost() {
		return atk2Cost;
	}

	public void setAtk2Cost(int atk2Cost) {
		this.atk2Cost = atk2Cost;
	}

	public int getAtk3Cost() {
		return atk3Cost;
	}

	public void setAtk3Cost(int atk3Cost) {
		this.atk3Cost = atk3Cost;
	}

	
	
}
