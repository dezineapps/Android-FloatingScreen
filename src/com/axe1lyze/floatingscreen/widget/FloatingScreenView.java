package com.axe1lyze.floatingscreen.widget;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class FloatingScreenView extends LinearLayout{
	private static FloatingScreenView frontFloatingScreenView = null;
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private double statusBarHeight;
	public WindowManager.LayoutParams getWindowLayoutParams(){
		return params;
	}
	
	public FloatingScreenView(Context context) {
		this(context,getFloatingScreenLayoutParams());
	}
	
	public FloatingScreenView(Context context,WindowManager.LayoutParams params) {
		super(context);
		this.params = params;
		this.windowManager = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);
		statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
	}
	
	public void attachToScreen(){
		windowManager.addView(this, params);
	}
	
	public void detachFromScreen(){
		windowManager.removeView(this);
	}
	
	private float startX=0,startY=0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:

			startX = event.getX();
			startY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			params.x = (int) ((event.getRawX())-startX);
			params.y = (int) (((event.getRawY())-startY)-statusBarHeight);
			windowManager.updateViewLayout(this, params);
			return true;
		case MotionEvent.ACTION_UP:
			if(!equals(frontFloatingScreenView)){
				windowManager.removeView(this);
				windowManager.addView(this, params);
				frontFloatingScreenView=this;
			}
			break;
		case MotionEvent.ACTION_OUTSIDE:
			break;
		default:break;
		}
		return false;
	}
	
	public static WindowManager.LayoutParams getFloatingScreenLayoutParams(){
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.flags = 	WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
						WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH|
						WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS|
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.gravity = Gravity.TOP|Gravity.LEFT;
		params.width=params.height=500;//.LayoutParams.WRAP_CONTENT;
		return params;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}


class FloatingScreenTitleView extends LinearLayout{

	public FloatingScreenTitleView(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
}

class FloatingScreenContentLayout extends FrameLayout{

	public FloatingScreenContentLayout(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
}