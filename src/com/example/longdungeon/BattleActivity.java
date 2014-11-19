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

public class BattleActivity extends ActionBarActivity implements
		OnClickListener, OnItemClickListener {

	private ListView listAbility;
	private View lyoutBattle;
	private TextView txtViewMobName, txtViewMobHp, txtViewPlayerName,
			txtViewPlayerScore, txtViewPlayerHp, txtViewPlayerMana,
			txtViewStamina;
	private Mob mob;
	private Player player;
	private Button btnAttack, btnDenfend, btnMagic, btnItem, btnRun;
	private String[] listAttack, listMagic, listItem;
	private ArrayAdapter<String> adapterAttack, adapterMagic, adapterItem;
	
	//gordon's variables for the game loop
			/* player vars
			int playerMaxHp = 120;
			int playerHp =120;
			int playerMana = 60;
			int playerMaxMana = 60;
			int playerMaxStm = 100; 
			int playerStm = 100;
			int playerDef = 20;
			int playerBaseAtk=15;
			
			//enemyVars
			int enemyMaxHp = 76;
			int enemyHp =76;
			int enemyMana = 0;
			int enemyMaxMana = 0;
			int enemyMaxStm = 60; 
			int enemyStm = 60;
			int enemyDef = 20;
			int enemyBaseAtk=14;
	*/		
			//gameLoopVars
			boolean playerTurn = true;
			boolean playerDefending = false;
			boolean enemyDefending = false;
			boolean ranAway = false;
			int d10Roll = 0;
			int atkVal;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battle);

		setUpMob();
		setUpPlayer();
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
			{
				playerDefending = true;
				if(player.getCurStm()<player.getMaxStm())
				{
					int stmRegen = (player.getCurStm()/5);
					player.setCurStm(stmRegen+player.getCurStm());//get back 1/5 of your stamina
					txtViewStamina.setText("Stamina: " + player.getCurStm() + "/"+ player.getMaxStm());
					playerTurn=false;//defending uses your turn
				}
				
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
		if (parent.getItemAtPosition(position).toString().contains("DMG")) {
			
			//mobCurHp -= baseDamage;
			switch (position) {
			case 0:// basic attack case based on it being in the 0th position
				if(player.getCurStm()>=10)
				{
					player.setCurStm((player.getCurStm())-10);
					txtViewStamina.setText("Stamina: " + player.getCurStm() + "/"+ player.getMaxStm());//attacks always cost stamina, if they miss or not
					
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{Toast.makeText(getApplicationContext(), "Your attack missed!",Toast.LENGTH_SHORT).show();}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal= (player.getDamage())/2;
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Glancing hit for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());
						
					}
					
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{
						atkVal= (player.getDamage());
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Attack hits for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());						
					}
					
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal= (player.getDamage());
						atkVal= atkVal*2;//critical attack doubles damage
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Critical attack hits for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());
					}
					atkVal=0;//clear attack val;
					playerTurn = false;//once you've attacked the enemy gets a turn
				}
				else{
						Toast.makeText(getApplicationContext(), "not enough stamina!",Toast.LENGTH_SHORT).show();
					}
				break;
			case 1:// medium attack case based on it being in the 1st position
				if(player.getCurStm()>=20)
				{
					player.setCurStm((player.getCurStm())-20);
					txtViewStamina.setText("Stamina: " + player.getCurStm() + "/"+ player.getMaxStm());//attacks always cost stamina, if they miss or not
					
					d10Roll =randomWithRange(1,10);
					
					if((d10Roll==1)||(d10Roll==2))
					{Toast.makeText(getApplicationContext(), "Your attack missed!",Toast.LENGTH_SHORT).show();}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal= (player.getDamage()*4)/3;//medium attack value is 4/3 of base attack
						atkVal = atkVal/2;//damaged halved for glancing blow
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Glancing hit for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());
					}
					
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{
						atkVal= (player.getDamage()*4)/3;//medium attack value is 4/3 of base attack
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Attack hits for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());	
					}
					
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal= (player.getDamage()*4)/3;//medium attack value is 4/3 of base attack
						atkVal= atkVal*2;//atk doubled for critical hit
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Critical attack hits for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());
					}
					atkVal=0;
					playerTurn = false;//once you've attacked the enemy gets a turn
				}
				else{Toast.makeText(getApplicationContext(), "not enough stamina!",
						Toast.LENGTH_SHORT).show();}
				break;
			default://  heavy attack case based on it being in the 2nd position
				if(player.getCurStm()>=30)
				{
					player.setCurStm((player.getCurStm())-30);
					txtViewStamina.setText("Stamina: " + player.getCurStm() + "/"+ player.getMaxStm());//attacks always cost stamina, if they miss or not
					
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{Toast.makeText(getApplicationContext(), "Your attack missed!",Toast.LENGTH_SHORT).show();}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal= (player.getDamage()*2);//Heavy attack value is 2 times the base attack
						atkVal = atkVal/2;//damaged halved for glancing blow
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Glancing hit for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());
					}
					
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{
						atkVal= (player.getDamage()*2);//Heavy attack value is 2 times the base attack
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Attack hits for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());	
					}
					
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal= (player.getDamage()*2);//Heavy attack value is 2 times the base attack
						atkVal= atkVal*2;//atk doubled for critical hit
						if (enemyDefending == true)
						{atkVal= atkVal/2;}
						String atkString="Critical attack hits for "+atkVal+" damage!";
						Toast.makeText(getApplicationContext(), atkString ,Toast.LENGTH_SHORT).show();
						mob.setCurHp(mob.getCurHp()-atkVal);
						txtViewMobHp.setText("HP: " + mob.getCurHp() + "/" + mob.getMaxHp());
					}
					atkVal=0;
					playerTurn = false;//once you've attacked the enemy gets a turn
				}
				else{Toast.makeText(getApplicationContext(), "not enough stamina!",
						Toast.LENGTH_SHORT).show();}
				break;
			}
		}
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
						Intent intentShopping = new Intent(BattleActivity.this,
								ShoppingActivity.class);
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
	
	public void startGame()
	{
		Toast.makeText(getApplicationContext(), "A fearsome goblin approaches!",
				Toast.LENGTH_SHORT).show();
/*
		while ((player.getCurHp())>0 && (mob.getCurHp())>0 && ranAway ==false)
		{
			
//defend================================================================================
			else if(playerChoice==2)
			{
				playerDefending =true;
				playerStm = playerMaxStm;
				playerChoice=0;
			}
//defend================================================================================
			
//magic================================================================================
			else if(playerChoice==3&&(playerMana>0))
			{
				playerChoice =0;
				System.out.println("Choose your spell\n" +
						"1:Fireball\n" +
						"2:Heal\n" +
						"3:Thunderbolt\n");
				playerChoice = sc.nextInt();
				if(playerChoice ==1)
				{
					atkVal = 15;
					if((playerStm - 15)>0)
					{
						playerMana-=15;//costs 10 mana
					}
					else if((playerStm - 15)<0)
					{
						playerMana = 0;//can't have negative stamina
					}
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{System.out.println("Your spell missed!");}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal = (atkVal/2);
						
						if(enemyDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("Glancing blow for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{	
						if(enemyDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("Spell hits for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal = (((atkVal)*3)/2);
						
						if(enemyDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("Critical hit for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					atkVal = 0;
					d10Roll = 0;
					
				}
				else if(playerChoice ==2)
				{
					int plusHealth = 30;
					if((playerMana - 20)>0)
					{
						playerStm-=20;//costs 20 mana
					}
					else if((playerStm -20)<0)
					{
						playerStm = 0;//can't have negative mana
					}
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{System.out.println("Your spell missed!");}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						plusHealth = (plusHealth/2);
						
						System.out.println("Spell restores "+plusHealth+" health!");
						playerHp+=plusHealth;						
					}
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{	
						System.out.println("Spell restores "+plusHealth+" health!");
						playerHp+=plusHealth;						
					}
					else if((d10Roll==9)||(d10Roll==10))
					{
						plusHealth = (plusHealth/2);
						System.out.println("Perfect Casting! You gain "+plusHealth+"health! ");
						playerHp+=plusHealth;						
					}
					plusHealth = 0;
					d10Roll = 0;
				}
				else if(playerChoice ==3)
				{
					atkVal = 35;
					if((playerStm - 25)>0)
					{
						playerMana-=25;//costs 10 mana
					}
					else if((playerStm - 25)<0)
					{
						playerMana = 0;//can't have negative stamina
					}
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{System.out.println("Your spell missed!");}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal = (atkVal/2);
						
						if(enemyDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("Glancing blow for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{	
						if(enemyDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("Spell hits for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal = (((atkVal)*3)/2);
						
						if(enemyDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("Critical hit for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					atkVal = 0;
					d10Roll = 0;
				}
				playerChoice=0;
			}
			else if ( (playerChoice ==3) && playerMana<=0)
			{
				System.out.println("You are all out of Mana! you must forfeit a turn to regain your composure");
				playerMana = playerMaxMana;
				playerChoice=0;
			}
//magic================================================================================================

//item=================================================================================================

			else if(playerChoice==4)
			{
				playerChoice =0;
				System.out.println("Choose your item\n" +
						"1:Health Potion\n" +
						"2:Mana Potion\n" +
						"3:Stamina Potion\n");
				playerChoice = sc.nextInt();
				if(playerChoice==1)
				{
					playerHp+=50;
				}
				if(playerChoice==2)
				{
					playerMana+=70;
				}
				if(playerChoice==2)
				{
					playerStm+=80;
				}
			}
			
			
//end player turn=====================================================================================

//begin enemy turn=====================================================================================
			if(enemyStm<=0)
			{
				System.out.println("The goblin wheezes and stops to catch it's breath");
				enemyStm=enemyMaxStm;
			}
			else if(enemyStm>0)
			{
				System.out.println("The goblin attacks!");
				int enemyAtk=randomWithRange(1,3);
				if(enemyAtk ==1)
				{
					atkVal = enemyBaseAtk;
					if((enemyStm - 10)>0)
					{
						enemyStm-=10;//costs 10 stamina
					}
					else if((enemyStm - 10)<0)
					{
						enemyStm = 0;//can't have negative stamina
					}
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{System.out.println("The Goblin's attack missed!");}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal = (enemyBaseAtk/2);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin lands a glancing blow for"+atkVal+"damage!");
						playerHp-=atkVal;						
					}
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{
						atkVal = (enemyBaseAtk);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin's attack hits for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal = (((enemyBaseAtk)*3)/2);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin lands a critical hit for "+atkVal+" damage!");
						playerHp-=atkVal;						
					}
					atkVal = 0;
					d10Roll = 0;
					
				}
				else if(enemyAtk ==2)
				{
					atkVal = enemyBaseAtk+10;
					if((enemyStm - 10)>0)
					{
						enemyStm-=10;//costs 10 stamina
					}
					else if((enemyStm - 10)<0)
					{
						enemyStm = 0;//can't have negative stamina
					}
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{System.out.println("The Goblin's attack missed!");}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal = (atkVal/2);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin lands a glancing blow for"+atkVal+"damage!");
						playerHp-=atkVal;						
					}
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin's attack hits for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal = (((atkVal)*3)/2);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin lands a critical hit for "+atkVal+" damage!");
						playerHp-=atkVal;						
					}
					atkVal = 0;
					d10Roll = 0;
				}
				else if(playerChoice ==3)
				{
					atkVal = enemyBaseAtk+20;
					if((enemyStm - 10)>0)
					{
						enemyStm-=10;//costs 10 stamina
					}
					else if((enemyStm - 10)<0)
					{
						enemyStm = 0;//can't have negative stamina
					}
					d10Roll =randomWithRange(1,10);
					if((d10Roll==1)||(d10Roll==2))
					{System.out.println("The Goblin's attack missed!");}
					
					else if((d10Roll==2)||(d10Roll==3)||(d10Roll==4))
					{
						atkVal = (atkVal/2);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin lands a glancing blow for"+atkVal+"damage!");
						playerHp-=atkVal;						
					}
					else if((d10Roll==5)||(d10Roll==6)||(d10Roll==7)||(d10Roll==8))
					{
						atkVal = (atkVal);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin's attack hits for"+atkVal+"damage!");
						enemyHp-=atkVal;						
					}
					else if((d10Roll==9)||(d10Roll==10))
					{
						atkVal = (((atkVal)*3)/2);
						
						if(playerDefending)
						{atkVal =  atkVal/2;}
						
						System.out.println("The goblin lands a critical hit for "+atkVal+" damage!");
						playerHp-=atkVal;						
					}
					atkVal = 0;
					d10Roll = 0;
				}
			
			}
		}
		if (playerHp==0)
		{
			System.out.println("You have been defeated...");
		}
		else if(ranAway==true)
		{System.out.println("You run away screaming...");}
		else
		{
			System.out.println("You are victorious!");
		}
		
		
		*/
		

	}
	
	public int randomWithRange(int min, int max)//used for rolls to hit
	{
		   int range = (max - min) + 1;     
		   return (int)(Math.random() * range) + min;
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
