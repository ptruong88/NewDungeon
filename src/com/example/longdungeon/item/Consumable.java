package com.example.longdungeon.item;

public class Consumable 
{
private String name;
private String description;//some story info about the item
private int plusHP;//how many hp the consumable restores 
private int plusSTM;//how much stamina the consumable restores
private int plusMGK;//how much magic the consumable restores

public Consumable(){}

public Consumable(String newName, String newDesc, int newPlusHp, int newPlusStm, int newPlusMgk)
{
	name = newName;
	description = newDesc;
	plusHP = newPlusHp;
	plusSTM = newPlusStm;
	plusMGK = newPlusMgk;
}

public String getName()
{return name;}

public String getDesc()
{return description;}

public int getPlusHp()
{return plusHP;}

public int getPlusStm()
{return plusSTM;}

public int getPlusMgk()
{return plusMGK;}
}
