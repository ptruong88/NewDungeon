package com.example.longdungeon;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class InventoryActivity extends ActionBarActivity {
private LinearLayout itemLayout, itemSubLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);

		String[] items = {"Weapon", "Helmet", "Shield", "Clothes"};
		
		Button[] btnItemArray = new Button[items.length];
		for(int i=0; i<items.length; i++){
			btnItemArray[i] = new Button(this);
			btnItemArray[i].setText(items[i]);
			btnItemArray[i].setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					itemCatClicked(v);
					
				}});
		}
		
		itemLayout = (LinearLayout) this.findViewById(R.id.itemLayout);
		if (itemLayout.getChildCount() > 0) {
			itemLayout.removeAllViews();
		}
		for (int i = 0; i < items.length; ++i) {
			itemLayout.addView(btnItemArray[i]);
		}
		
//		Button btnCong = (Button) this.findViewById(R.id.buttonCong);
//		btnCong.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intentCongrate = new Intent(InventoryActivity.this,
//						CongrateActivity.class);
//				startActivity(intentCongrate);
//			}
//		});
	}
	
	public void itemCatClicked(View v){
		String[] items = {"Sword", "Axe"};
		
		Button[] btnItemSubArray = new Button[items.length];
		for(int i=0; i<items.length; i++){
			btnItemSubArray[i] = new Button(this);
			btnItemSubArray[i].setText(items[i]);
			btnItemSubArray[i].setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					itemSubCatClicked(v);
					
				}});
		}
		
		itemSubLayout = (LinearLayout) this.findViewById(R.id.itemSubLayout);
		if (itemSubLayout.getChildCount() > 0) {
			itemSubLayout.removeAllViews();
		}
		for (int i = 0; i < items.length; ++i) {
			itemSubLayout.addView(btnItemSubArray[i]);
		}
	}
	
	public void itemSubCatClicked(View v){
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
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
