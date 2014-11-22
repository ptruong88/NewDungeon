package com.example.longdungeon.character;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.example.longdungeon.item.Item;
import com.example.longdungeon.item.Potion;
import com.example.longdungeon.item.Equipment;

public class Player extends Person implements Parcelable {

	// these are the magic, stamina and health potions,
	// which restore points to the cur values for those stats
	private int score;
	private int maxMana;
	private int curMana;
	private int level;
	private Equipment[] playerEquip;
	private Item[] playerInventory;
	private int inventoryMaxSpace, inventoryCurSpace;
	private int curEquipment;// Track to know how many equipment player has.
	private int skillPoint;

	public static final String PLAYER_DATA = "com.example.longdungeon.character.Player";

	// public final static int POSITION_SWORD = 0;
	// public final static int POSITION_HELMET = 1;
	// public final static int POSITION_SHIELD = 2;
	// public final static int POSITION_CLOTH = 3;
	// public final static int POSITION_RING = 4;

	public Player() {
		super();
		defaultStats();
	}

	public Player(String nameNew) {
		super(nameNew);
		defaultStats();

	}

	private void defaultStats() {
		gold = 1000;
		score = 0;
		maxHp = 120;
		curHp = maxHp;
		maxStm = 100;
		curStm = maxStm;
		maxMana = 60;
		curMana = maxMana;
		def = 20;
		damage = 15;
		level = 0;
		skillPoint = 5;
		setUpPlayerEquip();
		setUpPlayerInventory();
	}

	private void setUpPlayerEquip() {
		playerEquip = new Equipment[5];
		playerEquip[Item.ITEM_SWORD] = new Equipment("Wood Sword",
				Item.ITEM_SWORD);
		damage = playerEquip[Item.ITEM_SWORD].getStatNumber();

		playerEquip[Item.ITEM_HELMET] = new Equipment("Wood Helmet",
				Item.ITEM_HELMET);
		playerEquip[Item.ITEM_SHIELD] = new Equipment("Wood Shield",
				Item.ITEM_SHIELD);
		playerEquip[Item.ITEM_CLOTH] = new Equipment("Wood Cloth",
				Item.ITEM_CLOTH);
		def = playerEquip[Item.ITEM_HELMET].getStatNumber()
				+ playerEquip[Item.ITEM_SHIELD].getStatNumber()
				+ playerEquip[Item.ITEM_CLOTH].getStatNumber();

		playerEquip[Item.ITEM_RING] = new Equipment("Wood Ring", Item.ITEM_RING);
		maxMana = playerEquip[Item.ITEM_RING].getStatNumber();

		curEquipment = 5;
	}

	private void setUpPlayerInventory() {
		inventoryCurSpace = 0;
		inventoryMaxSpace = 20;
		playerInventory = new Item[inventoryMaxSpace];

		playerInventory[0] = new Potion("Small Potion", Item.ITEM_HEALTH_POTION);
		((Potion) playerInventory[0]).setStatNumber((int) (maxHp * 0.3));
		((Potion) playerInventory[0]).setSize(5);

		playerInventory[1] = new Potion("Small Potion",
				Item.ITEM_STAMINA_POTION);
		((Potion) playerInventory[1]).setStatNumber((int) (maxStm * 0.3));
		((Potion) playerInventory[1]).setSize(5);

		playerInventory[2] = new Potion("Small Potion", Item.ITEM_MANA_POTION);
		((Potion) playerInventory[2]).setStatNumber((int) (maxMana * 0.3));
		((Potion) playerInventory[2]).setSize(5);
		inventoryCurSpace = 3;
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

	public void setSkillPoint(int skillPoint) {
		this.skillPoint = skillPoint;
	}

	public int getSkillPoint() {
		return skillPoint;
	}

	public Equipment getPlayerEquip(int position) {
		return playerEquip[position];
	}

	public Equipment[] getPlayerEquip() {
		return playerEquip;
	}

	// ************************************
	// REMOVE EQUIPMENT FROM PLAYER
	/**
	 * Remove Equipment based on itemType. For itemType use Item.ITEM_SWORD,
	 * Item.ITEM_HELMET, and so on.
	 * 
	 * @param itemType
	 */
	public void removeEquipment(int itemType) {
		// Because equipment can't be null, so change its name to XXX. That
		// means that equipment is removed.
		playerEquip[itemType].setName("XXX");
		--curEquipment;
	}

	// END REMOVE EQUIPMENT METHODS
	// ********************************

	// *******************************
	// EQUIP NEW ITEM TO PLAYER
	/**
	 * Insert Equipment based on new equipment's itemType.
	 * 
	 * @param newEquipment
	 */
	public void insertNewEquipment(Equipment newEquipment) {
		playerEquip[newEquipment.getItemType()] = newEquipment;
		++curEquipment;
	}

	// END EQUIP ITEM METHODS
	// ***********************************

	public int getCurEquipment() {
		return curEquipment;
	}

	public Item[] getPlayerInventory() {
		return playerInventory;
	}

	public void setPlayerInventory(Item[] playerInventory) {
		this.playerInventory = playerInventory;
	}

	public void insertItemToInventory(Item item) {
		playerInventory[inventoryCurSpace] = item;
		++inventoryCurSpace;
	}

	public void removeItemToInventory(int position) {
		playerInventory[position] = null;
		--inventoryCurSpace;
	}

	/**
	 * Sort inventory orderly from weapon, helmet, shield, cloth, ring, potion.
	 */
	int j = 0;

	public void sortInventory() {
		Item[] temp = new Item[inventoryMaxSpace];
		sortInventoryItem(temp, Item.ITEM_SWORD);
		sortInventoryItem(temp, Item.ITEM_HELMET);
		sortInventoryItem(temp, Item.ITEM_SHIELD);
		sortInventoryItem(temp, Item.ITEM_CLOTH);
		sortInventoryItem(temp, Item.ITEM_RING);
		sortInventoryItem(temp, Item.ITEM_HEALTH_POTION);
		sortInventoryItem(temp, Item.ITEM_MANA_POTION);
		sortInventoryItem(temp, Item.ITEM_STAMINA_POTION);
		playerInventory = temp;
	}

	private void sortInventoryItem(Item[] temp, int itemType) {
		for (int i = 0; i < inventoryCurSpace; ++i)
			if (playerInventory[i].getItemType() == itemType) {
				temp[j] = playerInventory[i];
				++j;
			}
	}

	// ***********************************
	// End Sort Inventory

	public int getInventoryMaxSpace() {
		return inventoryMaxSpace;
	}

	public void setInventoryMaxSpace(int inventoryMaxSpace) {
		this.inventoryMaxSpace = inventoryMaxSpace;
	}

	public int getInventoryCurSpace() {
		return inventoryCurSpace;
	}

	public void setInventoryCurSpace(int inventoryCurSpace) {
		this.inventoryCurSpace = inventoryCurSpace;
	}

	public boolean isInventoryFull() {
		return inventoryCurSpace == inventoryMaxSpace;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Player(Parcel in) {
		playerEquip = new Equipment[5];
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeInt(XP);
		dest.writeInt(gold);
		dest.writeInt(maxHp);
		dest.writeInt(curHp);
		dest.writeInt(def);
		dest.writeInt(damage);
		dest.writeInt(maxStm);
		dest.writeInt(curStm);
		dest.writeInt(score);
		dest.writeInt(maxMana);
		dest.writeInt(curMana);
		dest.writeInt(level);
		dest.writeInt(skillPoint);
		for (int i = 0; i < playerEquip.length; ++i) {
			dest.writeParcelable(playerEquip[i], flags);
		}
		dest.writeInt(inventoryCurSpace);
		dest.writeInt(inventoryMaxSpace);
		for (int i = 0; i < inventoryCurSpace; ++i) {
			dest.writeParcelable(playerInventory[i], flags);
		}

	}

	public void readFromParcel(Parcel in) {
		name = in.readString();
		XP = in.readInt();
		gold = in.readInt();
		maxHp = in.readInt();
		curHp = in.readInt();
		def = in.readInt();
		damage = in.readInt();
		maxStm = in.readInt();
		curStm = in.readInt();
		score = in.readInt();
		maxMana = in.readInt();
		curMana = in.readInt();
		level = in.readInt();
		skillPoint = in.readInt();
		for (int i = 0; i < 5; ++i) {
			playerEquip[i] = in
					.readParcelable(Equipment.class.getClassLoader());
		}
		inventoryCurSpace = in.readInt();
		inventoryMaxSpace = in.readInt();
		playerInventory = new Item[inventoryMaxSpace];
		for (int i = 0; i < inventoryCurSpace; ++i) {
			playerInventory[i] = in.readParcelable(Item.class.getClassLoader());
		}
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Player createFromParcel(Parcel in) {
			return new Player(in);
		}

		public Player[] newArray(int size) {
			return new Player[size];
		}
	};

}
