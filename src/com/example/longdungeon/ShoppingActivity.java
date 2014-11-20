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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingActivity extends ActionBarActivity implements
		OnClickListener, OnItemClickListener {

	private LinearLayout lyoutCategory;
	private TextView txtViewGold;
	private String[] listWeapon, listHelmet, listShield, listCloth, listPotion,
			listRing;

	private ArrayAdapter<String> adapter;
	private ListView listItems;
	private AlertDialog.Builder alertDialog;
	private Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);

		getPlayerFromBundle();
		setUpButtonAction();
		setUpListItem();
		setUpPlayerInfo();
	}

	private void setUpPlayerInfo() {
		// TODO Auto-generated method stub
		txtViewGold = (TextView) this.findViewById(R.id.textViewGold);
		txtViewGold.setText("Gold: " + player.getGold());
	}

	private void setUpListItem() {
		// TODO Auto-generated method stub
		listItems = (ListView) this.findViewById(R.id.listViewItems);
		listItems.setOnItemClickListener(this);
		// Set up for buy confirm dialog.
		alertDialog = new AlertDialog.Builder(this);
		// Setting Dialog Title
		alertDialog.setTitle("Confrim buying...");

		listWeapon = items("weapon");
		listHelmet = items("helmet");
		listShield = items("shiled");
		listCloth = items("cloth");
		listRing = items("ring");
		listPotion = items("potion");

		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1);
		listItems.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		setUpConfirmBuy(parent.getItemAtPosition(position).toString());

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
		this.findViewById(R.id.buttonInventory).setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		if (adapter.getCount() > 0)
			adapter.clear();
		switch (button.getId()) {
		case R.id.buttonAll:
			Equipment[] playerEquip = player.getPlayerEquip();
			Item[] playerInventory = player.getPlayerInventory();
			System.out.println("Player Equipment "
					+ player.getPlayerEquip()[0].getName());
			System.out.println("Player Inventory "
					+ player.getInventoryCurSpace());
			String[] list = new String[player.getPlayerEquip().length
					+ player.getInventoryCurSpace()];
			list[0] = "+" + playerEquip[0].getStatNumber() + "DMG "
					+ playerEquip[0].getName();
			for (int i = 1; i < 4; ++i) {
				list[i] = "+" + playerEquip[i].getStatNumber() + "DEF "
						+ playerEquip[i].getName();
			}
			list[4] = "+" + playerEquip[4].getStatNumber() + "MANA "
					+ playerEquip[4].getName();
			for (int i = 5, j=0; i < list.length; ++i,++j) {
				switch (playerInventory[j].getItemType()) {
				case Item.ITEM_HEALTH_POTION:
					list[i] = "+"
							+ ((Potion) playerInventory[j]).getStatNumber()
							+ "HP " + ((Potion) playerInventory[j]).getName();
					break;
				case Item.ITEM_MANA_POTION:
					list[i] = "+"
							+ ((Potion) playerInventory[j]).getStatNumber()
							+ "MANA " + ((Potion) playerInventory[j]).getName();
					break;
				default:
					list[i] = "+"
							+ ((Potion) playerInventory[j]).getStatNumber()
							+ "STM " + ((Potion) playerInventory[j]).getName();
					break;
				}
			}

			for (int i = 0; i < list.length; ++i)
				adapter.add(list[i]);
			break;
		case R.id.buttonWeapon:
			for (int i = 0; i < listWeapon.length; ++i)
				adapter.add(listWeapon[i]);
			break;
		case R.id.buttonHelmet:
			for (int i = 0; i < listHelmet.length; ++i)
				adapter.add(listHelmet[i]);
			break;
		case R.id.buttonShield:
			for (int i = 0; i < listShield.length; ++i)
				adapter.add(listShield[i]);
			break;
		case R.id.buttonCloth:
			for (int i = 0; i < listCloth.length; ++i)
				adapter.add(listCloth[i]);
			break;
		case R.id.buttonRing:
			for (int i = 0; i < listRing.length; ++i)
				adapter.add(listRing[i]);
			break;
		case R.id.buttonPotion:
			for (int i = 0; i < listPotion.length; ++i)
				adapter.add(listPotion[i]);
			break;
		default:// Inventory button
			Intent intentInventory = new Intent(ShoppingActivity.this,
					InventoryActivity.class);
			intentInventory.putExtra("com.example.longdungeon.character",
					player);
			startActivity(intentInventory);
			break;
		}
		listItems.setAdapter(adapter);
	}

	private void getPlayerFromBundle() {
		// TODO Auto-generated method stub
		Bundle fromBattle = getIntent().getExtras();
		player = fromBattle.getParcelable("com.example.longdungeon.character");
	}

	private void setUpConfirmBuy(final String message) {
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

	private String[] items(String des) {
		int size = 3;
		int num = 50;
		String[] listItem = new String[size];
		String item = "";
		if (des == "weapon")
			item = "DMG Sword";
		else if (des == "helmet")
			item = "DEF Helmet";
		else if (des == "shiled")
			item = "DEF Shield";
		else if (des == "cloth")
			item = "DEF Cloth";
		else if (des == "ring")
			item = "DMG Ring";
		else if (des == "potion")
			item = "HP Potion";

		for (int i = 0; i < size; ++i) {
			listItem[i] = "+" + num + " " + item + " (" + num + " Gold)";
			num += 50;
		}
		return listItem;
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
