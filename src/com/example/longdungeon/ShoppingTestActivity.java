package com.example.longdungeon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.longdungeon.character.Player;
import com.example.longdungeon.item.Equipment;
import com.example.longdungeon.item.Item;
import com.example.longdungeon.item.Potion;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
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

	private ArrayAdapter<String> adapterShop, adapterInventory;
	private ListView listItems, listItemsInventory;
	private AlertDialog.Builder alertDialog, alertDialogCancelBuy,
			alertDialogSell;
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
		setUpSellingDialog();
		setUpCancelBuyingDialog();
		setUpListSellAndInventory();
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

		double[] percent = randPercent(1.0);
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

		double[] percent = randPercent(0.0);
		sellHealPotion = setUpPotionToSell(Item.ITEM_HEALTH_POTION, percent);
		sellManaPotion = setUpPotionToSell(Item.ITEM_MANA_POTION, percent);
		sellStaminaPotion = setUpPotionToSell(Item.ITEM_STAMINA_POTION, percent);
	}

	private Potion[] setUpPotionToSell(int itemType, double[] percent) {
		String des = " Potion";
		int base;
		switch (itemType) {
		case Item.ITEM_HEALTH_POTION:
			base = (int) (player.getMaxHp() * 0.4);
			break;
		case Item.ITEM_MANA_POTION:
			base = (int) (player.getMaxMana() * 0.4);
			break;
		default:
			base = (int) (player.getMaxStm() * 0.4);
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
		double a = 0.1;
		for (int i = 0; i < num.length; ++i, a += 0.1) {
			num[i] = (int) ((Math.random() + range + a) * 100);
			System.out.println("Number-- " + num[i]);
		}

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

	private void setUpSellingDialog() {
		// Set up for buy confirm dialog.
		alertDialogSell = new AlertDialog.Builder(this);
		// Setting Dialog Title
		alertDialogSell.setTitle("Confirm selling...");
	}

	// A string to show what difference between old and new equipment.
	private String compareStat;

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		switch (parent.getId()) {
		case R.id.listViewItems:
			buying(parent, position);
			break;
		default:
			selling(parent, position);
			alertDialogSell.show();
			break;
		}
	}

	private void selling(AdapterView<?> parent, int position) {
		final String item = parent.getItemAtPosition(position).toString();
		if (item.endsWith("E")) {
			alertDialogSell.setMessage("Can't sell an equipped item");
			alertDialogSell.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							dialog.cancel();
						}
					});
		} else {
			Item[] inventory = player.getPlayerInventory();
			Item temp = null;
			int cost;
			for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
				if (inventory[i].equals(item)) {
					temp = inventory[i];
					position = i;
					break;
				}
			}
			cost = (int) (temp.getCost() * 0.75);
			alertDialogSell.setMessage("Selling " + temp + " to get " + cost
					+ " Gold");
			setSellDialogButton(item, position, cost);
		}
	}

	private void setSellDialogButton(final String item, final int position,
			final int cost) {
		// TODO Auto-generated method stub
		alertDialogSell.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						adapterInventory.remove(item);
						player.removeItemFromInventory(position);
						player.setGold(player.getGold() + cost);
						txtViewGold.setText("Gold: " + player.getGold());
					}
				});
		// Setting Negative "NO" Btn
		alertDialogSell.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
	}

	private void buying(AdapterView<?> parent, final int position) {
		// TODO Auto-generated method stub

		final String message = parent.getItemAtPosition(position).toString();
		System.out.println("Buy from shop--- " + message);
		int cost = cost(message);
		if (player.isInventoryFull()) {
			alertDialogCancelBuy.setTitle("Can't make a buy...");
			alertDialogCancelBuy
					.setMessage("You don't have enough inventory space to place a new item.");
			alertDialogCancelBuy.show();
		} else if (player.getGold() < cost) {
			alertDialogCancelBuy.setTitle("Can't make a buy...");
			alertDialogCancelBuy
					.setMessage("You don't have enough gold to buy the item");
			alertDialogCancelBuy.show();
		} else {

			// Setting Dialog Message
			if (compareStat.length() != 0)
				alertDialog.setMessage(message + "\n(" + compareStat + ")");
			else
				alertDialog.setMessage(message);

			// Setting Positive "Yes" Btn
			alertDialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							// Toast.makeText(getApplicationContext(),
							// "You clicked on YES",
							// Toast.LENGTH_SHORT).show();

							// String v = "You bought " + message;
							// Toast.makeText(getApplicationContext(), v,
							// Toast.LENGTH_SHORT).show();
							// listItems.setEnabled(false);
							buyingStuff(message, position);
							// new Handler().postDelayed(new Runnable() {
							// @Override
							// public void run() {
							// listItems.setEnabled(true);
							// }
							//
							// }, Toast.LENGTH_LONG * 2200);
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
			i = position(message, sellHealPotion);
			compareStat = "";
			return sellHealPotion[i].getCost();
		} else if (message.contains("STM")) {
			i = position(message, sellStaminaPotion);
			compareStat = "";
			return sellHealPotion[i].getCost();
		} else if (message.contains("MANA") && message.contains("Potion")) {
			i = position(message, sellManaPotion);
			compareStat = "";
			return sellHealPotion[i].getCost();
		}
		return 0;
	}

	private void buyingStuff(String message, int pos) {
		// if (adapterShop.getCount() > 0)
		// adapterShop.clear();
		if (message.contains("Sword")) {
			pos = position(message, sellWeapon);
			putUpdateSellItemToListView(pos, sellWeapon);
			updateListToSell(pos, sellWeapon);
			adapterShop.remove(message);
		} else if (message.contains("Helmet")) {
			pos = position(message, sellHelmet);
			putUpdateSellItemToListView(pos, sellHelmet);
			updateListToSell(pos, sellHelmet);
			adapterShop.remove(message);

		} else if (message.contains("Shield")) {
			pos = position(message, sellShield);
			putUpdateSellItemToListView(pos, sellShield);
			updateListToSell(pos, sellShield);
			adapterShop.remove(message);

		} else if (message.contains("Cloth")) {
			pos = position(message, sellCloth);
			putUpdateSellItemToListView(pos, sellCloth);
			updateListToSell(pos, sellCloth);
			adapterShop.remove(message);

		} else if (message.contains("Ring")) {
			pos = position(message, sellRing);
			putUpdateSellItemToListView(pos, sellRing);
			updateListToSell(pos, sellRing);
			adapterShop.remove(message);

		} else if (message.contains("HP")) {
			pos = position(message, sellHealPotion);
			putUpdateSellItemToListView(pos, sellHealPotion);
			updateListToSell(pos, sellHealPotion);
			adapterShop.remove(message);

		} else if (message.contains("STM")) {
			pos = position(message, sellStaminaPotion);
			putUpdateSellItemToListView(pos, sellStaminaPotion);
			updateListToSell(pos, sellStaminaPotion);
			adapterShop.remove(message);

		} else if (message.contains("MANA") && message.contains("Potion")) {
			pos = position(message, sellManaPotion);
			putUpdateSellItemToListView(pos, sellManaPotion);
			updateListToSell(pos, sellManaPotion);
			adapterShop.remove(message);

		}
		// listItems.setAdapter(adapterShop);
	}

	// ***********************************
	// Update sell equipment and display.
	private void updateListToSell(int pos, Equipment[] sellEquipment) {
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
		adapterInventory.add(equip.toString());
	}

	// ***********************************
	// Update sell equipment and display.

	// ***********************************
	// Update sell potion and display.
	private void updateListToSell(int pos, Potion[] sellPotion) {
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
		txtViewGold.setText("Gold: " + player.getGold());
		adapterInventory.add(equip.toString());
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
	private void setUpListSellAndInventory() {
		listItems = (ListView) this.findViewById(R.id.listViewItems);
		listItems.setOnItemClickListener(this);

		adapterShop = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1);

		// Display list view in the first place.
		displayToList(sellWeapon);
		listItems.setAdapter(adapterShop);

		// Display inventory items.
		listItemsInventory = (ListView) this
				.findViewById(R.id.listViewItemsInventory);
		listItemsInventory.setOnItemClickListener(this);

		adapterInventory = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1);

		// Display weapon in the first place.
		if (!adapterInventory.isEmpty())
			adapterInventory.clear();
		adapterInventory.add(player.getPlayerEquip(Item.ITEM_SWORD).toString());
		displayFromInventory(Item.ITEM_SWORD);
		listItemsInventory.setAdapter(adapterInventory);
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
		if (!adapterShop.isEmpty()) {
			adapterShop.clear();
			adapterInventory.clear();
		}
		switch (button.getId()) {
		case R.id.buttonAll:
			displayAll();
			break;
		case R.id.buttonWeapon:
			displayToList(sellWeapon);

			adapterInventory.add(player.getPlayerEquip(Item.ITEM_SWORD)
					.toString());
			System.out.println("Equipment "
					+ player.getPlayerEquip(Item.ITEM_SWORD).isEquipped());
			displayFromInventory(Item.ITEM_SWORD);
			break;
		case R.id.buttonHelmet:
			displayToList(sellHelmet);
			adapterInventory.add(player.getPlayerEquip(Item.ITEM_HELMET)
					.toString());
			displayFromInventory(Item.ITEM_HELMET);
			break;
		case R.id.buttonShield:
			displayToList(sellShield);
			adapterInventory.add(player.getPlayerEquip(Item.ITEM_SHIELD)
					.toString());
			displayFromInventory(Item.ITEM_SHIELD);
			break;
		case R.id.buttonCloth:
			displayToList(sellCloth);
			adapterInventory.add(player.getPlayerEquip(Item.ITEM_CLOTH)
					.toString());
			displayFromInventory(Item.ITEM_CLOTH);
			break;
		case R.id.buttonRing:
			displayToList(sellRing);
			adapterInventory.add(player.getPlayerEquip(Item.ITEM_RING)
					.toString());
			displayFromInventory(Item.ITEM_RING);
			break;
		case R.id.buttonPotion:
			displayToList(sellHealPotion);
			displayToList(sellManaPotion);
			displayToList(sellStaminaPotion);

			displayFromInventory(Item.ITEM_HEALTH_POTION);
			displayFromInventory(Item.ITEM_MANA_POTION);
			displayFromInventory(Item.ITEM_STAMINA_POTION);
			break;
		default:// Inventory button
			Intent intentInventory = new Intent(ShoppingTestActivity.this,
					InventoryTestActivity.class);
			// player.sortInventory();
			intentInventory.putExtra(Player.PLAYER_DATA, player);
			startActivity(intentInventory);
			break;
		}
//		listItems.setAdapter(adapterShop);
//		listItemsInventory.setAdapter(adapterInventory);
	}

	private void displayAll() {
		displayToList(sellWeapon);
		displayToList(sellHelmet);
		displayToList(sellShield);
		displayToList(sellCloth);
		displayToList(sellRing);
		displayToList(sellHealPotion);
		displayToList(sellManaPotion);
		displayToList(sellStaminaPotion);

		adapterInventory.add(player.getPlayerEquip(Item.ITEM_SWORD).toString());
		displayFromInventory(Item.ITEM_SWORD);
		adapterInventory
				.add(player.getPlayerEquip(Item.ITEM_HELMET).toString());
		displayFromInventory(Item.ITEM_HELMET);
		adapterInventory
				.add(player.getPlayerEquip(Item.ITEM_SHIELD).toString());
		displayFromInventory(Item.ITEM_SHIELD);
		adapterInventory.add(player.getPlayerEquip(Item.ITEM_CLOTH).toString());
		displayFromInventory(Item.ITEM_CLOTH);
		adapterInventory.add(player.getPlayerEquip(Item.ITEM_RING).toString());
		displayFromInventory(Item.ITEM_RING);
		displayFromInventory(Item.ITEM_HEALTH_POTION);
		displayFromInventory(Item.ITEM_MANA_POTION);
		displayFromInventory(Item.ITEM_STAMINA_POTION);

	}

	private void displayToList(Item[] sellPotion) {
		for (int i = 0; i < sellPotion.length; ++i)
			if (sellPotion[i] != null)
				adapterShop.add(sellPotion[i].toString() + " ("
						+ (sellPotion[i].getCost()) + " Gold)");
	}

	// End button action**************

	/**
	 * Display inventory to list.
	 */

	private void displayFromInventory(int itemType) {
		Item[] inventory = player.getPlayerInventory();
		for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
			if (inventory[i].getItemType() == itemType) {
				adapterInventory.add(inventory[i].toString());
			}
		}
	}

	// End display inventory.

	protected void onStart() {
		super.onStart();
		System.out.println("onStart - shop");
	}

	protected void onRestart() {
		super.onRestart();
		System.out.println("onRestart - shop");
	}

	protected void onResume() {
		super.onResume();
		System.out.println("onResume - shop");
	}

	/**
	 * Data save when player doesn't play anymore.
	 */
//	protected void onPause() {
//		super.onPause();
//		System.out.println("onPause - shop");
//
//		File file = new File(getFilesDir(), Player.PLAYER_FILE);
//		FileOutputStream outputStream;
//
//		try {
//			if (!file.exists())
//				file.createNewFile();
//			outputStream = openFileOutput(Player.PLAYER_FILE,
//					Context.MODE_PRIVATE);
//			player.writeToFile(player, outputStream);
//			// System.out.println("Test file");
//			// BufferedReader inputReader = new BufferedReader(
//			// new InputStreamReader(
//			// openFileInput(Player.PLAYER_FILE)));
//			//
//			//
//			// System.out.println(inputReader.readLine());
//			// System.out.println(inputReader.readLine());
//			// System.out.println(inputReader.readLine());
//			// System.out.println(inputReader.readLine());
//			// System.out.println(inputReader.readLine());
//			// System.out.println(inputReader.readLine());
//			// System.out.println(inputReader.readLine());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	protected void onStop() {
		super.onStop();
		System.out.println("onStop - shop");
	}

	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy - shop");
	}

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
