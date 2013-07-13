package com.axe1lyze.android.widget;

import com.axe1lyze.android.app.FloatingScreenService;
import com.axe1lyze.android.view.MoveGestureDetector;
import com.axe1lyze.android.view.MoveGestureDetector.SimpleOnMoveGestureListener;
import com.axe1lyze.android.floatingscreen.R;

import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class FloatingScreenView extends LinearLayout{

	private static FloatingScreenView frontFloatingScreenView = null;
	
	private WindowManager windowManager;
	
	private LayoutInflater inflater;
	private MoveGestureDetector moveGestureDetector;
	
	private WindowManager.LayoutParams params;
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
		this.inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		this.moveGestureDetector  = new MoveGestureDetector(context,onMoveGestureListener);
		setOrientation(LinearLayout.VERTICAL);
		//setFocusableInTouchMode(true);
		setBackgroundColor(0xffffffff);
		
		LinearLayout floatingLayoutTitleBar = (LinearLayout) inflater.inflate(R.layout.layout_floatingscreentitlebar,null);
		floatingLayoutTitleBar.findViewById(R.id.floatingScreenCloseButton).setOnClickListener(closeButtonClickListener);
		floatingLayoutTitleBar.findViewById(R.id.floatingScreenAppIconImageView).setOnClickListener(appIconImageViewClickListener);
		addView(floatingLayoutTitleBar);
	}
	
	

	private OnClickListener appIconImageViewClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
		}
	};
	
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
	
	private SimpleOnMoveGestureListener onMoveGestureListener = new SimpleOnMoveGestureListener(){
		
		public boolean onMoveBegin(MoveGestureDetector detector) {
			return super.onMoveBegin(detector);
		}
		
		public boolean onMove(MoveGestureDetector detector) {
			params.x = (int) (detector.getMovedPositionFactorX());
			params.y = (int) (detector.getMovedPositionFactorY());
			windowManager.updateViewLayout(FloatingScreenView.this, params);
			return super.onMove(detector);
		}
		
		public void onMoveEnd(MoveGestureDetector detector) {
			if(!equals(frontFloatingScreenView)){
				params.flags ^= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
				detachFromScreen();
				attachToScreen();
			}
		}
		
		public void onMoveOutside(MoveGestureDetector detector) {
			params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			windowManager.updateViewLayout(FloatingScreenView.this, params);
		};
		
	};
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		invalidate();
		boolean retMove = moveGestureDetector.onTouch(event);
		return retMove;
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
