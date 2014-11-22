package com.example.longdungeon;

import com.example.longdungeon.character.Player;
import com.example.longdungeon.item.Equipment;
import com.example.longdungeon.item.Item;
import com.example.longdungeon.item.Potion;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingTestActivity extends ActionBarActivity implements
		OnClickListener, OnItemClickListener {

	private TextView txtViewGold;

	private ArrayAdapter<String> adapter;
	private ListView listItems;
	private AlertDialog.Builder alertDialog, alertDialogCancelBuy;
	private Player player;
	private Equipment[] sellWeapon, sellHelmet, sellShield, sellCloth,
			sellRing;
	private Potion[] sellHealPotion, sellManaPotion, sellStaminaPotion;
	private String[] materialsEquip, materialsPotion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_test);

		getPlayerFromBundle();
		setUpPlayerTextView();
		setUpEquipmentToSell();
		setUpPotionToSell();
		setUpBuyingDialog();
		setUpCancelBuyingDialog();
		setUpListSell();
		setUpButton();

	}

	/**
	 * Get player information from bundle.
	 */
	private void getPlayerFromBundle() {
		// TODO Auto-generated method stub
		Bundle fromBattle = getIntent().getExtras();
		player = fromBattle.getParcelable(Player.PLAYER_DATA);
	}

	/**
	 * Set player gold to text view.
	 */
	private void setUpPlayerTextView() {
		// TODO Auto-generated method stub
		txtViewGold = (TextView) this.findViewById(R.id.textViewGold);
		txtViewGold.setText("Gold: " + player.getGold());
	}

	/**
	 * Set equipment to sell in array.
	 */
	private void setUpEquipmentToSell() {
		materialsEquip = new String[5];
		materialsEquip[0] = "Iron";
		materialsEquip[1] = "Bronze";
		materialsEquip[2] = "Silver";
		materialsEquip[3] = "Gold";
		materialsEquip[4] = "Platinum";

		double[] percent = randPercent(2.0);
		sellWeapon = setUpItemToSell(Item.ITEM_SWORD, percent);
		sellHelmet = setUpItemToSell(Item.ITEM_HELMET, percent);
		sellShield = setUpItemToSell(Item.ITEM_SHIELD, percent);
		sellCloth = setUpItemToSell(Item.ITEM_CLOTH, percent);
		sellRing = setUpItemToSell(Item.ITEM_RING, percent);
	}

	private Equipment[] setUpItemToSell(int itemType, double[] percent) {
		String des;
		int base;
		switch (itemType) {
		case Item.ITEM_SWORD:
			des = " Sword";
			base = player.getDamage();
			break;
		case Item.ITEM_HELMET:
			des = " Helmet";
			base = player.getDef();
			break;
		case Item.ITEM_SHIELD:
			des = " Shield";
			base = player.getDef();
			break;
		case Item.ITEM_CLOTH:
			des = " Cloth";
			base = player.getDef();
			break;
		default:
			des = " Ring";
			base = player.getMaxMana();
			break;
		}
		Equipment[] equip = new Equipment[5];
		for (int i = 0; i < equip.length; ++i) {
			equip[i] = new Equipment(materialsEquip[i] + des, itemType);
			equip[i].setStatNumber((int) (base * percent[i]));
			equip[i].setPosition(i);
		}
		return equip;
	}

	// End set up equipment to sell******************

	/**
	 * Set potion to sell in array.
	 */
	private void setUpPotionToSell() {
		materialsPotion = new String[5];
		materialsPotion[0] = "Small";
		materialsPotion[1] = "Medium";
		materialsPotion[2] = "Large";
		materialsPotion[3] = "Super";
		materialsPotion[4] = "Super Super";

		double[] percent = randPercent(2.0);
		sellHealPotion = setUpPotionToSell(Item.ITEM_HEALTH_POTION, percent);
		sellManaPotion = setUpPotionToSell(Item.ITEM_MANA_POTION, percent);
		sellStaminaPotion = setUpPotionToSell(Item.ITEM_STAMINA_POTION, percent);
	}

	private Potion[] setUpPotionToSell(int itemType, double[] percent) {
		String des = " Potion";
		int base;
		switch (itemType) {
		case Item.ITEM_HEALTH_POTION:
			base = player.getMaxHp();
			break;
		case Item.ITEM_MANA_POTION:
			base = player.getMaxMana();
			break;
		default:
			base = player.getMaxStm();
			break;
		}
		Potion[] potion = new Potion[5];
		for (int i = 0; i < potion.length; ++i) {
			potion[i] = new Potion(materialsPotion[i] + des, itemType);
			potion[i].setStatNumber((int) (base * percent[i]));
			potion[i].setPosition(i);
			potion[i].setCost(potion[i].getCost() * potion[i].getSize());
		}
		return potion;
	}

	// End set up potion to sell*******************

	/**
	 * Set up random percent for equipment and potion
	 */
	private double[] randPercent(double range) {
		int[] num = new int[materialsEquip.length];
		double a = 0.2;
		for (int i = 0; i < num.length; ++i, a += 0.2)
			num[i] = (int) (Math.random() * range * a * 100) + 1;

		int min;
		for (int i = 0; i < num.length; ++i) {
			min = num[i];
			for (int j = i; j < num.length; ++j) {
				if (num[j] < min) {
					min = num[j];
					num[j] = num[i];
					num[i] = min;
				}
			}
		}

		double[] rand = new double[num.length];
		for (int i = 0; i < rand.length; ++i)
			rand[i] = num[i] * 0.01;
		return rand;
	}// End set up random percent********************

	/**
	 * Set dialog for cancel buying because player doesn't have enough gold.
	 */
	private void setUpCancelBuyingDialog() {
		// Set up for buy confirm dialog.
		alertDialogCancelBuy = new AlertDialog.Builder(this);
		// Setting Dialog Title

		alertDialogCancelBuy.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						// Toast.makeText(getApplicationContext(),
						// "You clicked on YES",
						// Toast.LENGTH_SHORT).show();
						dialog.cancel();

					}
				});
	}// End cancel buy dialog*********************

	/**
	 * Set dialog for confirm buying.
	 */
	private void setUpBuyingDialog() {
		// Set up for buy confirm dialog.
		alertDialog = new AlertDialog.Builder(this);
		// Setting Dialog Title
		alertDialog.setTitle("Confirm buying...");
	}

	// A string to show what difference between old and new equipment.
	private String compareStat;

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		final String message = parent.getItemAtPosition(position).toString();
		System.out.println("Buy from shop--- " + message);
		int cost = cost(message);
		if (player.getGold() < cost) {
			alertDialogCancelBuy.setTitle("Can't make a buy...");
			alertDialogCancelBuy
					.setMessage("You don't have enough gold to buy the item");
			alertDialogCancelBuy.show();
		} else if (player.isInventoryFull()) {
			alertDialogCancelBuy.setTitle("Can't make a buy...");
			alertDialogCancelBuy
					.setMessage("You don't have enough inventory space to place a new item.");
		} else {

			// Setting Dialog Message
			alertDialog.setMessage(message + "\n(" + compareStat + ")");

			// Setting Positive "Yes" Btn
			alertDialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							// Toast.makeText(getApplicationContext(),
							// "You clicked on YES",
							// Toast.LENGTH_SHORT).show();

							String v = "You bought " + message;
							Toast.makeText(getApplicationContext(), v,
									Toast.LENGTH_SHORT).show();
							listItems.setEnabled(false);
							buyingStuff(message, position);
							new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {
									listItems.setEnabled(true);
								}

							}, Toast.LENGTH_LONG * 2200);
						}
					});
			// Setting Negative "NO" Btn
			alertDialog.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							// Toast.makeText(getApplicationContext(),
							// "You clicked on NO", Toast.LENGTH_SHORT)
							// .show();
							dialog.cancel();
						}
					});
			alertDialog.show();
		}
	}

	private int cost(String message) {
		int i, temp;
		if (message.contains("Sword")) {
			i = position(message, sellWeapon);
			temp = sellWeapon[i].getStatNumber()
					- player.getPlayerEquip(Item.ITEM_SWORD).getStatNumber();
			compareStat = (temp > 0 ? "+" : "-") + temp + " "
					+ sellWeapon[i].getStatName();
			return sellWeapon[i].getCost();
		} else if (message.contains("Helmet")) {
			i = position(message, sellHelmet);
			temp = sellHelmet[i].getStatNumber()
					- player.getPlayerEquip(Item.ITEM_HELMET).getStatNumber();
			compareStat = (temp > 0 ? "+" : "-") + temp + " "
					+ sellHelmet[i].getStatName();
			return sellHelmet[i].getCost();
		} else if (message.contains("Shield")) {
			i = position(message, sellShield);
			temp = sellShield[i].getStatNumber()
					- player.getPlayerEquip(Item.ITEM_SHIELD).getStatNumber();
			compareStat = (temp > 0 ? "+" : "-") + temp + " "
					+ sellShield[i].getStatName();
			return sellShield[i].getCost();
		} else if (message.contains("Cloth")) {
			i = position(message, sellCloth);
			temp = sellCloth[i].getStatNumber()
					- player.getPlayerEquip(Item.ITEM_CLOTH).getStatNumber();
			compareStat = (temp > 0 ? "+" : "-") + temp + " "
					+ sellCloth[i].getStatName();
			return sellCloth[i].getCost();
		} else if (message.contains("Ring")) {
			i = position(message, sellRing);
			temp = sellRing[i].getStatNumber()
					- player.getPlayerEquip(Item.ITEM_RING).getStatNumber();
			compareStat = (temp > 0 ? "+" : "-") + temp + " "
					+ sellRing[i].getStatName();
			return sellRing[i].getCost();
		} else if (message.contains("HP")) {
			return sellHealPotion[position(message, sellHealPotion)].getCost();
		} else if (message.contains("STM")) {
			return sellStaminaPotion[position(message, sellStaminaPotion)]
					.getCost();
		} else if (message.contains("MANA") && message.contains("Potion")) {
			return sellManaPotion[position(message, sellManaPotion)].getCost();
		}
		return 0;
	}

	private void buyingStuff(String message, int pos) {
		// if (adapter.getCount() > 0)
		// adapter.clear();
		if (message.contains("Sword")) {
			pos = position(message, sellWeapon);
			putUpdateSellItemToListView(pos, sellWeapon);
			updateListToSell(sellWeapon, pos);
			adapter.remove(message);
		} else if (message.contains("Helmet")) {
			pos = position(message, sellHelmet);
			putUpdateSellItemToListView(pos, sellHelmet);
			updateListToSell(sellHelmet, pos);
			adapter.remove(message);
		} else if (message.contains("Shield")) {
			pos = position(message, sellShield);
			putUpdateSellItemToListView(pos, sellShield);
			updateListToSell(sellShield, pos);
			adapter.remove(message);
		} else if (message.contains("Cloth")) {
			pos = position(message, sellCloth);
			putUpdateSellItemToListView(pos, sellCloth);
			updateListToSell(sellCloth, pos);
			adapter.remove(message);
		} else if (message.contains("Ring")) {
			pos = position(message, sellRing);
			putUpdateSellItemToListView(pos, sellRing);
			updateListToSell(sellRing, pos);
			adapter.remove(message);
		} else if (message.contains("HP")) {
			pos = position(message, sellHealPotion);
			putUpdateSellItemToListView(pos, sellHealPotion);
			updateListToSell(sellHealPotion, pos);
			adapter.remove(message);
		} else if (message.contains("STM")) {
			pos = position(message, sellStaminaPotion);
			putUpdateSellItemToListView(pos, sellStaminaPotion);
			updateListToSell(sellStaminaPotion, pos);
			adapter.remove(message);
		} else if (message.contains("MANA") && message.contains("Potion")) {
			pos = position(message, sellManaPotion);
			putUpdateSellItemToListView(pos, sellManaPotion);
			updateListToSell(sellManaPotion, pos);
			adapter.remove(message);
		}
		// listItems.setAdapter(adapter);
	}

	// ***********************************
	// Update sell equipment and display.
	private void updateListToSell(Equipment[] sellEquipment, int pos) {
		sellEquipment[pos] = null;
		for (int i = pos; i < sellEquipment.length - 1; ++i) {
			if (sellEquipment[i + 1] != null) {
				sellEquipment[i] = sellEquipment[i + 1];
				sellEquipment[i + 1] = null;
			}
		}
	}

	private void putUpdateSellItemToListView(int pos, Equipment[] sellEquipment) {
		Equipment equip = sellEquipment[pos];
		player.insertItemToInventory(equip);
		player.setGold(player.getGold() - equip.getCost());
		txtViewGold.setText("Gold: " + player.getGold());
	}

	// ***********************************
	// Update sell equipment and display.

	// ***********************************
	// Update sell potion and display.
	private void updateListToSell(Potion[] sellPotion, int pos) {
		sellPotion[pos] = null;
		for (int i = pos; i < sellPotion.length - 1; ++i) {
			if (sellPotion[i + 1] != null) {
				sellPotion[i] = sellPotion[i + 1];
				sellPotion[i + 1] = null;
			}
		}
	}

	private void putUpdateSellItemToListView(int pos, Potion[] sellPotion) {
		Potion equip = sellPotion[pos];
		player.insertItemToInventory(equip);
		player.setGold(player.getGold() - equip.getCost());
		txtViewGold.setText(player.getGold() + "");
	}

	private int position(String message, Item[] item) {
		for (int i = 0; i < item.length; ++i)
			if (item[i] != null && message.contains(item[i].getName()))
				return i;
		// if(item[i] != null)
		// System.out.println("--- "+item[i].getName() + "--- "+ i);
		return 0;
	}

	// ***********************************
	// Update sell potion and display.
	// End*****************

	/**
	 * Set list view for selling.
	 */
	private void setUpListSell() {
		listItems = (ListView) this.findViewById(R.id.listViewItems);
		listItems.setOnItemClickListener(this);

		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1);

		// Display list view in the first place.
		displayEquipToList(sellWeapon);
		listItems.setAdapter(adapter);
	}// End***************

	/**
	 * Set up button
	 */
	private void setUpButton() {
		// TODO Auto-generated method stub
		this.findViewById(R.id.buttonAll).setOnClickListener(this);
		this.findViewById(R.id.buttonWeapon).setOnClickListener(this);
		this.findViewById(R.id.buttonHelmet).setOnClickListener(this);
		this.findViewById(R.id.buttonShield).setOnClickListener(this);
		this.findViewById(R.id.buttonCloth).setOnClickListener(this);
		this.findViewById(R.id.buttonRing).setOnClickListener(this);
		this.findViewById(R.id.buttonPotion).setOnClickListener(this);
		this.findViewById(R.id.buttonInventory).setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		if (adapter.getCount() > 0)
			adapter.clear();
		switch (button.getId()) {
		case R.id.buttonAll:
			displayAll();
			break;
		case R.id.buttonWeapon:
			displayEquipToList(sellWeapon);
			break;
		case R.id.buttonHelmet:
			displayEquipToList(sellHelmet);
			break;
		case R.id.buttonShield:
			displayEquipToList(sellShield);
			break;
		case R.id.buttonCloth:
			displayEquipToList(sellCloth);
			break;
		case R.id.buttonRing:
			displayEquipToList(sellRing);
			break;
		case R.id.buttonPotion:
			displayPotionToList(sellHealPotion);
			displayPotionToList(sellManaPotion);
			displayPotionToList(sellStaminaPotion);
			break;
		default:// Inventory button
			Intent intentInventory = new Intent(ShoppingTestActivity.this,
					InventoryTestActivity.class);
			player.sortInventory();
			intentInventory.putExtra(Player.PLAYER_DATA,
					player);
			startActivity(intentInventory);
			break;
		}
		listItems.setAdapter(adapter);
	}

	private void displayAll() {
		displayEquipToList(sellWeapon);
		displayEquipToList(sellHelmet);
		displayEquipToList(sellShield);
		displayEquipToList(sellCloth);
		displayEquipToList(sellRing);
		displayPotionToList(sellHealPotion);
		displayPotionToList(sellManaPotion);
		displayPotionToList(sellStaminaPotion);
	}

	private void displayPotionToList(Potion[] sellPotion) {
		for (int i = 0; i < sellPotion.length; ++i)
			if (sellPotion[i] != null)
				adapter.add(sellPotion[i].toString() + " ("
						+ (sellPotion[i].getCost()) + " Gold)");
	}

	private void displayEquipToList(Equipment[] sellEquipment) {
		for (int i = 0; i < sellEquipment.length; ++i)
			if (sellEquipment[i] != null)
				adapter.add(sellEquipment[i].toString() + " ("
						+ sellEquipment[i].getCost() + " Gold)");
	}// End button action**************

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}