package com.example.longdungeon.layout;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class BattleLayout extends RelativeLayout {

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
	}

}
