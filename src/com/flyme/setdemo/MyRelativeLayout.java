package com.flyme.setdemo;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class MyRelativeLayout extends RelativeLayout {

	private Scroller scroller;


	public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public MyRelativeLayout(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context){
		scroller = new Scroller(context, new DecelerateInterpolator ());
	}
	
	public void startMove(int startX, int startY, int dx, int dy){
		scroller.startScroll(startX, startY, dx, dy,400);
		postInvalidate();
	}
	
	
	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
		}
		super.computeScroll();
	}

}
