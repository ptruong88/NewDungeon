package com.example.longdungeon.item;

public class Spell extends Equipment 
{
	private int damage;
	private int plusHp;//hp to restore
	private int plusStm;//stamina to restore
	private int cost;//magic consumed to cast spell
	
	public Spell(){}
	public Spell(String name, String desc)
	{
		super(name,desc,0,0,0);//equipping spells does not affect stats
	}
	public Spell(String name, String desc,int dmg,int plusHp,int plusStm,int cost)
	{
		super(name,desc,0,0,0);//equipping spells does not affect stats
		setDamage(dmg);
		setPlusHp(plusHp);
		setPlusStm(plusStm);
		setCost(cost);
	}
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getPlusHp() {
		return plusHp;
	}
	public void setPlusHp(int plusHp) {
		this.plusHp = plusHp;
	}
	public int getPlusStm() {
		return plusStm;
	}
	public void setPlusStm(int plusStm) {
		this.plusStm = plusStm;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
}
