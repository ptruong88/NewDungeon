package com.example.longdungeon.layout;

import android.os.Handler;

public class AnimationPlayer {

	private Handler handler;
	private PlayerImage player;
	private MobImage mob;
	private ImageBattle battle;
	private AnimationMob animMob;

	public AnimationPlayer() {
	}

	public AnimationPlayer(PlayerImage player, MobImage mob, Handler handler,
			ImageBattle battle) {
		this.handler = handler;
		this.player = player;
		this.mob = mob;
		this.battle = battle;
	}

	private Runnable stand = new Runnable() {
		@Override
		public void run() {
			player.updateStand();
			handler.postDelayed(this, 100);
			battle.invalidate();
		}
	};

	public void setStand() {
		player.setStand();
		this.stand.run();
	}

	private Runnable moveLeft = new Runnable() {
		@Override
		public void run() {
			if (player.isAtMob()) {
				handler.removeCallbacks(moveLeft);
				setAttack();
			} else {
				player.updateMoveLeft();
				handler.postDelayed(this, 50);
				battle.invalidate();
			}
		}
	};

	public void setMoveLeft() {
		handler.removeCallbacks(stand);
		player.setMoveLeft();
		moveLeft.run();
	}

	private Runnable attack = new Runnable() {
		@Override
		public void run() {
			if (player.isDoneAttack()) {
				handler.removeCallbacks(attack);
				setMoveRight();
			} else {
				player.updateAttack();
				handler.postDelayed(this, attackDelay);
				battle.invalidate();
			}
		}
	};

	private long attackDelay;
	private void setAttack() {
		player.setAttack();
		animMob.setDefend();
		attackDelay = player.getTimeDelay()/7;
		attack.run();
	}

	private Runnable moveRight = new Runnable() {
		@Override
		public void run() {
			if (player.isBack()) {
				handler.removeCallbacks(moveRight);
				setStand();
			} else {
				player.updateMoveRight();
				handler.postDelayed(this, 50);
				battle.invalidate();
			}
		}
	};

	private void setMoveRight() {
		player.setMoveRight();
		moveRight.run();
	}

	private Runnable defend = new Runnable() {
		@Override
		public void run() {
			if (player.isDoneDefend()) {
				handler.removeCallbacks(defend);
				setStand();
			} else {
				player.updateDefend();
				handler.postDelayed(this, 100);
				battle.invalidate();
			}
		}
	};

	public void setDefend() {
		handler.removeCallbacks(stand);
		player.setDefend();
		defend.run();
	}
	
	public void setAnimationMob(AnimationMob animMob) {
		this.animMob = animMob;
	}

}
