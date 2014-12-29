package com.example.longdungeon.layout;

import java.util.ArrayList;
import java.util.Random;

import com.example.longdungeon.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PlayerImage {

	private boolean isFinished = false;
	private int kniWidth, kniHeight;// The width and height of image on screen

	private int kniLeft, kniTop; // The coordinates of knight on screen
	private int INTERVAL = 50;
	private Bitmap knight;
	private int knightW, knightH;
	private int xKnight;
	private int frameCountX = 0;
	private int frameCountY = 0;
	private int width, height;
	private Rect srcBox, dstBox;
	private int mobWidth, velocity,// velocity between mob and player
			distance;// distance between mob and player

	public PlayerImage(Bitmap bitmap) {
		knight = bitmap;
		knightW = knight.getWidth() / 7;
		knightH = knight.getHeight() / 3;
		setUp();
	}

	public PlayerImage(Bitmap bitmap, int width, int height) {
		this.width = width;
		this.height = height;
		knight = bitmap;
		knightW = knight.getWidth() / 7;
		knightH = knight.getHeight() / 3;
		setUp();
	}

	private void setUp() {
		double ratio = (double) knightW / knightH;
		kniHeight = (int) (height * 0.8);
		kniWidth = (int) (kniHeight * ratio);
		srcBox = new Rect(frameCountX * knightW, 0,
				(1 + frameCountX) * knightW, knightH);
		kniLeft = width - kniWidth;
		kniTop = height - kniHeight;
		dstBox = new Rect(kniLeft, kniTop, kniWidth + kniLeft, height);

		Log.i("On Draw", "AAA");
	}

	public void draw(Canvas canvas) {
		srcBox = new Rect(frameCountX * knightW, frameCountY * knightH,
				(1 + frameCountX) * knightW, (frameCountY + 1) * knightH);
		dstBox = new Rect(kniLeft, kniTop, kniWidth + kniLeft, height);
		canvas.drawBitmap(knight, srcBox, dstBox, null);

	}

	public void setMobWidth(int width) {
		mobWidth = width;
		distance = this.width - mobWidth - kniWidth;
		velocity = distance / 24;
		if (velocity == 0)
			velocity = 1;
	}

	public int getWidth() {
		return kniWidth;
	}

	public void updateStand() {
		++frameCountX;
		if (frameCountX > 3)
			frameCountX = 0;
	}

	public void setStand() {
		frameCountX = 0;
		frameCountY = 0;
	}

	private int count;
	public void setMoveLeft() {
		count =0 ;
		frameCountX = 0;
		frameCountY = 0;
	}

	public void updateMoveLeft() {
		++count;
		kniLeft -= velocity;
		++frameCountX;
		if (frameCountX > 3)
			frameCountX = 0;
	}

	public boolean isAtMob() {
		return kniLeft <= mobWidth;
	}

	public void setAttack() {
		frameCountX = -1;
		frameCountY = 1;
	}

	public void updateAttack() {
		++frameCountX;
	}

	public boolean isDoneAttack() {
		return frameCountX > 5;
	}

	public void setMoveRight() {
		frameCountX = 0;
		frameCountY = 0;
	}

	public void updateMoveRight() {
		kniLeft += velocity;
		++frameCountX;
		if (frameCountX > 3)
			frameCountX = 0;
	}

	public boolean isBack() {
		return kniLeft >= width - kniWidth;
	}

	public void setDefend() {
		frameCountX = -1;
		frameCountY = 2;
	}

	public void updateDefend() {
		++frameCountX;
	}

	public boolean isDoneDefend() {
		return frameCountX > 5;
	}
	
	public long getTimeDelay(){
		return 4000 - (count*100);
	}

	// private int count;
	// private int movShake;
	// private Runnable shake = new Runnable() {
	// @Override
	// public void run() {
	// if (count < 6) {
	// ++count;
	// kniLeft += movShake;
	// movShake *= -1;
	// handler.postDelayed(this, 50);
	//
	// } else {
	// handler.removeCallbacks(shake);
	// setStand(true);
	// }
	// }
	// };
	//
	// public void setShake(boolean shake) {
	// if (shake) {
	// handler.removeCallbacks(stand);
	// count = 0;
	// movShake = -10;
	// this.shake.run();
	// } else
	// handler.removeCallbacks(this.shake);
	// }
	//
	// public void animationKnight() {
	// // setMoveLeft(true);
	// setShake(true);
	// }
}
