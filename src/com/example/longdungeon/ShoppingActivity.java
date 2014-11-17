package com.example.longdungeon;

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

public class ShoppingActivity extends ActionBarActivity {

	private LinearLayout lyoutCategory;
	private TextView txtConfirmBuy;
	private String[] listWeapon, listHelmet, listShiled, listCloth, listPotion;
	private Button btnInventory;
	ArrayAdapter<String> adapter;
	ListView listItems;
	AlertDialog.Builder alertDialog2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);

		String[] items = { "All", "Weapon", "Helmet", "Shield", "Cloth",
				"Potion" };

		int sizeBtn = 6;
		Button[] btnItemsArray = new Button[sizeBtn];
		for (int i = 0; i < sizeBtn; ++i) {
			btnItemsArray[i] = new Button(this);
			btnItemsArray[i].setText(items[i]);
		}

		lyoutCategory = (LinearLayout) this.findViewById(R.id.layoutCategory);
		if (lyoutCategory.getChildCount() > 0) {
			lyoutCategory.removeAllViews();
		}
		for (int i = 0; i < sizeBtn; ++i) {
			lyoutCategory.addView(btnItemsArray[i]);
		}

		listWeapon = items("weapon");
		listHelmet = items("helmet");
		listShiled = items("shiled");
		listCloth = items("cloth");
		listPotion = items("potion");

		// Button[] btnWeapon = createItems("weapon");
		// Button[] btnHelmet = createItems("helmet");

		listItems = (ListView) this.findViewById(R.id.listViewItems);

		displayItems(btnItemsArray[0], 0);
		displayItems(btnItemsArray[1], 1);
		displayItems(btnItemsArray[2], 2);
		displayItems(btnItemsArray[3], 3);
		displayItems(btnItemsArray[4], 4);
		displayItems(btnItemsArray[5], 5);

		setListItemsOnClick();

		setUpButtonInventory();

		alertDialog2 = new AlertDialog.Builder(this);

		// displayAllItems(btnItemsArray[0], lyoutDes, btnWeapon, btnHelmet,
		// "all");
		// displayAllItems(btnItemsArray[1], lyoutDes, btnWeapon, btnHelmet,
		// "weapon");
		// displayAllItems(btnItemsArray[2], lyoutDes, btnWeapon, btnHelmet,
		// "helmet");
		//
		// txtConfirmBuy = (TextView)
		// this.findViewById(R.id.textViewConfirmBuy);
		//
		// String child = "Children in layout " + lyoutCategory.getChildCount();
		// txtConfirmBuy.setText(child);
		//
		// Button btnInventory = (Button)
		// this.findViewById(R.id.buttonInventory);
		// btnInventory.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// Intent intentInventory = new Intent(ShoppingActivity.this,
		// InventoryActivity.class);
		// startActivity(intentInventory);
		// }
		// });

	}

	private void setUpButtonInventory() {
		// TODO Auto-generated method stub
		btnInventory = (Button) this.findViewById(R.id.buttonInventory);
		btnInventory.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Intent intentInventory = new Intent(ShoppingActivity.this,
//						InventoryActivity.class);
				Intent intentInventory = new Intent(ShoppingActivity.this,
						InventoryTestActivity.class);
				startActivity(intentInventory);
			}
		});
	}

	private void setListItemsOnClick() {
		// TODO Auto-generated method stub
		listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String value = (String) parent.getItemAtPosition(position);
				// Toast.makeText(getApplicationContext(), value,
				// Toast.LENGTH_SHORT).show();
				setUpConfirmBuy(value);

			}
		});
	}

	private void setUpConfirmBuy(final String message) {

		// Setting Dialog Title
		alertDialog2.setTitle("Confrim buying");

		// Setting Dialog Message
		alertDialog2.setMessage(message);

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("YES",
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
		alertDialog2.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						// Toast.makeText(getApplicationContext(),
						// "You clicked on NO", Toast.LENGTH_SHORT)
						// .show();
						dialog.cancel();
					}
				});
		alertDialog2.show();
	}

	private void displayItems(Button button, final int des) {
		// TODO Auto-generated method stub
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (des) {
				case 0:
					// String all = {listWeapon, listHelmet, listShiled,
					// listCloth,
					// listPotion};
					int num = listWeapon.length + listHelmet.length
							+ listShiled.length + listCloth.length
							+ listPotion.length;
					String[] all = new String[num];
					int i = 0;
					for (int j = 0; j < listWeapon.length; ++i, ++j) {
						all[i] = listWeapon[j];
					}
					for (int j = 0; j < listHelmet.length; ++i, ++j) {
						all[i] = listHelmet[j];
					}
					for (int j = 0; j < listShiled.length; ++i, ++j) {
						all[i] = listShiled[j];
					}
					for (int j = 0; j < listCloth.length; ++i, ++j) {
						all[i] = listCloth[j];
					}
					for (int j = 0; j < listPotion.length; ++i, ++j) {
						all[i] = listPotion[j];
					}

					adapter = new ArrayAdapter<String>(getApplicationContext(),
							android.R.layout.activity_list_item,
							android.R.id.text1, all);
					listItems.setAdapter(adapter);
					break;
				case 1:
					adapter = new ArrayAdapter<String>(getApplicationContext(),
							android.R.layout.activity_list_item,
							android.R.id.text1, listWeapon);
					listItems.setAdapter(adapter);
					break;
				case 2:
					adapter = new ArrayAdapter<String>(getApplicationContext(),
							android.R.layout.activity_list_item,
							android.R.id.text1, listHelmet);
					listItems.setAdapter(adapter);
					break;
				case 3:
					adapter = new ArrayAdapter<String>(getApplicationContext(),
							android.R.layout.activity_list_item,
							android.R.id.text1, listShiled);
					listItems.setAdapter(adapter);
					break;
				case 4:
					adapter = new ArrayAdapter<String>(getApplicationContext(),
							android.R.layout.activity_list_item,
							android.R.id.text1, listCloth);
					listItems.setAdapter(adapter);
					break;
				default:
					adapter = new ArrayAdapter<String>(getApplicationContext(),
							android.R.layout.activity_list_item,
							android.R.id.text1, listPotion);
					listItems.setAdapter(adapter);
					break;
				}
			}
		});

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
		else if (des == "potion")
			item = "HP Potion";

		for (int i = 0; i < size; ++i) {
			listItem[i] = "+" + num + " " + item + " (" + num + " Gold)";
			num += 50;
		}
		return listItem;
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
