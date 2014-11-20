package com.example.longdungeon.item;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment extends Item implements Parcelable {

	private int damage;
	private int defend;
	private int mana;

	public Equipment() {
		super();
	}

	public Equipment(String name, int itemType) {
		super(name, itemType);
		switch (itemType) {
		case ITEM_SWORD:
			damage = 10;
			cost = damage;
			statName = "DMG";
			break;
		case ITEM_RING:
			mana = 10;
			cost = mana;
			statName = "MANA";
			break;
		default:
			defend = 10;
			cost = defend;
			statName = "DEF";
			break;
		}
	}

	public void setStatNumber(int number) {
		switch (itemType) {
		case ITEM_SWORD:
			damage = number;
			cost = number;
			break;
		case ITEM_RING:
			mana = number;
			cost = number;
			break;
		default:
			defend = number;
			cost = number;
			break;
		}
	}

	// Depend on what item is, getStat will get damage, defend, or stamina.
	public int getStatNumber() {
		switch (itemType) {
		case ITEM_SWORD:
			return damage;
		case ITEM_RING:
			return mana;
		default:
			return defend;
		}
	}

	public String toString() {
		return "+" + getStatNumber() + " " + getStatName() + " " + name;
	}

	public Equipment(Parcel in) {
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
		switch (itemType) {
		case ITEM_SWORD:
			dest.writeInt(damage);
			break;
		case ITEM_RING:
			dest.writeInt(mana);
			break;
		default:
			dest.writeInt(defend);
			break;
		}
		dest.writeInt(cost);
	}

	public void readFromParcel(Parcel in) {
		name = in.readString();
		itemType = in.readInt();
		switch (itemType) {
		case ITEM_SWORD:
			damage = in.readInt();
			break;
		case ITEM_RING:
			mana = in.readInt();
			break;
		default:
			defend = in.readInt();
			break;
		}
		cost = in.readInt();
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Equipment createFromParcel(Parcel in) {
			return new Equipment(in);
		}

		public Equipment[] newArray(int size) {
			return new Equipment[size];
		}
	};
}
