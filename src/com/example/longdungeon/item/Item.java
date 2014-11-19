package com.example.longdungeon.item;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{

	protected String name;
	protected String description;// some story info about the item
	protected int itemType;
	public final static int ITEM_SWORD = 0;
	public final static int ITEM_HELMET = 1;
	public final static int ITEM_SHIELD = 2;
	public final static int ITEM_CLOTH = 3;
	public final static int ITEM_RING = 4;
	public static final int ITEM_HEALTH_POTION = 5;
	public static final int ITEM_MANA_POTION = 6;
	public static final int ITEM_STAMINA_POTION = 7;

	public Item() {
		// TODO Auto-generated constructor stub
	}
	
	public Item(String name, int itemType){
		this.name = name;
		this.itemType = itemType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	public Item(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(itemType);
	}

	public void readFromParcel(Parcel in) {
		name = in.readString();
		itemType = in.readInt();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}

		public Item[] newArray(int size) {
			return new Item[size];
		}
	};

}
