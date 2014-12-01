package com.example.longdungeon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import com.example.longdungeon.character.Player;
import com.example.longdungeon.item.Equipment;
import com.example.longdungeon.item.Item;
import com.example.longdungeon.item.Potion;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginTestActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_test);

		final EditText edTxtLogin = (EditText) this
				.findViewById(R.id.editTextLogin);
		final Button btnLogin = (Button) this.findViewById(R.id.buttonLogin);
		btnLogin.setVisibility(Button.INVISIBLE);

		edTxtLogin.addTextChangedListener(new TextWatcher() {
			String namePlayer;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				namePlayer = edTxtLogin.getText().toString();
				btnLogin.setVisibility(namePlayer.length() < 1 ? Button.INVISIBLE
						: Button.VISIBLE);
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Player player = new Player(edTxtLogin.getText().toString());
				Intent intentBattle = new Intent(LoginTestActivity.this,
						BattleTestActivity.class);
				intentBattle.putExtra(Player.PLAYER_DATA, player);
				startActivity(intentBattle);
				finish();
			}
		});
	}

	protected void onResume() {
		super.onResume();
		this.findViewById(R.id.layoutLoginText).setVisibility(View.INVISIBLE);

		File file = new File(getFilesDir(), Player.PLAYER_FILE);
		 file.delete();
//		if (file.exists()) {
//
//			// new Handler().postDelayed(new Runnable() {
//			//
//			// @Override
//			// public void run() {
//			// TODO Auto-generated method stub
//			try {
//				BufferedReader inputReader = new BufferedReader(
//						new InputStreamReader(openFileInput(Player.PLAYER_FILE)));
//
//				Player player = new Player();
//				player.readFromFile(player,inputReader);				
//
//				Intent intentShop = new Intent(LoginTestActivity.this,
//						ShoppingTestActivity.class);
//				intentShop.putExtra(Player.PLAYER_DATA, player);
//				startActivity(intentShop);
//				finish();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// }
//			// }, 4000);
//
//		} else
			this.findViewById(R.id.layoutLoginText).setVisibility(View.VISIBLE);
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
