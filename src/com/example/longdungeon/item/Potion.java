package com.example.longdungeon.item;

import android.os.Parcel;
import android.os.Parcelable;

public class Potion extends Item implements Parcelable {

	private int plusHP;// how many hp the potion restores
	private int plusSTM;// how much stamina the potion restores
	private int plusMGK;// how much magic the potion restores
	private int size;
	public final int size_of_stack = 5;

	public Potion() {
		size = 0;
	}

	public Potion(String name, int itemType) {
		super(name, itemType);
		size = 5;
		// this.potionType = potionType;
		switch (itemType) {
		case ITEM_HEALTH_POTION:
			plusHP = 10;
			cost = plusHP;
			statName = "HP";
			break;
		case ITEM_STAMINA_POTION:
			plusSTM = 10;
			cost = plusSTM;
			statName = "MANA";
			break;
		default:
			plusMGK = 10;
			cost = plusMGK;
			statName = "STM";
			break;
		}
	}

	public void setStatNumber(int number) {
		switch (itemType) {
		case ITEM_HEALTH_POTION:
			plusHP = number;
			cost = number;
			break;
		case ITEM_MANA_POTION:
			plusMGK = number;
			cost = number;
			break;
		default:
			plusSTM = number;
			cost = number;
			break;
		}
	}

	// Depend on what kind of potion is, getStatPotion returns
	// heal, mana, or stamina.
	public int getStatNumber() {
		switch (itemType) {
		case ITEM_HEALTH_POTION:
			return plusHP;
		case ITEM_MANA_POTION:
			return plusMGK;
		default:
			return plusSTM;
		}
	}

	public String toString() {
		return "+" + getStatNumber() + " " + getStatName() + " " + name + " x"
				+ size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	// public String getPotionType() {
	// return potionType;
	// }
	//
	// public void setPotionType(String potionType) {
	// this.potionType = potionType;
	// }

	public Potion(Parcel in) {
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
		dest.writeInt(size);
		switch (itemType) {
		case ITEM_HEALTH_POTION:
			dest.writeInt(plusHP);
			break;
		case ITEM_STAMINA_POTION:
			dest.writeInt(plusSTM);
			break;
		default:
			dest.writeInt(plusMGK);
			break;
		}
		dest.writeInt(cost);
	}

	public void readFromParcel(Parcel in) {
		name = in.readString();
		itemType = in.readInt();
		size = in.readInt();
		switch (itemType) {
		case ITEM_HEALTH_POTION:
			plusHP = in.readInt();
			break;
		case ITEM_STAMINA_POTION:
			plusSTM = in.readInt();
			break;
		default:
			plusMGK = in.readInt();
			break;
		}
		cost = in.readInt();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Potion createFromParcel(Parcel in) {
			return new Potion(in);
		}

		public Potion[] newArray(int size) {
			return new Potion[size];
		}
	};
}
