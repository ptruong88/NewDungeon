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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InventoryTestActivity extends ActionBarActivity implements
		OnClickListener, OnItemClickListener {

	private TextView txtViewPlayerName, txtViewPlayerHp, txtViewPlayerMana,
			txtViewPlayerStm, txtViewPlayerDmg, txtViewPlayerDef, txtViewGold,
			txtViewScore;
	private ListView listItems;
	private String[] listAll, listWeapon, listHelmet, listShield, listCloth,
			listPotion;
	private ArrayAdapter<String> adapter;
	private AlertDialog.Builder alertDialog;
	private Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_test);

		getPlayerFromBundle();
		setUpButtonAction();
		setUpListItems();
		setUpTextView();

		alertDialog = new AlertDialog.Builder(this);
	}

	private void setUpTextView() {
		// TODO Auto-generated method stub
		txtViewPlayerName = (TextView) this
				.findViewById(R.id.textViewPlayerName);
		txtViewPlayerName.setText(player.getName());

		txtViewPlayerHp = (TextView) this.findViewById(R.id.textViewPlayerHp);
		txtViewPlayerHp.setText(player.getCurHp() + "/" + player.getMaxHp());

		txtViewPlayerMana = (TextView) this
				.findViewById(R.id.textViewPlayerMana);
		txtViewPlayerMana.setText(player.getCurMana() + "/"
				+ player.getMaxMana());

		txtViewPlayerStm = (TextView) this.findViewById(R.id.textViewPlayerStm);
		txtViewPlayerStm.setText(player.getCurStm() + "/" + player.getMaxStm());

		txtViewPlayerDmg = (TextView) this
				.findViewById(R.id.textViewPlayerDamage);
		txtViewPlayerDmg.setText(player.getDamage() + "");

		txtViewPlayerDef = (TextView) this
				.findViewById(R.id.textViewPlayerDefend);
		txtViewPlayerDef.setText(player.getDef() + "");

		txtViewGold = (TextView) this.findViewById(R.id.textViewGold);
		txtViewGold.setText("Gold: " + player.getGold());

		txtViewScore = (TextView) this.findViewById(R.id.textViewScore);
		txtViewScore.setText("Score: " + player.getScore());
	}

	private void getPlayerFromBundle() {
		// TODO Auto-generated method stub
		Bundle fromBattle = getIntent().getExtras();
		player = fromBattle.getParcelable(Player.PLAYER_DATA);
	}

	private void setUpButtonAction() {
		// TODO Auto-generated method stub
		this.findViewById(R.id.buttonAll).setOnClickListener(this);
		this.findViewById(R.id.buttonWeapon).setOnClickListener(this);
		this.findViewById(R.id.buttonHelmet).setOnClickListener(this);
		this.findViewById(R.id.buttonShield).setOnClickListener(this);
		this.findViewById(R.id.buttonCloth).setOnClickListener(this);
		this.findViewById(R.id.buttonRing).setOnClickListener(this);
		this.findViewById(R.id.buttonPotion).setOnClickListener(this);
		this.findViewById(R.id.buttonShop).setOnClickListener(this);
		this.findViewById(R.id.buttonBattle).setOnClickListener(this);
		this.findViewById(R.id.buttonSkill).setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		if (adapter.getCount() > 0)
			adapter.clear();
		switch (button.getId()) {
		case R.id.buttonAll:
			displayAll();
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonWeapon:
			displayWeapon();
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonHelmet:
			displayHelmet();
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonShield:
			displayShield();
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonCloth:
			displayCloth();
			listItems.setAdapter(adapter);
			break;

		case R.id.buttonRing:
			displayRing();
			listItems.setAdapter(adapter);
			break;

		case R.id.buttonPotion:
			displayPotion();
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonShop:
			Intent intentShop = new Intent(InventoryTestActivity.this,
					ShoppingTestActivity.class);
			intentShop.putExtra(Player.PLAYER_DATA, player);
			startActivity(intentShop);
			break;
		case R.id.buttonBattle:
			Intent intentBattle = new Intent(InventoryTestActivity.this,
					BattleTestActivity.class);
			intentBattle.putExtra(Player.PLAYER_DATA, player);
			startActivity(intentBattle);
			break;
		case R.id.buttonSkill:
			Button btn = (Button) this.findViewById(R.id.buttonSkill);
			if (btn.getText().equals("Skill")) {
				this.findViewById(R.id.scrollViewCategory).setVisibility(
						View.INVISIBLE);
				this.findViewById(R.id.listViewItems).setVisibility(
						View.INVISIBLE);
				this.findViewById(R.id.layoutSkill).setVisibility(View.VISIBLE);
				btn.setText("Inventory");
			}
			else{
				this.findViewById(R.id.scrollViewCategory).setVisibility(
						View.VISIBLE);
				this.findViewById(R.id.listViewItems).setVisibility(
						View.VISIBLE);
				this.findViewById(R.id.layoutSkill).setVisibility(View.INVISIBLE);
				btn.setText("Skill");
			}
			break;
		}

	}

	/*
	 * Display player equipment and inventory to list view if player presses All
	 * button.
	 */
	private void displayAll() {
		displayWeapon();
		displayHelmet();
		displayShield();
		displayCloth();
		displayRing();
		displayPotion();
	}

	private void displayWeapon() {
		Equipment equipments = player.getPlayerEquip()[Item.ITEM_SWORD];
		String name;

		name = "+" + equipments.getStatNumber() + " "
				+ equipments.getStatName() + " " + equipments.getName();
		adapter.add(name);

		Item[] inventory = player.getPlayerInventory();
		for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
			if (inventory[i].getItemType() == Item.ITEM_SWORD) {
				equipments = (Equipment) inventory[i];
				name = "+" + equipments.getStatNumber() + " "
						+ equipments.getStatName() + " " + equipments.getName();
				adapter.add(name);
			}
		}
	}

	private void displayHelmet() {
		Equipment equipments = player.getPlayerEquip()[Item.ITEM_HELMET];
		String name;

		name = "+" + equipments.getStatNumber() + " "
				+ equipments.getStatName() + " " + equipments.getName();
		adapter.add(name);

		Item[] inventory = player.getPlayerInventory();
		for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
			if (inventory[i].getItemType() == Item.ITEM_HELMET) {
				equipments = (Equipment) inventory[i];
				name = "+" + equipments.getStatNumber() + " "
						+ equipments.getStatName() + " " + equipments.getName();
				adapter.add(name);
			}
		}
	}

	private void displayShield() {
		Equipment equipments = player.getPlayerEquip()[Item.ITEM_SHIELD];
		String name;

		name = "+" + equipments.getStatNumber() + " "
				+ equipments.getStatName() + " " + equipments.getName();
		adapter.add(name);

		Item[] inventory = player.getPlayerInventory();
		for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
			if (inventory[i].getItemType() == Item.ITEM_SHIELD) {
				equipments = (Equipment) inventory[i];
				name = "+" + equipments.getStatNumber() + " "
						+ equipments.getStatName() + " " + equipments.getName();
				adapter.add(name);
			}
		}
	}

	private void displayCloth() {
		Equipment equipments = player.getPlayerEquip()[Item.ITEM_CLOTH];
		String name;

		name = "+" + equipments.getStatNumber() + " "
				+ equipments.getStatName() + " " + equipments.getName();
		adapter.add(name);

		Item[] inventory = player.getPlayerInventory();
		for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
			if (inventory[i].getItemType() == Item.ITEM_CLOTH) {
				equipments = (Equipment) inventory[i];
				name = "+" + equipments.getStatNumber() + " "
						+ equipments.getStatName() + " " + equipments.getName();
				adapter.add(name);
			}
		}
	}

	private void displayRing() {
		Equipment equipments = player.getPlayerEquip()[Item.ITEM_RING];
		String name;

		name = "+" + equipments.getStatNumber() + " "
				+ equipments.getStatName() + " " + equipments.getName();
		adapter.add(name);

		Item[] inventory = player.getPlayerInventory();
		for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
			if (inventory[i].getItemType() == Item.ITEM_RING) {
				equipments = (Equipment) inventory[i];
				name = "+" + equipments.getStatNumber() + " "
						+ equipments.getStatName() + " " + equipments.getName();
				adapter.add(name);
			}
		}
	}

	private void displayPotion() {
		Item[] inventory = player.getPlayerInventory();
		Potion potion;
		String name;
		for (int i = 0; i < player.getInventoryCurSpace(); ++i) {
			if (inventory[i].getItemType() == Item.ITEM_HEALTH_POTION
					|| inventory[i].getItemType() == Item.ITEM_MANA_POTION
					|| inventory[i].getItemType() == Item.ITEM_STAMINA_POTION) {
				potion = (Potion) inventory[i];
				name = "+" + potion.getStatNumber() + " "
						+ potion.getStatName() + " " + potion.getName();
				adapter.add(name);
			}
		}
	}

	private void setUpListItems() {
		// TODO Auto-generated method stub
		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1);

		listItems = (ListView) this.findViewById(R.id.listViewItems);
		listItems.setVisibility(View.VISIBLE);
		listItems.setOnItemClickListener(this);

		displayWeapon();
		listItems.setAdapter(adapter);
		
		this.findViewById(R.id.scrollViewCategory).setVisibility(
				View.VISIBLE);
		this.findViewById(R.id.listViewItems).setVisibility(
				View.VISIBLE);
		this.findViewById(R.id.layoutSkill).setVisibility(View.INVISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String value = (String) parent.getItemAtPosition(position);
		setUpConfirmBuy(value);
	}

	private void setUpConfirmBuy(final String message) {

		// Setting Dialog Title
		// alertDialog.setTitle("");

		// Setting Dialog Message
		alertDialog.setMessage(message);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change, menu);
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
