package com.example.longdungeon.item;

public class Equipment 
{
	private String name;
	private String desc;
	private int defRating;//+ or - to defense, 
	//really light armor might lower your base 
	//defense in exchange for a big stamina boost
	private int stmRating;//+ or - to max stamina
	//really heavy armor might lower your base 
	//Stamina in exchange for a big defense boost
	private int mgkRating;//+ or - to max magic
	
	public Equipment(){};
	public Equipment(String newName,String newDesc,int newDefRating,int newStmRating,int newMgkRating)
	{
		setName(newName);
		setDesc(newDesc);
		setDefRating(newDefRating);
		setStmRating(newStmRating);
		setMgkRating(newMgkRating);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getDefRating() {
		return defRating;
	}
	public void setDefRating(int defRating) {
		this.defRating = defRating;
	}
	public int getStmRating() {
		return stmRating;
	}
	public void setStmRating(int stmRating) {
		this.stmRating = stmRating;
	}
	public int getMgkRating() {
		return mgkRating;
	}
	public void setMgkRating(int mgkRating) {
		this.mgkRating = mgkRating;
	}
	
	

}
