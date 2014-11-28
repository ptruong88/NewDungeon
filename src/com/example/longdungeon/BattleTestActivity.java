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
import android.widget.ImageButton;
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
			txtViewStamina, txtViewMobStamina;
	private Mob mob;
	private Player player;
	private String[] listAttack, listMagic, listItem;
	private ArrayAdapter<String> adapterAttack, adapterMagic, adapterItem;
	private static int[] imageMobs = new int[] { R.drawable.goblin,
			R.drawable.skeleton, R.drawable.spider };
	private ImageView imageMob;

	// gordon's variables for the game loop
	// boolean playerTurn = true;
	boolean playerDefending = false;
	boolean enemyDefending = false;
	boolean ranAway = false;
	int d10Roll = 0;
	int atkVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle_test);

		
		setUpPlayer();
		setUpMob();
		setUpListView();
		setUpStringForListView();
		setUpButtonAction();
		setUpHideListView();
		setUpDialogForRun();
		setUpWinDialog();
		setLoseDialog();
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

		listMagic = new String[] { "Fire Magic 10 DMG/10 MANA",
				"Ice Magic 15 DMG/15 MANA", "Lightning Magic 20 DMG/20 MANA" };
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
		case R.id.buttonDefend: {
			playerDefending = true;
			if (player.getCurStm() < player.getMaxStm()) {
				int stmRegen = (player.getCurStm() / 5);
				player.setCurStm(stmRegen + player.getCurStm());// get back 1/5
																// of your
																// stamina
				txtViewStamina.setText("Stamina: " + player.getCurStm() + "/"
						+ player.getMaxStm());

			}
			enemyTurn();// defending uses your turn
		}
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
		if (parent.getItemAtPosition(position).toString().contains("STM")) {

			// mobCurHp -= baseDamage;
			switch (position) {
			case 0:// basic attack case based on it being in the 0th position
				if (player.getCurStm() >= 10) {
					player.setCurStm((player.getCurStm()) - 10);
					txtViewStamina.setText("Stamina: " + player.getCurStm()
							+ "/" + player.getMaxStm());// attacks always cost
														// stamina, if they miss
														// or not

					d10Roll = randomWithRange(1, 10);
					if ((d10Roll == 1) || (d10Roll == 2)) {
						Toast.makeText(getApplicationContext(),
								"Your attack missed!", Toast.LENGTH_SHORT)
								.show();
					}

					else if ((d10Roll == 2) || (d10Roll == 3) || (d10Roll == 4)) {
						atkVal = (player.getDamage()) / 2;
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Glancing hit for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());

					}

					else if ((d10Roll == 5) || (d10Roll == 6) || (d10Roll == 7)
							|| (d10Roll == 8)) {
						atkVal = (player.getDamage());
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if ((d10Roll == 9) || (d10Roll == 10)) {
						atkVal = (player.getDamage());
						atkVal = atkVal * 2;// critical attack doubles damage
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Critical attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}
					atkVal = 0;// clear attack val;
					if (mob.getCurHp() <= 0) {
						player.setCurHp(player.getMaxHp());
						player.setCurMana(player.getMaxMana());
						player.setCurStm(player.getMaxStm());
						winDialog.show();
					}
					enemyTurn();// once you've attacked the enemy gets a turn
				}
				break;
			case 1:// medium attack case based on it being in the 1st position
				if (player.getCurStm() >= 20) {
					player.setCurStm((player.getCurStm()) - 20);
					txtViewStamina.setText("Stamina: " + player.getCurStm()
							+ "/" + player.getMaxStm());// attacks always cost
														// stamina, if they miss
														// or not

					d10Roll = randomWithRange(1, 10);

					if ((d10Roll == 1) || (d10Roll == 2)) {
						Toast.makeText(getApplicationContext(),
								"Your attack missed!", Toast.LENGTH_SHORT)
								.show();
					}

					else if ((d10Roll == 2) || (d10Roll == 3) || (d10Roll == 4)) {
						atkVal = (player.getDamage() * 4) / 3;// medium attack
																// value is 4/3
																// of base
																// attack
						atkVal = atkVal / 2;// damaged halved for glancing blow
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Glancing hit for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if ((d10Roll == 5) || (d10Roll == 6) || (d10Roll == 7)
							|| (d10Roll == 8)) {
						atkVal = (player.getDamage() * 4) / 3;// medium attack
																// value is 4/3
																// of base
																// attack
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if ((d10Roll == 9) || (d10Roll == 10)) {
						atkVal = (player.getDamage() * 4) / 3;// medium attack
																// value is 4/3
																// of base
																// attack
						atkVal = atkVal * 2;// atk doubled for critical hit
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Critical attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}
					atkVal = 0;
					if (mob.getCurHp() <= 0) {
						player.setCurHp(player.getMaxHp());
						player.setCurMana(player.getMaxMana());
						player.setCurStm(player.getMaxStm());
						winDialog.show();
					}
					enemyTurn();// once you've attacked the enemy gets a turn
				}
				break;
			default:// heavy attack case based on it being in the 2nd position
				if (player.getCurStm() >= 30) {
					player.setCurStm((player.getCurStm()) - 30);
					txtViewStamina.setText("Stamina: " + player.getCurStm()
							+ "/" + player.getMaxStm());// attacks always cost
														// stamina, if they miss
														// or not

					d10Roll = randomWithRange(1, 10);
					if ((d10Roll == 1) || (d10Roll == 2)) {
						Toast.makeText(getApplicationContext(),
								"Your attack missed!", Toast.LENGTH_SHORT)
								.show();
					}

					else if ((d10Roll == 2) || (d10Roll == 3) || (d10Roll == 4)) {
						atkVal = (player.getDamage() * 2);// Heavy attack value
															// is 2 times the
															// base attack
						atkVal = atkVal / 2;// damaged halved for glancing blow
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Glancing hit for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if ((d10Roll == 5) || (d10Roll == 6) || (d10Roll == 7)
							|| (d10Roll == 8)) {
						atkVal = (player.getDamage() * 2);// Heavy attack value
															// is 2 times the
															// base attack
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if ((d10Roll == 9) || (d10Roll == 10)) {
						atkVal = (player.getDamage() * 2);// Heavy attack value
															// is 2 times the
															// base attack
						atkVal = atkVal * 2;// atk doubled for critical hit
						if (enemyDefending == true) {
							atkVal = atkVal / 2;
						}
						String atkString = "Critical attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}
					atkVal = 0;
					if (mob.getCurHp() <= 0) {
						player.setCurHp(player.getMaxHp());
						player.setCurMana(player.getMaxMana());
						player.setCurStm(player.getMaxStm());
						winDialog.show();
					}
					enemyTurn();// once you've attacked the enemy gets a turn

				}
				break;
			}
		} else if (parent.getItemAtPosition(position).toString()
				.contains("MANA")) {
			switch (position) {
			case 0:// Fireball
				if (player.getCurMana() >= 10) {
					player.setCurMana((player.getCurMana()) - 10);
					txtViewPlayerMana.setText("Mana: " + player.getCurMana()
							+ "/" + player.getMaxMana());
					;// attacks always cost stamina, if they miss or not

					int d3Roll = randomWithRange(1, 3);
					if (d3Roll == 1) {
						Toast.makeText(getApplicationContext(),
								"Your spell missed!", Toast.LENGTH_SHORT)
								.show();
					}

					else if (d3Roll == 2) {
						atkVal = (player.getDamage());
						// spells ignore defense
						String atkString = "Spell hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if (d3Roll == 3) {
						atkVal = (player.getDamage());
						atkVal = atkVal * 2;// critical attack doubles damage
						// spells ignore defense
						String atkString = "Critical attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}
					atkVal = 0;// clear attack val;
					if (mob.getCurHp() <= 0) {
						player.setCurHp(player.getMaxHp());
						player.setCurMana(player.getMaxMana());
						player.setCurStm(player.getMaxStm());
						winDialog.show();
					}
					enemyTurn();// once you've attacked the enemy gets a turn
				} else {
					Toast.makeText(getApplicationContext(), "not enough Mana!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 1:// Ice spell
				if (player.getCurMana() >= 20) {
					player.setCurMana((player.getCurMana()) - 20);
					txtViewPlayerMana.setText("Mana: " + player.getCurMana()
							+ "/" + player.getMaxMana());
					;// attacks always cost stamina, if they miss or not

					int d3Roll = randomWithRange(1, 3);
					if (d3Roll == 1) {
						Toast.makeText(getApplicationContext(),
								"Your spell missed!", Toast.LENGTH_SHORT)
								.show();
					}

					else if (d3Roll == 2) {
						atkVal = ((player.getDamage() * 4) / 3);
						// spells ignore defense
						String atkString = "Spell hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if (d3Roll == 3) {
						atkVal = ((player.getDamage() * 4) / 3);
						atkVal = atkVal * 2;// critical attack doubles damage
						// spells ignore defense
						String atkString = "Critical attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}
					atkVal = 0;// clear attack val;
					if (mob.getCurHp() <= 0) {
						player.setCurHp(player.getMaxHp());
						player.setCurMana(player.getMaxMana());
						player.setCurStm(player.getMaxStm());
						winDialog.show();
					}
					enemyTurn();// once you've attacked the enemy gets a turn
				} else {
					Toast.makeText(getApplicationContext(), "not enough Mana!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:// lightning storm
				if (player.getCurMana() >= 30) {
					player.setCurMana((player.getCurMana()) - 30);
					txtViewPlayerMana.setText("Mana: " + player.getCurMana()
							+ "/" + player.getMaxMana());
					;// attacks always cost stamina, if they miss or not

					int d3Roll = randomWithRange(1, 3);
					if (d3Roll == 1) {
						Toast.makeText(getApplicationContext(),
								"Your spell missed!", Toast.LENGTH_SHORT)
								.show();
					}

					else if (d3Roll == 2) {
						atkVal = ((player.getDamage() * 2));
						// spells ignore defense
						String atkString = "Spell hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}

					else if (d3Roll == 3) {
						atkVal = ((player.getDamage() * 2));
						atkVal = atkVal * 2;// critical attack doubles damage
						// spells ignore defense
						String atkString = "Critical attack hits for " + atkVal
								+ " damage!";
						Toast.makeText(getApplicationContext(), atkString,
								Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp() - atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/"
								+ mob.getMaxHp());
					}
					atkVal = 0;// clear attack val;
					if (mob.getCurHp() <= 0) {
						player.setCurHp(player.getMaxHp());
						player.setCurMana(player.getMaxMana());
						player.setCurStm(player.getMaxStm());
						winDialog.show();
					}
					enemyTurn();// once you've attacked the enemy gets a turn
				} else {
					Toast.makeText(getApplicationContext(), "not enough Mana!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}//
	}

	private void setUpPlayer() {
		// TODO Auto-generated method stub
		txtViewPlayerName = (TextView) this
				.findViewById(R.id.textViewPlayerName);
		Intent intentLogin = getIntent();
		player = intentLogin.getExtras().getParcelable(Player.PLAYER_DATA);
		txtViewPlayerName.setText(player.getName());

		txtViewPlayerScore = (TextView) this
				.findViewById(R.id.textViewPlayerScore);
		txtViewPlayerScore.setText("Score: " + player.getScore());

		txtViewPlayerHp = (TextView) this.findViewById(R.id.textViewPlayerHp);
		txtViewPlayerHp.setText("HP: " + player.getCurHp() + "/"
				+ player.getMaxHp());

		txtViewPlayerMana = (TextView) this
				.findViewById(R.id.textViewPlayerMana);
		txtViewPlayerMana.setText("Mana: " + player.getCurMana() + "/"
				+ player.getMaxMana());

		txtViewStamina = (TextView) this.findViewById(R.id.textViewPlayerStm);
		txtViewStamina.setText("Stamina: " + player.getCurStm() + "/"
				+ player.getMaxStm());
	}

	private void setUpMob() {
		// TODO Auto-generated method stub
		String nameMob = "Goblin";
		mob = new Mob(nameMob);
		txtViewMobName = (TextView) this.findViewById(R.id.textViewMobName);
		txtViewMobName.setText(nameMob);
		txtViewMobHp = (TextView) this.findViewById(R.id.textViewMobHp);
		txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());

		txtViewMobStamina = (TextView) this
				.findViewById(R.id.textViewMobStamina);
		txtViewMobStamina.setText("Stamina: " + mob.getCurStm() + "/"
				+ mob.getMaxStm());

		mobMaxHp = mob.getMaxHp();
		mobCurHp = mob.getCurHp();
		
		imageMob = (ImageView)this.findViewById(R.id.imageMob);
		imageMob.setImageResource(imageMobs[player.getLevel()]);
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

	// @Override
	// public void onWindowFocusChanged(boolean hasFocus) {
	// // TODO Auto-generated method stub
	// super.onWindowFocusChanged(hasFocus);
	// setUpPic();// Set up mob and player picture based on layout.
	// }
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
						Intent intentShopping = new Intent(
								BattleTestActivity.this,
								ShoppingTestActivity.class);
						player.setLevel(player.getLevel() == 2 ? 0 : player
								.getLevel() + 1);
						intentShopping.putExtra(Player.PLAYER_DATA, player);
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

	private AlertDialog.Builder winDialog;

	private void setUpWinDialog() {
		// TODO Auto-generated method stub
		winDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		winDialog.setTitle("YOU WIN!");

		ImageView imageV = new ImageView(this);
		imageV.setImageResource(R.drawable.victory);
		winDialog.setView(imageV);

		// Setting Positive "Yes" Btn
		winDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						// Toast.makeText(getApplicationContext(),
						// "You clicked on YES",
						// Toast.LENGTH_SHORT).show();
						player.setLevel(1);
						Intent intentShopping = new Intent(
								BattleTestActivity.this,
								ShoppingTestActivity.class);
						intentShopping.putExtra(Player.PLAYER_DATA, player);
						startActivity(intentShopping);
						finish();
					}
				});
	}

	private AlertDialog.Builder loseDialog;

	private void setLoseDialog() {
		// TODO Auto-generated method stub
		loseDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		loseDialog.setTitle("YOU LOSE!");

		ImageView imageL = new ImageView(this);
		imageL.setImageResource(R.drawable.defeat);
		loseDialog.setView(imageL);

		// Setting Positive "Yes" Btn
		loseDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						// Toast.makeText(getApplicationContext(),
						// "You clicked on YES",
						// Toast.LENGTH_SHORT).show();
						Intent intentLogin = new Intent(
								BattleTestActivity.this,
								LoginTestActivity.class);
						startActivity(intentLogin);
						finish();
					}
				});
	}

	private void setUpPic() {
		// TODO Auto-generated method stub
		BattleLayout relLyoutPic = (BattleLayout) this
				.findViewById(R.id.layoutPic);
		int lyoutX = relLyoutPic.getMeasuredWidth();
		int lyoutY = relLyoutPic.getMeasuredHeight();

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

	public void enemyTurn() {
		{

			if ((mob.getCurStm() <= 0)) {
				Toast.makeText(getApplicationContext(),
						"The goblin wheezes and stops to catch it's breath",
						Toast.LENGTH_SHORT).show();
				mob.setCurStm(mob.getMaxStm());// enemy regains all stamina but
												// is open for a free hit
				txtViewMobStamina.setText("Stamina: " + mob.getCurStm() + "/"
						+ mob.getMaxStm());
				if (player.getCurHp() <= 0) {
					loseDialog.show();
				}
				playerDefending = false;// player's defense lasts only 1 turn

			} else {
				Toast.makeText(getApplicationContext(), "The goblin attacks!",
						Toast.LENGTH_SHORT).show();
				int enemyAtk = randomWithRange(1, 3);
				if (enemyAtk == 1) {
					atkVal = mob.getDamage();
					if ((mob.getCurStm() - 10) >= 0) {
						mob.setCurStm(mob.getCurStm() - 10);// costs 10 stamina
						txtViewMobStamina.setText("Stamina: " + mob.getCurStm()
								+ "/" + mob.getMaxStm());
					} else if ((mob.getCurStm() - 10) < 0) {
						mob.setCurStm(0);// can't have negative stamina
						txtViewMobStamina.setText("Stamina: " + mob.getCurStm()
								+ "/" + mob.getMaxStm());
					}
					d10Roll = randomWithRange(1, 10);
					if ((d10Roll == 1) || (d10Roll == 2)) {
						Toast.makeText(getApplicationContext(),
								"The goblin's attack missed!",
								Toast.LENGTH_SHORT).show();
					}

					else if ((d10Roll == 2) || (d10Roll == 3) || (d10Roll == 4)) {
						atkVal = (atkVal / 2);

						if (playerDefending) {
							atkVal = atkVal / 2;
						}
						Toast.makeText(
								getApplicationContext(),
								"The goblin lands a glancing blow for" + atkVal
										+ "damage!", Toast.LENGTH_SHORT).show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					} else if ((d10Roll == 5) || (d10Roll == 6)
							|| (d10Roll == 7) || (d10Roll == 8)) {
						// no modification to base damage
						if (playerDefending) {
							atkVal = atkVal / 2;
						}

						Toast.makeText(
								getApplicationContext(),
								"The goblin's attack hits for" + atkVal
										+ "damage!", Toast.LENGTH_SHORT).show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					} else if ((d10Roll == 9) || (d10Roll == 10)) {
						atkVal = (((atkVal) * 3) / 2);

						if (playerDefending) {
							atkVal = atkVal / 2;
						}

						Toast.makeText(
								getApplicationContext(),
								"The goblin lands a critical hit for " + atkVal
										+ " damage!", Toast.LENGTH_SHORT)
								.show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					}
					atkVal = 0;
					d10Roll = 0;
					playerDefending = false;
					if (player.getCurHp() <= 0) {
						loseDialog.show();
					}

				} else if (enemyAtk == 2) {
					atkVal = (mob.getDamage() * 4) / 3;
					if ((mob.getCurStm() - 20) >= 0) {
						mob.setCurStm(mob.getCurStm() - 20);// costs 20 stamina
						txtViewMobStamina.setText("Stamina: " + mob.getCurStm()
								+ "/" + mob.getMaxStm());
					} else if ((mob.getCurStm() - 20) < 0) {
						mob.setCurStm(0);// can't have negative stamina
						txtViewMobStamina.setText("Stamina: " + mob.getCurStm()
								+ "/" + mob.getMaxStm());
					}
					d10Roll = randomWithRange(1, 10);
					if ((d10Roll == 1) || (d10Roll == 2)) {
						Toast.makeText(getApplicationContext(),
								"The goblin's attack missed!",
								Toast.LENGTH_SHORT).show();
					}

					else if ((d10Roll == 2) || (d10Roll == 3) || (d10Roll == 4)) {
						atkVal = (atkVal / 2);

						if (playerDefending) {
							atkVal = atkVal / 2;
						}
						Toast.makeText(
								getApplicationContext(),
								"The goblin lands a glancing blow for" + atkVal
										+ "damage!", Toast.LENGTH_SHORT).show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					} else if ((d10Roll == 5) || (d10Roll == 6)
							|| (d10Roll == 7) || (d10Roll == 8)) {
						// no modification to base damage
						if (playerDefending) {
							atkVal = atkVal / 2;
						}

						Toast.makeText(
								getApplicationContext(),
								"The goblin's attack hits for" + atkVal
										+ "damage!", Toast.LENGTH_SHORT).show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					} else if ((d10Roll == 9) || (d10Roll == 10)) {
						atkVal = (((atkVal) * 3) / 2);

						if (playerDefending) {
							atkVal = atkVal / 2;
						}

						Toast.makeText(
								getApplicationContext(),
								"The goblin lands a critical hit for " + atkVal
										+ " damage!", Toast.LENGTH_SHORT)
								.show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					}
					atkVal = 0;
					d10Roll = 0;
					playerDefending = false;
					if (player.getCurHp() <= 0) {
						loseDialog.show();
					}
				} else if (enemyAtk == 3) {
					atkVal = mob.getDamage() * 2;// heavy attack is twice the
													// base value
					if ((mob.getCurStm() - 30) >= 0) {
						mob.setCurStm(mob.getCurStm() - 30);// costs 30 stamina
						txtViewMobStamina.setText("Stamina: " + mob.getCurStm()
								+ "/" + mob.getMaxStm());
					} else if ((mob.getCurStm() - 30) < 0) {
						mob.setCurStm(0);// can't have negative stamina
						txtViewMobStamina.setText("Stamina: " + mob.getCurStm()
								+ "/" + mob.getMaxStm());
					}
					d10Roll = randomWithRange(1, 10);
					if ((d10Roll == 1) || (d10Roll == 2)) {
						Toast.makeText(getApplicationContext(),
								"The goblin's attack missed!",
								Toast.LENGTH_SHORT).show();
					}

					else if ((d10Roll == 2) || (d10Roll == 3) || (d10Roll == 4)) {
						atkVal = (atkVal / 2);

						if (playerDefending) {
							atkVal = atkVal / 2;
						}
						Toast.makeText(
								getApplicationContext(),
								"The goblin lands a glancing blow for" + atkVal
										+ "damage!", Toast.LENGTH_SHORT).show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					} else if ((d10Roll == 5) || (d10Roll == 6)
							|| (d10Roll == 7) || (d10Roll == 8)) {
						// no modification to base damage
						if (playerDefending) {
							atkVal = atkVal / 2;
						}

						Toast.makeText(
								getApplicationContext(),
								"The goblin's attack hits for" + atkVal
										+ "damage!", Toast.LENGTH_SHORT).show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					} else if ((d10Roll == 9) || (d10Roll == 10)) {
						atkVal = (((atkVal) * 3) / 2);

						if (playerDefending) {
							atkVal = atkVal / 2;
						}

						Toast.makeText(
								getApplicationContext(),
								"The goblin lands a critical hit for " + atkVal
										+ " damage!", Toast.LENGTH_SHORT)
								.show();
						player.setCurHp(player.getCurHp() - atkVal);
						txtViewPlayerHp.setText("HP: " + player.getCurHp()
								+ "/" + player.getMaxHp());
					}
					atkVal = 0;
					d10Roll = 0;
					playerDefending = false;
					if (player.getCurHp() <= 0) {
						loseDialog.show();
					}

				}

			}
		}
		if (player.getCurHp() == 0) {
			Toast.makeText(getApplicationContext(), "You died!",
					Toast.LENGTH_SHORT).show();
		}

	}

	public int randomWithRange(int min, int max)// used for rolls to hit
	{
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	protected void onStart() {
		super.onStart();
		System.out.println("onStart - battle");
	}

	protected void onRestart() {
		super.onRestart();
		System.out.println("onRestart - battle");
	}

	protected void onResume() {
		super.onResume();
		System.out.println("onResume - battle");
	}

	protected void onPause() {
		super.onPause();
		System.out.println("onPause - battle");
	}

	protected void onStop() {
		super.onStop();
		System.out.println("onStop - battle");
	}

	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy - battle");
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
