package com.example.longdungeon;

import com.example.longdungeon.character.Player;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.os.Bundle;
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
		setContentView(R.layout.activity_login);

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
				intentBattle.putExtra(Player.PLAYER_DATA,
						player);
				startActivity(intentBattle);
				finish();
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