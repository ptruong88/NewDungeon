package com.example.longdungeon;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BattleActivity extends ActionBarActivity {

	private ListView listAbility;

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

		
	}
	
	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        setUpPic();// Set up mob and player picture based on layout.
    }

	private void setUpPic() {
		// TODO Auto-generated method stub
		RelativeLayout relLyoutPic = (RelativeLayout) this
				.findViewById(R.id.layoutPic);
		int lyoutX = relLyoutPic.getWidth();
		int lyoutY = relLyoutPic.getHeight();
		
		System.out.println("Layout width "+lyoutX);
		System.out.println("Layout height "+lyoutY);

		ImageView imgMob = (ImageView) this.findViewById(R.id.picMob);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				lyoutX/2, lyoutY);
		imgMob.setLayoutParams(params);

		ImageView imgPlayer = (ImageView) this.findViewById(R.id.picPlayer);
		params = new RelativeLayout.LayoutParams(
				lyoutX/2, lyoutY/2);
		imgPlayer.setLayoutParams(params);
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
