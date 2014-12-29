package com.example.longdungeon.layout;

import com.example.longdungeon.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ImageBattle extends View {

	private PlayerImage player;
	private MobImage mob;
	private Handler handler;
	private int width, height;// Screen size
	private AnimationPlayer animPlayer;//The animation for player
	private AnimationMob animMob;//The animation for mob

	public ImageBattle(Context context) {
		super(context);
		player = new PlayerImage(BitmapFactory.decodeResource(getResources(),
				R.drawable.knight_animation));
		mob = new MobImage(BitmapFactory.decodeResource(getResources(),
				R.drawable.goblin));
	}

	public ImageBattle(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ImageBattle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w;
		height = h;
	}

	public void onDraw(Canvas canvas) {
		if (player == null) {
			player = new PlayerImage(BitmapFactory.decodeResource(
					getResources(), R.drawable.knight_animation), width, height);
			mob = new MobImage(BitmapFactory.decodeResource(getResources(),
					R.drawable.goblin_750), width, height);
			handler = new Handler();
			animPlayer = new AnimationPlayer(player,mob,handler, this);
			animMob = new AnimationMob(player,mob,handler, this);
			player.setMobWidth(mob.getWidth()-200);
			mob.setPlayerWidth(player.getWidth()-200);
			animPlayer.setAnimationMob(animMob);
			animMob.setAnimationPlayer(animPlayer);
			setStand();
		}
		player.draw(canvas);
		mob.draw(canvas);
	}
	
	public void setStand(){
		animPlayer.setStand();
		animMob.setStand();
	}
	
	public void setPlayerAttack(){
		animPlayer.setMoveLeft();
	}
	
	public void setMobAttack(){
		animMob.setMoveRight();
	}

	public PlayerImage getPlayer() {
		return player;
	}

	public void setPlayer(PlayerImage player) {
		this.player = player;
	}

	public MobImage getMob() {
		return mob;
	}

	public void setMob(MobImage mob) {
		this.mob = mob;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	
}
