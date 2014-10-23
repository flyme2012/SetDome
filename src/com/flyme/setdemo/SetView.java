package com.flyme.setdemo;

import android.R.bool;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SetView extends RelativeLayout {

	private RelativeLayout leftContent;
	private LinearLayout rightContent;
	private RelativeLayout rightMove;
	private int wight;

	public SetView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public SetView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public SetView(Context context) {
		super(context);
		init(context);
	}
	
	
	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context){
		wight = getWight();
		LayoutInflater.from(context).inflate(R.layout.setview, this);
		leftContent = (RelativeLayout) findViewById(R.id.left_content);
		rightMove = (RelativeLayout) findViewById(R.id.right_move);
		rightContent = (LinearLayout) findViewById(R.id.right_content);
		
		initView();
	}
	
	private static int DEFULT_SCALE = 5 ;
	private int startX;
	private int startY;
	private int boundaryX = 0 ;
	
	private void initView(){
		LayoutParams leftparams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		leftContent.setLayoutParams(leftparams);
		LayoutParams rightparams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rightparams.leftMargin = (int) (wight / DEFULT_SCALE) ;
		rightMove.setLayoutParams(rightparams);
		boundaryX = wight / DEFULT_SCALE ;
	}
	
	
	/**
	 * 设置整个左半部的VIew
	 * @param view
	 */
	public void setLeftContentView(View view){
		if (leftContent.getChildCount() == 0) {
			leftContent.addView(view);
		}else {
			leftContent.removeAllViews();
			leftContent.addView(view);
		}
	}
	
	
	/**
	 * 设置右半部的VIew
	 * @param view
	 */
	public void setRightContentView(View view){
		if (rightMove.getChildCount() == 0) {
			rightMove.addView(view);
		}else {
			rightMove.removeAllViews();
			rightMove.addView(view);
		}
	}
	
	
	
	private boolean isV = false ;
	private boolean isH = false ;
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			reset();
		}
		if (ev.getX() < boundaryX ) {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				restoreX();
			}
			leftContent.dispatchTouchEvent(ev);
			return true ;
		}
		
		if (rightIsMove) {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				restoreX();
			}
			return true ;
		}
		if (isV ) {
			return super.dispatchTouchEvent(ev);
		}
		
		if (isH) {
			onTouchEvent(ev);
			return true ;
		}
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) ev.getX();
			startY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getX();
			int moveY = (int) ev.getY();
			if ((moveX - startX ) > 5 && isV == false) {
				isH = true ;
				onTouchEvent(ev);
				return true;
			}
			if (Math.abs(moveY -startY) > 5 && isH == false ) {
				isV = true ;
				return super.dispatchTouchEvent(ev);
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	
	private void reset(){
		isV = false ;
		isH = false ;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("down");
			startX = (int) event.getX();
			startY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			moveX = (int) event.getX();
			if ((moveX - startX ) > 5) {
				rightMove.scrollTo(startX - moveX, 0);
			}else {
			}
			break;
		case MotionEvent.ACTION_UP:
			restoreRightMove(moveX - startX);
			moveX = 0;
			break;
		}
		return  true;
	}
	
	
	private void restoreRightMove(int event){
		if (event > wight /  3) {
			rightMove.scrollTo(-(int) (wight/DEFULT_SCALE * 2), 0);
			boundaryX = wight / 5 * 3 ;
			rightIsMove = true ;
		}else {
			restoreX();
		}
	}
	
	private void restoreX(){
		rightIsMove = false ;
		rightMove.scrollTo(0, 0);
		boundaryX = wight /DEFULT_SCALE;
	}
	
	
	private boolean rightIsMove = false ;
	private int moveX;
	
	
	/**
	 * 获取屏幕的宽度
	 * @return
	 */
	private int getWight(){
		WindowManager wm = (WindowManager) getContext() 
                .getSystemService(Context.WINDOW_SERVICE); 
		return wm.getDefaultDisplay().getWidth(); 
	}

}
