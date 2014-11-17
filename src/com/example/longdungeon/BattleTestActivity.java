package com.example.longdungeon;

import com.example.longdungeon.character.Mob;
import com.example.longdungeon.character.Person;
import com.example.longdungeon.character.Player;
import com.example.longdungeon.layout.BattleLayout;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class BattleTestActivity extends ActionBarActivity implements
		OnClickListener, OnItemClickListener {

	private ListView listAbility;
	private View lyoutBattle;
	private TextView txtViewMobName, txtViewMobHp, txtViewPlayerName,
			txtViewPlayerScore, txtViewPlayerHp, txtViewPlayerMana,
			txtViewStamina;
	private Person mob;
	private Player player;
	private String[] listAttack, listMagic, listItem;
	private ArrayAdapter<String> adapterAttack, adapterMagic, adapterItem;
	private static boolean mobNew;
	private int[] mobImageResource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle_test);
		
		System.out.println("Create ");
		setUpMob();
		setUpPlayer();
		setMobImageResource();
		setUpImage();
		// setUpPic();
		setUpListView();
		setUpStringForListView();
		setUpButtonAction();
		setUpHideListView();
		setUpDialogForRun();
	}

	private void setUpListView() {
		// TODO Auto-generated method stub
		listAbility = (ListView) this.findViewById(R.id.listViewAbilityOptions);
		listAbility.setOnItemClickListener(this);
	}

	private int baseDamage;// Player Attack

	private void setUpStringForListView() {
		// TODO Auto-generated method stub
		listAttack = new String[3];
		// { "Heavy Attack 10DMG/10STM",
		// "Medium Attack 15DMG/15STM", "Light Attack 20DMG/20STM" };
		baseDamage = player.getDamage();
		String attack = "Light Attack " + baseDamage + "DMG/" + baseDamage
				+ "STM";
		listAttack[0] = attack;
		attack = "Medium Attack " + (baseDamage + 5) + "DMG/"
				+ (baseDamage + 5) + "STM";
		listAttack[1] = attack;
		attack = "Heavy Attack " + (baseDamage + 10) + "DMG/"
				+ (baseDamage + 10) + "STM";
		listAttack[2] = attack;
		adapterAttack = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1,
				listAttack);

		listMagic = new String[] { "Fire Magic 10DMG/10STM",
				"Ice Magic 15DMG/15STM", "Lightning Magic 20DMG/20STM" };
		adapterMagic = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1,
				listMagic);

		listItem = new String[] { "Health Potion 10HP x5",
				"Health Potion 20HP x5", "Mana Potion 10MN x5",
				"Mana Potion 20MN x5", "Stamina Potion 10STM x5",
				"Stamina Potion 20STM x5" };
		adapterItem = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.activity_list_item, android.R.id.text1,
				listItem);
	}

	private void setUpButtonAction() {
		// TODO Auto-generated method stub
		this.findViewById(R.id.buttonAttack).setOnClickListener(this);
		this.findViewById(R.id.buttonDefend).setOnClickListener(this);
		this.findViewById(R.id.buttonMagic).setOnClickListener(this);
		this.findViewById(R.id.buttonItem).setOnClickListener(this);
		this.findViewById(R.id.buttonRun).setOnClickListener(this);
	}

	public void onClick(View button) {
		switch (button.getId()) {
		case R.id.buttonAttack:
			listAbility.setAdapter(adapterAttack);
			listAbility.setVisibility(View.VISIBLE);
			break;
		case R.id.buttonDefend:
			break;
		case R.id.buttonMagic:
			listAbility.setAdapter(adapterMagic);
			listAbility.setVisibility(View.VISIBLE);
			break;
		case R.id.buttonItem:
			listAbility.setAdapter(adapterItem);
			listAbility.setVisibility(View.VISIBLE);
			break;
		default:// button run way
			alertDialog.show();
			break;
		}

	}

	int mobMaxHp, mobCurHp;

	// Display what item is click on list view, such attack type, magic item, or
	// item type.
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(),
		// parent.getItemAtPosition(position).toString(),
		// Toast.LENGTH_SHORT).show();
		if (parent.getItemAtPosition(position).toString().contains("DMG")) {
			mobCurHp -= baseDamage;
			switch (position) {
			case 0:
				txtViewMobHp.setText("HP: " + mobCurHp + "/" + mobMaxHp);
				break;
			case 1:
				mobCurHp -= 5;
				txtViewMobHp.setText("HP: " + mobCurHp + "/" + mobMaxHp);
				break;
			default:
				mobCurHp -= 10;
				txtViewMobHp.setText("HP: " + mobCurHp + "/" + mobMaxHp);
				break;
			}
		}
		listAbility.setVisibility(View.INVISIBLE);
	}

	private void setUpPlayer() {
		// TODO Auto-generated method stub
		txtViewPlayerName = (TextView) this
				.findViewById(R.id.textViewPlayerName);
		Intent intentLogin = getIntent();
		player = new Player(intentLogin.getStringExtra("namePlayer"));
		txtViewPlayerName.setText(player.getName());

		txtViewPlayerScore = (TextView) this
				.findViewById(R.id.textViewPlayerScore);
		txtViewPlayerScore.setText("Score: " + (player).getScore());

		txtViewPlayerHp = (TextView) this.findViewById(R.id.textViewPlayerHp);
		txtViewPlayerHp.setText("HP: " + player.getCurHp() + "/"
				+ player.getMaxHp());

		txtViewPlayerMana = (TextView) this
				.findViewById(R.id.textViewPlayerMana);
		txtViewPlayerMana.setText("Mana: " + (player).getCurMana() + "/"
				+ (player).getMaxMana());

		txtViewStamina = (TextView) this.findViewById(R.id.textViewPlayerStm);
		txtViewStamina.setText("Stamina: " + (player).getCurStm() + "/"
				+ (player).getMaxStm());
	}

	private void setUpMob() {
		// TODO Auto-generated method stub
		String nameMob = "Gobin";
		mob = new Mob(nameMob);
		txtViewMobName = (TextView) this.findViewById(R.id.textViewMobName);
		txtViewMobName.setText(nameMob);
		txtViewMobHp = (TextView) this.findViewById(R.id.textViewMobHp);
		txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());
		mobMaxHp = mob.getMaxHp();
		mobCurHp = mob.getCurHp();
	}

	private void setUpHideListView() {
		// TODO Auto-generated method stub
		lyoutBattle = (View) this.findViewById(R.id.layoutBattle);
		lyoutBattle.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				listAbility.setVisibility(View.INVISIBLE);
				// listAbility.postDelayed(new Runnable() {
				// @Override
				// public void run() {
				// listAbility.setVisibility(View.GONE); // or
				// // View.INVISIBLE
				// // as Jason
				// // Leung wrote
				// }
				// }, 3000);
				return true;
			}
		});

	}

	private AlertDialog.Builder alertDialog;

	private void setUpDialogForRun() {
		// TODO Auto-generated method stub
		alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Run away...");

		// Setting Dialog Message
		alertDialog.setMessage("Are you sure you want to run away?");

		// Setting Positive "Yes" Btn
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						// Toast.makeText(getApplicationContext(),
						// "You clicked on YES",
						// Toast.LENGTH_SHORT).show();
						int level = player.getLevel();
						level = (level == mobImageResource.length - 1 ? 0
								: ++level);
						player.setLevel(level);
						Intent intentShopping = new Intent(
								BattleTestActivity.this, ShoppingActivity.class);
						intentShopping.putExtra(
								"com.example.longdungeon.character", player);
						startActivity(intentShopping);
						finish();
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
	}

	// // Change image of mob and player based on layoutPic
	// @Override
	// public void onWindowFocusChanged(boolean hasFocus) {
	// // TODO Auto-generated method stub
	// super.onWindowFocusChanged(hasFocus);
	// setUpPic();// Set up mob and player picture based on layout.
	// }

	protected void onStart() {
		super.onStart();
		System.out.println("Start");
		
	}
	
	protected void onResume(){
		super.onResume();
		System.out.println("Resume");
		setUpPic();
	}
	
	protected void onPause(){
		super.onPause();
		System.out.println("Pause");
	}

    protected void onStop(){
    	super.onStop();
    	System.out.println("Stop");
    }

    protected void onDestroy(){
    	super.onDestroy();
    	System.out.println("Destroy");
    }


	private void setMobImageResource() {
		mobImageResource = new int[] { R.drawable.goblin, R.drawable.skeleton,
				R.drawable.spider };
	}

	private ImageView imgMob;

	private void setUpPic() {

		// if (mobNew == false) {
		BattleLayout relLyoutPic = (BattleLayout) this
				.findViewById(R.id.layoutPic);
		 relLyoutPic.measure(RelativeLayout.LayoutParams.WRAP_CONTENT,
		 RelativeLayout.LayoutParams.WRAP_CONTENT);
		int lyoutX = relLyoutPic.getMeasuredWidth();
		int lyoutY = relLyoutPic.getMeasuredHeight();

		// int lyoutX = relLyoutPic.getWidth();
		// int lyoutY = relLyoutPic.getHeight();

		// int lyoutX = relLyoutPic.getWidth();
		// int lyoutY = relLyoutPic.getHeight();

		System.out.println("Layout width " + lyoutX);
		System.out.println("Layout height " + lyoutY);
		
		lyoutX = relLyoutPic.getMeasuredWidth();
		lyoutY = relLyoutPic.getMeasuredHeight();
		System.out.println("Layout width 2 " + lyoutX);
		System.out.println("Layout height 2 " + lyoutY);

		// imgMob.getLayoutParams().width = (int) (lyoutX * 0.6);
		imgMob.getLayoutParams().width = (int) (lyoutX * 0.2);// test
		imgMob.getLayoutParams().height = lyoutY;
		// RelativeLayout.LayoutParams params = new
		// RelativeLayout.LayoutParams(
		// (int)(lyoutX*0.6), lyoutY);
		// imgMob.setLayoutParams(params);

		ImageView imgPlayer = (ImageView) this.findViewById(R.id.imagePlayer);
		// params = new RelativeLayout.LayoutParams(
		// lyoutX/2, lyoutY/2);
		// imgPlayer.setLayoutParams(params);
		imgPlayer.getLayoutParams().width = (int) (lyoutX * 0.5);
		imgPlayer.getLayoutParams().height = (int) (lyoutY * 0.8);

		// }

	}

	private void setUpImage() {
		imgMob = (ImageView) this.findViewById(R.id.imageMob);
		imgMob.setImageResource(mobImageResource[player.getLevel()]);
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
