package com.example.longdungeon;

import com.example.longdungeon.character.Mob;
import com.example.longdungeon.character.Player;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends ActionBarActivity {

	private ListView listAbility;
	private View lyoutBattle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle);

		TextView txtViewPlayerName = (TextView) this
				.findViewById(R.id.textViewPlayerName);
		Intent intentLogin = getIntent();
		txtViewPlayerName.setText(intentLogin.getStringExtra("namePlayer"));

		// Define list view for options
		listAbility = (ListView) this.findViewById(R.id.listViewAbilityOptions);

		setUpButtonAttack();
		setUpButtonMagic();
		setUpButtonItem();
		setUpButtonRunaway();

		Player player = new Player(txtViewPlayerName.toString());
		Mob mob = new Mob("Gobin");

		setInfoMob();

		setUpHideListView();
	}

	private void setUpHideListView() {
		// TODO Auto-generated method stub
		lyoutBattle = (View) this.findViewById(R.id.layoutBattle);
		lyoutBattle.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				listAbility.setVisibility(View.INVISIBLE);
//				listAbility.postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						listAbility.setVisibility(View.GONE); // or
//																// View.INVISIBLE
//																// as Jason
//																// Leung wrote
//					}
//				}, 3000);
				return true;
			}
		});

	}

	private void setInfoMob() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		setUpPic();// Set up mob and player picture based on layout.
	}

	private void setUpButtonRunaway() {
		// TODO Auto-generated method stub
		final AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog2.setTitle("Run away...");

		// Setting Dialog Message
		alertDialog2.setMessage("Are you sure you want to run away?");

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						// Toast.makeText(getApplicationContext(),
						// "You clicked on YES",
						// Toast.LENGTH_SHORT).show();
						Intent intentShopping = new Intent(BattleActivity.this,
								ShoppingActivity.class);
						startActivity(intentShopping);
						finish();
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

		Button btnRun = (Button) this.findViewById(R.id.buttonRun);
		btnRun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Showing Alert Dialog
				alertDialog2.show();

			}
		});

	}

	private void setUpButtonItem() {
		// TODO Auto-generated method stub
		Button btnItem = (Button) this.findViewById(R.id.buttonItem);
		btnItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Attack Options
				String[] items = new String[] { "Health Potion 10HP x5",
						"Health Potion 20HP x5", "Mana Potion 10MN x5",
						"Mana Potion 20MN x5", "Stamina Potion 10STM x5",
						"Stamina Potion 20STM x5" };
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.activity_list_item,
						android.R.id.text1, items);
				listAbility.setAdapter(adapter);
				listAbility.setVisibility(View.VISIBLE);
			}
		});
	}

	private void setUpPic() {
		// TODO Auto-generated method stub
		RelativeLayout relLyoutPic = (RelativeLayout) this
				.findViewById(R.id.layoutPic);
		int lyoutX = relLyoutPic.getWidth();
		int lyoutY = relLyoutPic.getHeight();

		System.out.println("Layout width " + lyoutX);
		System.out.println("Layout height " + lyoutY);

		ImageView imgMob = (ImageView) this.findViewById(R.id.imageMob);
		imgMob.getLayoutParams().width = (int) (lyoutX * 0.6);
		imgMob.getLayoutParams().height = lyoutY;
		// RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		// (int)(lyoutX*0.6), lyoutY);
		// imgMob.setLayoutParams(params);

		ImageView imgPlayer = (ImageView) this.findViewById(R.id.imagePlayer);
		// params = new RelativeLayout.LayoutParams(
		// lyoutX/2, lyoutY/2);
		// imgPlayer.setLayoutParams(params);
		imgPlayer.getLayoutParams().width = (int) (lyoutX * 0.5);
		imgPlayer.getLayoutParams().height = (int) (lyoutY * 0.8);
	}

	private void setUpButtonMagic() {
		// TODO Auto-generated method stub
		Button btnMagic = (Button) this.findViewById(R.id.buttonMagic);
		btnMagic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Attack Options
				String[] magics = new String[] { "Fire Magic 10DMG/10STM",
						"Ice Magic 15DMG/15STM", "Lightning Magic 20DMG/20STM" };
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.activity_list_item,
						android.R.id.text1, magics);
				listAbility.setAdapter(adapter);
				listAbility.setVisibility(View.VISIBLE);
			}
		});
	}

	private void setUpButtonAttack() {
		// TODO Auto-generated method stub
		Button btnAttack = (Button) this.findViewById(R.id.buttonAttack);
		btnAttack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Attack Options
				String[] attacks = new String[] { "Heavy Attack 10DMG/10STM",
						"Medium Attack 15DMG/15STM", "Light Attack 20DMG/20STM" };
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.activity_list_item,
						android.R.id.text1, attacks);
				listAbility.setAdapter(adapter);
				listAbility.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.battle, menu);
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
