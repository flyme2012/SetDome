package com.flyme.setdemo;

import android.R.integer;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class SetView extends RelativeLayout {

	private RelativeLayout leftContent;
	private LinearLayout rightContent;
	private MyRelativeLayout rightMove;
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
		rightMove = (MyRelativeLayout) findViewById(R.id.right_move);
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
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		if (ev.getX() < boundaryX ) {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				restoreX((int)(ev.getX() - startX));
			}
			leftContent.dispatchTouchEvent(ev);
			return true ;
		}
		
		if (rightIsMove) {
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				restoreX((int)(ev.getX()- startX));
			}
			return true ;
		}
		
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			isV = false ;
			if (isH) {
				restoreRightMove((int )(ev.getX() - startX));
				isH = false ;
				return true;
			}else {
				isH = false ;
				return super.dispatchTouchEvent(ev);
			}
		}
		
		System.out.println(isH+"=="+isV);
		if (isV) {
			return super.dispatchTouchEvent(ev);
		}
		
		
		if (isH) {
			onTouchEvent(ev);
			return true ;
		}
		
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			startX = (int) ev.getX();
			startY = (int) ev.getY();
		}
		
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			onTouchEvent(ev);
		}
		
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			startX = (int) ev.getX();
//			startY = (int) ev.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
////			if ((moveX - startX ) > 5 && isV == false ) {
//				onTouchEvent(ev);
////			}
//				System.out.println("isv"+isV);
//			if (isV) {
//				rightContent.dispatchTouchEvent(ev);
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			onTouchEvent(ev);
//			break;
//		}
		return super.dispatchTouchEvent(ev);
//		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();
			int moveY = (int) event.getY();
			if (Math.abs(moveY - startY) > 5 && isH == false) {
				isV = true ;
			}

			if ((moveX - startX ) > 5 && isV == false ) {
				rightMove.scrollTo((startX - moveX)  / 2, 0);
				isH = true ;
			}
			
			break;
		case MotionEvent.ACTION_UP:
			restoreRightMove((int )(event.getX() - startX));
			isV = false ;
			isH = false ;
			break;
		}
		return  true;
	}
	
	
	private boolean isV = false ;
	private boolean  isH  = false ;
	
	
	private void restoreRightMove(int event){
		if (Math.abs(rightMove.getScrollX()) > wight /  5) {
			rightMove.startMove(rightMove.getScrollX() , 0 , -270 - rightMove.getScrollX(), 0);
			boundaryX = wight / 5 * 3 ;
			rightIsMove = true ;
		}else {
			restoreX(event);
		}
	}
	
	private void restoreX(int event){
		rightIsMove = false ;
		rightMove.startMove( rightMove.getScrollX() , 0, -rightMove.getScrollX(), 0);
		boundaryX = wight /DEFULT_SCALE;
	}
	
	
	private boolean rightIsMove = false ;
	
	
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
