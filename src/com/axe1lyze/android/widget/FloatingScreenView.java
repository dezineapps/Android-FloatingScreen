package com.axe1lyze.android.widget;

import com.axe1lyze.android.app.FloatingScreenService;
import com.axe1lyze.android.floatingscreen.R;

import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class FloatingScreenView extends LinearLayout{

	private LayoutInflater inflater;
	
	private static FloatingScreenView frontFloatingScreenView = null;
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private double statusBarHeight;
	
	private FloatingScreenTitleView titleView;
	private View contentView;
	
	public WindowManager.LayoutParams getWindowLayoutParams(){
		return params;
	}
	
	public FloatingScreenView(FloatingScreenService context,int width,int height) {
		this(context,getFloatingScreenLayoutParams(width,height));
	}
	
	public FloatingScreenView(FloatingScreenService context,WindowManager.LayoutParams params) {
		super(context);
		this.params = params;
		this.windowManager = (WindowManager)context.getSystemService(Service.WINDOW_SERVICE);
		this.statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
		setOrientation(LinearLayout.VERTICAL);
		//setFocusableInTouchMode(true);
		setBackgroundColor(0xffffffff);
		inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		
		LinearLayout floatingLayoutTitleBar = (LinearLayout) inflater.inflate(R.layout.layout_floatingscreentitle,null);
		floatingLayoutTitleBar.findViewById(R.id.floatingScreenCloseButton).setOnClickListener(closeButtonClickListener);
		addView(floatingLayoutTitleBar);
		
	}
	
	private OnClickListener closeButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			detachFromScreen();
			((FloatingScreenService)getContext()).stopSelf();
		}
	};
	
	public void attachToScreen(){
		if(frontFloatingScreenView!=null){
			frontFloatingScreenView.clearFocus();
		}
		windowManager.addView(this, params);
		requestFocus();
		frontFloatingScreenView=this;
	}
	
	public void detachFromScreen(){
		windowManager.removeViewImmediate(this);
	}
	
	private float startX=0,startY=0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		invalidate();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			return false;
		case MotionEvent.ACTION_MOVE:
			params.x = (int) ((event.getRawX())-startX);
			params.y = (int) (((event.getRawY())-startY)-statusBarHeight);
			windowManager.updateViewLayout(this, params);
			return false;
		case MotionEvent.ACTION_UP:
			if(!equals(frontFloatingScreenView)){
				params.flags ^= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
				detachFromScreen();
				attachToScreen();
			
			}
			break;
		case MotionEvent.ACTION_OUTSIDE:
			params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			windowManager.updateViewLayout(this, params);
			break;
		default:break;
		}

		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		invalidate();
		return super.onKeyDown(keyCode, event);
	}
	
	
	public static WindowManager.LayoutParams getFloatingScreenLayoutParams(int width,int height){
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.format = PixelFormat.TRANSPARENT;
		params.flags = 	WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
						WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH|
						WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.gravity = Gravity.TOP|Gravity.LEFT;
		params.width = width;
		params.height = height;//.LayoutParams.WRAP_CONTENT;
		return params;
	}


	public void addContentView(View view) {
		addView(view);
	}
	
}


class FloatingScreenContentLayout extends FrameLayout{

	public FloatingScreenContentLayout(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
}


