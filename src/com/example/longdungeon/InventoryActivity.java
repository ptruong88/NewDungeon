package com.example.longdungeon;

import com.example.longdungeon.character.Player;

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

public class InventoryActivity extends ActionBarActivity implements
		OnClickListener, OnItemClickListener {

	private TextView txtViewPlayerName, txtViewPlayerHp, txtViewPlayerMana,
			txtViewPlayerStm, txtViewPlayerDmg, txtViewPlayerDef;
	private ListView listItems;
	private String[] listAll, listWeapon, listHelmet, listShield, listCloth,
			listPotion;
	private ArrayAdapter<String> adapter;
	private AlertDialog.Builder alertDialog;
	private Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);

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
	}

	private void getPlayerFromBundle() {
		// TODO Auto-generated method stub
		Bundle fromBattle = getIntent().getExtras();
		player = fromBattle.getParcelable("com.example.longdungeon.character");
	}

	private void setUpButtonAction() {
		// TODO Auto-generated method stub
		this.findViewById(R.id.buttonAll).setOnClickListener(this);
		this.findViewById(R.id.buttonWeapon).setOnClickListener(this);
		this.findViewById(R.id.buttonHelmet).setOnClickListener(this);
		this.findViewById(R.id.buttonShield).setOnClickListener(this);
		this.findViewById(R.id.buttonCloth).setOnClickListener(this);
		// this.findViewById(R.id.buttonRing).setOnClickListener(this);
		this.findViewById(R.id.buttonPotion).setOnClickListener(this);
		this.findViewById(R.id.buttonShop).setOnClickListener(this);
		this.findViewById(R.id.buttonBattle).setOnClickListener(this);
	}

	@Override
	public void onClick(View button) {
		// TODO Auto-generated method stub
		switch (button.getId()) {
		case R.id.buttonAll:
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.activity_list_item, android.R.id.text1,
					listAll);
			listItems.setAdapter(adapter);
			break;

		case R.id.buttonWeapon:
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.activity_list_item, android.R.id.text1,
					listWeapon);
			listItems.setAdapter(adapter);
			break;

		case R.id.buttonHelmet:
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.activity_list_item, android.R.id.text1,
					listHelmet);
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonShield:
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.activity_list_item, android.R.id.text1,
					listShield);
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonCloth:
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.activity_list_item, android.R.id.text1,
					listCloth);
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonPotion:
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.activity_list_item, android.R.id.text1,
					listPotion);
			listItems.setAdapter(adapter);
			break;
		case R.id.buttonShop:
			Intent intentShop = new Intent(InventoryActivity.this,
					ShoppingActivity.class);
			intentShop.putExtra("com.example.longdungeon.character", player);
			startActivity(intentShop);
			break;
		case R.id.buttonBattle:
			Intent intentBattle = new Intent(InventoryActivity.this,
					BattleActivity.class);
			intentBattle.putExtra("com.example.longdungeon.character", player);
			startActivity(intentBattle);
			break;
		}

	}

	private void setUpListItems() {
		// TODO Auto-generated method stub
		listItems = (ListView) this.findViewById(R.id.listViewItems);
		listItems.setVisibility(View.VISIBLE);
		listItems.setOnItemClickListener(this);

		listWeapon = new String[] { "Axe", "Sword" };
		listHelmet = new String[] { "Iron Helmet", "Gold Helmet" };
		listShield = new String[] { "Iron Shield", "Gold Shield" };
		listCloth = new String[] { "Iron Cloth", "Gold Cloth" };
		listPotion = new String[] { "Health Potion 10HP", "Health Potion 20HP",
				"Mana Potion 10Mana", "Mana Potion 20Mana",
				"Stamina Potion 10STM", "Stamina Potion 20STM" };
		int sizeAll = listWeapon.length + listHelmet.length + listShield.length
				+ listCloth.length + listPotion.length;
		listAll = new String[sizeAll];
		int j = 0;
		for (int i = 0; i < listWeapon.length; ++i, ++j)
			listAll[j] = listWeapon[i];
		for (int i = 0; i < listHelmet.length; ++i, ++j)
			listAll[j] = listHelmet[i];
		for (int i = 0; i < listShield.length; ++i, ++j)
			listAll[j] = listShield[i];
		for (int i = 0; i < listCloth.length; ++i, ++j)
			listAll[j] = listCloth[i];
		for (int i = 0; i < listPotion.length; ++i, ++j)
			listAll[j] = listPotion[i];
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
		alertDialog.setTitle("Confrim buying");

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
