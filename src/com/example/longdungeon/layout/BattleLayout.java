package com.example.longdungeon.layout;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class BattleLayout extends RelativeLayout {
	
	private int width, height;

	public BattleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public BattleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BattleLayout(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
//		setMeasuredDimension(width, height);
		System.out.println("onMeasure width sample " + getMeasuredWidth());
		System.out.println("onMeasure heigh sample " + getMeasuredHeight());
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		// here you have the size of the view and you can do stuff
		System.out.println("onLayout width sample " + (r-l));
		System.out.println("onLayout heigh sample " + (b-t));
		width = r-l;
		height = b-t;
	}
	
	protected void onSizeChanged (int w, int h, int oldw, int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		System.out.println("onSize width sample " + w);
		System.out.println("onSize heigh sample " + h);
	}

}
