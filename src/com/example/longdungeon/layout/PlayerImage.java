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

public class PlayerImage extends View {

	private boolean isFinished = false;
	private int rectWidth;
	private Handler handler = new Handler();
	private volatile int countup = 0;
	private int INTERVAL = 50;
	private Bitmap knight;
	private int knightW, knightH;
	private int xKnight;
	private int frameCountX = 0;
	private int frameCountY = 0;
	private int width, height;
	private Rect srcBox, dstBox;
	private int xmob, velocity,// velocity between mob and player
			distance;// distance between mob and player

	public PlayerImage(final Context context) {
		this(context, null);
	}

	public PlayerImage(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlayerImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		knight = BitmapFactory.decodeResource(getResources(),
				R.drawable.knight_animation);
		knightW = knight.getWidth() / 7;
		knightH = knight.getHeight() / 3;
		stand.run();
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (rectWidth == 0) {
			double ratio = (double) knightW / knightH;
			rectWidth = (int) (height * ratio);
			srcBox = new Rect(frameCountX * knightW, 0, (1 + frameCountX)
					* knightW, knightH);
			countup = width - rectWidth;
			dstBox = new Rect(countup, 0, rectWidth + countup, height);
			xmob = height;
			distance = width - xmob - rectWidth;
			velocity = (width - xmob - rectWidth) / 24;
			if (velocity == 0)
				velocity = 1;
			Log.i("On Draw", "AAA");
		}
		srcBox = new Rect(frameCountX * knightW, frameCountY * knightH,
				(1 + frameCountX) * knightW, (frameCountY + 1) * knightH);
		dstBox = new Rect(countup, 0, rectWidth + countup, height);
		canvas.drawBitmap(knight, srcBox, dstBox, null);

	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w;
		height = h;
	}

	private Runnable incrementRunnable = new Runnable() {
		@Override
		public void run() {
			if (countup > 0) {
				++frameCountX;
				--countup;
				Log.i("Count is: ", "" + countup);
				invalidate();
				if (frameCountX > 3)
					frameCountX = 0;
				handler.postDelayed(this, INTERVAL);

			} else {
				// finishedProgress("Finished!");
			}
		}
	};

	private Runnable decrementRunnable = new Runnable() {
		@Override
		public void run() {
			if (countup < width) {
				++frameCountX;
				++countup;
				Log.i("Count is: ", "" + countup);
				invalidate();
				if (frameCountX > 3)
					frameCountX = 0;
				// vibrate();
				handler.postDelayed(this, INTERVAL);

			} else {

			}
		}
	};

	private Runnable stand = new Runnable() {
		@Override
		public void run() {
			++frameCountX;
			if (frameCountX > 3)
				frameCountX = 0;
			invalidate();
			handler.postDelayed(this, 100);
		}
	};

	public void setStand(boolean stand) {
		if (stand) {
			handler.removeCallbacks(attack);
			handler.removeCallbacks(move);
			frameCountX = 0;
			frameCountY = 0;
			this.stand.run();
		} else
			handler.removeCallbacks(this.stand);
	}

	private byte direct;
	private boolean isStop;
	public static final byte MOVE_LEFT = 0;
	public static final byte MOVE_RIGHT = 1;
	private Runnable move = new Runnable() {
		@Override
		public void run() {
			if (direct == MOVE_LEFT) {
				if (countup <= xmob)
					setAttack(true);
				else {
					countup -= velocity;
					++frameCountX;
					if (frameCountX > 3)
						frameCountX = 0;
					handler.postDelayed(this, 50);
				}
			} else {
				if (countup >= width - rectWidth)
					setStand(true);
				else {
					countup += velocity;
					++frameCountX;
					if (frameCountX > 3)
						frameCountX = 0;
					handler.postDelayed(this, 50);
				}
			}
			invalidate();
		}
	};

	public void setMove(boolean move, byte dir) {
		if (move) {
			handler.removeCallbacks(attack);
			handler.removeCallbacks(stand);
			frameCountX = 0;
			frameCountY = 0;
			direct = dir;
			this.move.run();
		} else
			handler.removeCallbacks(this.move);
	}

	private Runnable attack = new Runnable() {
		@Override
		public void run() {
			if (frameCountX < 6) {
				++frameCountX;
				handler.postDelayed(this, 100);
				invalidate();
			} else
				setMove(true, MOVE_RIGHT);
		}
	};

	public void setAttack(boolean attack) {
		if (attack) {
			handler.removeCallbacks(move);
			handler.removeCallbacks(stand);
			frameCountX = -1;
			frameCountY = 1;
			this.attack.run();
		} else
			handler.removeCallbacks(this.attack);
	}

	public void animationKnight() {
		if (countup > xmob)
			setMove(true, MOVE_LEFT);
		else
			setAttack(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int i = event.getAction();
		if (!isFinished) {
			if (i == MotionEvent.ACTION_DOWN) {
				incrementRunnable.run();
				handler.removeCallbacks(decrementRunnable);
			} else if (i == MotionEvent.ACTION_UP) {
				handler.removeCallbacks(incrementRunnable);
				decrementRunnable.run();
			}
		}
		return true;
	}

	public void setRun() {
		incrementRunnable.run();
	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// width = measureWidth(widthMeasureSpec);
	// height = measureHeight(heightMeasureSpec);
	// setMeasuredDimension(width, height);
	// }
	//
	// private int measureWidth(int measureSpec) {
	// int result = 0;
	// int specMode = MeasureSpec.getMode(measureSpec);
	// int specSize = MeasureSpec.getSize(measureSpec);
	//
	// if (specMode == MeasureSpec.EXACTLY) {
	// // The parent has determined an exact size for the child.
	// result = specSize;
	// } else if (specMode == MeasureSpec.AT_MOST) {
	// // The child can be as large as it wants up to the specified size.
	// result = specSize;
	// } else {
	// // The parent has not imposed any constraint on the child.
	// result = canvasSize;
	// }
	//
	// return result;
	// }
	//
	// private int measureHeight(int measureSpecHeight) {
	// int result = 0;
	// int specMode = MeasureSpec.getMode(measureSpecHeight);
	// int specSize = MeasureSpec.getSize(measureSpecHeight);
	//
	// if (specMode == MeasureSpec.EXACTLY) {
	// // We were told how big to be
	// result = specSize;
	// } else if (specMode == MeasureSpec.AT_MOST) {
	// // The child can be as large as it wants up to the specified size.
	// result = specSize;
	// } else {
	// // Measure the text (beware: ascent is a negative number)
	// result = canvasSize;
	// }
	//
	// return (result + 2);
	// }
}
