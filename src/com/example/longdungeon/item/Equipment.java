package com.example.longdungeon.item;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment extends Item implements Parcelable {

	// private int damage;
	// private int defend;
	// private int mana;

	public Equipment() {
		super();
	}

	public Equipment(String name, int itemType) {
		super(name, itemType);
		switch (itemType) {
		case ITEM_SWORD:
			statNumber = 15;
			break;
		case ITEM_RING:
			statNumber = 60;
			break;
		default:
			statNumber = 3;
			break;
		}
		cost = statNumber;
	}

//	public void setStatNumber(int statNumber) {
//		this.statNumber = statNumber;
//		cost = statNumber;
//	}

	// Depend on what item is, getStat will get damage, defend, or stamina.
	// public int getStatNumber() {
	// switch (itemType) {
	// case ITEM_SWORD:
	// return damage;
	// case ITEM_RING:
	// return mana;
	// default:
	// return defend;
	// }
	// }

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
		dest.writeInt(statNumber);
		dest.writeInt(cost);
		dest.writeString(statName);
	}

	public void readFromParcel(Parcel in) {
		name = in.readString();
		itemType = in.readInt();
		statNumber = in.readInt();
		cost = in.readInt();
		statName = in.readString();
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
