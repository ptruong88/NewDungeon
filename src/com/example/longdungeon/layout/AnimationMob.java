package com.example.longdungeon.layout;

import android.os.Handler;

public class AnimationMob {

	private Handler handler;
	private PlayerImage player;
	private MobImage mob;
	private ImageBattle battle;
	private AnimationPlayer animPlayer;

	public AnimationMob() {
	}

	public AnimationMob(PlayerImage player, MobImage mob, Handler handler,
			ImageBattle battle) {
		this.handler = handler;
		this.player = player;
		this.mob = mob;
		this.battle = battle;
	}

	public MobImage getMobImage(){
		return mob;
	}
	
	private Runnable stand = new Runnable() {
		@Override
		public void run() {
			mob.updateStand();
			handler.postDelayed(this, 100);
			battle.invalidate();
		}
	};

	public void setStand() {
		mob.setStand();
		this.stand.run();
	}
	
	private Runnable defend = new Runnable() {
		@Override
		public void run() {
			if(mob.isDoneDefend()){
				handler.removeCallbacks(defend);
				setStand();
			}
			else{
			mob.updateDefend();
			handler.postDelayed(this, defendDelay);
			battle.invalidate();
			}
		}
	};

	private long defendDelay;
	public void setDefend() {
		handler.removeCallbacks(stand);
		defendDelay = (player.getTimeDelay()*3)/28;
		mob.setDefend();
		this.defend.run();
	}

	private Runnable moveLeft = new Runnable() {
		@Override
		public void run() {
			if (mob.isBack()) {
				handler.removeCallbacks(moveLeft);
				setStand();
			} else {
				mob.updateMoveLeft();
				handler.postDelayed(this, 50);
				battle.invalidate();
			}
		}
	};

	public void setMoveLeft() {
		mob.setMoveLeft();
		moveLeft.run();
	}

	private Runnable attack = new Runnable() {
		@Override
		public void run() {
			if (mob.isDoneAttack()) {
				handler.removeCallbacks(attack);
				setMoveLeft();
			} else {
				mob.updateAttack();
				handler.postDelayed(this, 400);
				battle.invalidate();
			}
		}
	};

	private void setAttack() {
		mob.setAttack();
		animPlayer.setDefend();
		attack.run();
	}

	private Runnable moveRight = new Runnable() {
		@Override
		public void run() {
			if (mob.isAtPlayer()) {
				handler.removeCallbacks(moveRight);
				setAttack();
			} else {
				mob.updateMoveRight();
				handler.postDelayed(this, 50);
				battle.invalidate();
			}
		}
	};

	public void setMoveRight() {
		handler.removeCallbacks(stand);
		mob.setMoveRight();
		moveRight.run();
	}
	
	public void setAnimationPlayer(AnimationPlayer animPlayer) {
		this.animPlayer = animPlayer;
	}
}
