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
		size = 0;
		// this.potionType = potionType;
		switch (itemType) {
		case ITEM_HEALTH_POTION:
			plusHP = 10;
			break;
		case ITEM_STAMINA_POTION:
			plusSTM = 10;
			break;
		default:
			plusMGK = 10;
			break;
		}
	}

	public void setPlusHP(int plusHP) {
		// if (!potionType.equals("heal"))
		// plusHP = 0;
		this.plusHP = plusHP;
	}

	public void setPlusSTM(int plusSTM) {
		// if (!potionType.equals("stamina"))
		// plusSTM = 0;
		this.plusSTM = plusSTM;
	}

	public void setPlusMGK(int plusMGK) {
		// if (!potionType.equals("mana"))
		// plusMGK = 0;
		this.plusMGK = plusMGK;
	}

	// Depend on what kind of potion is, getStatPotion returns
	// heal, mana, or stamina.
	public int getStatPotion() {
		switch (itemType) {
		case ITEM_HEALTH_POTION:
			return plusHP;
		case ITEM_MANA_POTION:
			return plusMGK;
		default:
			return plusSTM;
		}
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
