package com.example.longdungeon.layout;

import java.util.ArrayList;
import java.util.Random;

import com.example.longdungeon.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class MobImage {

	private boolean isFinished = false;
	private int mobWidth, mobHeight;// The width and height of image on screen
	private int mobLeft, mobTop; // The coordinates of mob on screen
	private int INTERVAL = 50;
	private Bitmap mob;
	private int mobW, mobH;
	private int xMob;
	private int frameCountX = 0;
	private int frameCountY = 0;
	private int width, height;
	private Rect srcBox, dstBox;
	private int kniWidth, velocity,// velocity between mob and player
			distance;// distance between mob and player

	public MobImage(final Bitmap bitmap) {
		mob = bitmap;
		mobW = mob.getWidth() / 4;
		mobH = mob.getHeight() / 5;
		setUp();
	}

	public MobImage(Bitmap bitmap, int width, int height) {
		this.width = width;
		this.height = height;
		mob = bitmap;
		mobW = mob.getWidth() / 4;
		mobH = mob.getHeight() / 5;
		setUp();
	}

	private void setUp() {
		double ratio = (double) mobW / mobH;
		mobHeight = height;
		mobWidth = (int) (mobHeight * ratio);
		srcBox = new Rect(frameCountX * mobW, 0, (1 + frameCountX) * mobW, mobH);
		mobLeft = 0;
		mobTop = 0;
		dstBox = new Rect(mobLeft, mobTop, mobWidth + mobLeft, mobHeight);

		Log.i("On Draw", "AAA");
	}

	public void draw(Canvas canvas) {
		srcBox = new Rect(frameCountX * mobW, frameCountY * mobH,
				(1 + frameCountX) * mobW, (frameCountY + 1) * mobH);
		dstBox = new Rect(mobLeft, mobTop, mobWidth + mobLeft, mobHeight);
		canvas.drawBitmap(mob, srcBox, dstBox, null);

	}

	public void setPlayerWidth(int width) {
		kniWidth = width;
		distance = this.width - kniWidth - mobWidth;
		velocity = distance / 24;
		if (velocity == 0)
			velocity = 1;
	}

	public int getWidth() {
		return mobWidth;
	}

	public void updateStand() {
		++frameCountX;
		if (frameCountX > 3)
			frameCountX = 0;
	}

	public void setStand() {
		mobLeft = 0;
		frameCountX = 0;
		frameCountY = 0;
	}

	public void setDefend() {
		frameCountX = 0;
		frameCountY = 3;
		count = 0;
	}

	public void updateDefend() {
		++frameCountX;
		++count;
		if (frameCountX > 3) {
			frameCountX = 0;
			frameCountY = 4;
		}
	}

	private int count;

	public boolean isDoneDefend() {
		return count > 6;
	}

	public void setMoveRight() {
		frameCountX = 0;
		frameCountY = 0;
	}

	public void updateMoveRight() {
		mobLeft += velocity;
		++frameCountX;
		if (frameCountX > 3)
			frameCountX = 0;
	}

	public boolean isAtPlayer() {
		return dstBox.right >= width - kniWidth;
	}

	public void setAttack() {
		count = -1;
		frameCountX = -1;
		frameCountY = 1;
	}

	public void updateAttack() {
		++count;
		++frameCountX;
		if (count == 3) {
			frameCountX = 0;
			frameCountY = 2;
		}
	}

	public boolean isDoneAttack() {
		return count > 5;
	}

	public void setMoveLeft() {
		frameCountX = 0;
		frameCountY = 0;
	}

	public void updateMoveLeft() {
		mobLeft -= velocity;
		++frameCountX;
		if (frameCountX > 3)
			frameCountX = 0;
	}

	public boolean isBack() {
		return mobLeft <= 0;
	}

	//

	//

	//
	//
	//
	// private int movShake;
	// private Runnable shake = new Runnable() {
	// @Override
	// public void run() {
	// if (count < 6) {
	// ++count;
	// mobLeft += movShake;
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
	// public void animationMob() {
	// setMoveRight(true);
	// }
}
