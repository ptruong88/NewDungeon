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
			break;
		case ITEM_RING:
			mana = 10;
			break;
		default:
			defend = 10;
			break;
		}
	}

	public void setDamage(int damage) {
		// if (!itemType.equals("sword"))
		// damage = 0;
		this.damage = damage;
	}

	public void setDefend(int defend) {
		// if (itemType.equals("sword") || itemType.equals("ring"))
		// defend = 0;
		this.defend = defend;
	}

	public void setMana(int mana) {
		// if (!itemType.equals("ring"))
		// mana = 0;
		this.mana = mana;
	}

	// Depend on what item is, getStat will get damage, defend, or stamina.
	public int getStat() {
		switch (itemType) {
		case ITEM_SWORD:
			return damage;
		case ITEM_RING:
			return mana;
		default:
			return defend;
		}
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
