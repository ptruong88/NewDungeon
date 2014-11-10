package com.example.longdungeon.activity;

import com.example.longdungeon.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShoppingActivity extends ActionBarActivity {

	private LinearLayout lyoutItems, lyoutDes;
	private TextView txtConfirmBuy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);

		String[] items = { "All", "Weapon", "Helmet", "Shield", "Clothes" };

		Button[] btnItemsArray = new Button[5];
		for (int i = 0; i < 5; ++i) {
			btnItemsArray[i] = new Button(this);
			btnItemsArray[i].setText(items[i]);
		}

		lyoutItems = (LinearLayout) this.findViewById(R.id.layoutItems);
		if (lyoutItems.getChildCount() > 0) {
			lyoutItems.removeAllViews();
		}
		for (int i = 0; i < 5; ++i) {
			lyoutItems.addView(btnItemsArray[i]);
		}

		Button[] btnWeapon = createItems("weapon");
		Button[] btnHelmet = createItems("helmet");

		lyoutDes = (LinearLayout) this.findViewById(R.id.layoutDes);

		displayAllItems(btnItemsArray[0], lyoutDes, btnWeapon, btnHelmet, "all");
		displayAllItems(btnItemsArray[1], lyoutDes, btnWeapon, btnHelmet,
				"weapon");
		displayAllItems(btnItemsArray[2], lyoutDes, btnWeapon, btnHelmet,
				"helmet");

		txtConfirmBuy = (TextView) this.findViewById(R.id.textViewConfirmBuy);

		String child = "Children in layout " + lyoutItems.getChildCount();
		txtConfirmBuy.setText(child);

		Button btnInventory = (Button)this.findViewById(R.id.buttonInventory);
		btnInventory.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intentInventory = new Intent(ShoppingActivity.this, InventoryActivity.class);
				startActivity(intentInventory);
			}
		});
		
	}

	private void displayAllItems(Button button, final LinearLayout lyoutDes,
			final Button[] btnWeapon, final Button[] btnHelmet, final String org) {
		// TODO Auto-generated method stub

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (lyoutDes.getChildCount() > 0) {
					lyoutDes.removeAllViews();
				}
				if (org.equals("all") || org.equals("weapon")) {
					for (int i = 0; i < btnWeapon.length; ++i) {
						lyoutDes.addView(btnWeapon[i]);
					}
				}
				if (org.equals("all") || org.equals("helmet")) {
					for (int i = 0; i < btnHelmet.length; ++i) {
						lyoutDes.addView(btnHelmet[i]);
					}
				}
			}
		});

	}

	private Button[] createItems(String des) {
		// TODO Auto-generated method stub
		Button[] items = new Button[3];
		int num = 50;
		String item = "";
		if (des == "weapon")
			item = "DMG Sword";
		else if (des == "helmet")
			item = "DEF Helmet";
		else if (des == "shiled")
			item = "DEF Shield";
		else if (des == "clothes")
			item = "DEF Clothes";

		for (int i = 0; i < 3; ++i) {
			items[i] = new Button(this);
			items[i].setText("+" + num + " " + item + " (" + num + " Gold)");
			num += 50;
		}
		return items;
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
