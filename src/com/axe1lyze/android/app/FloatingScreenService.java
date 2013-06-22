package com.axe1lyze.android.app;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public abstract class FloatingScreenService extends Service{
	public abstract FloatingScreenService getSelf(Intent intent);
	public abstract int getInitialWidth();
	public abstract int getInitialHeight();
	
	@Override
	public IBinder onBind(Intent intent) {return new FloatingScreenServiceBinder(getSelf(intent));}
	
	private FloatingScreenView floatingScreenView;
	private static LayoutInflater inflater;
	
	
	
	@Override
	public void onCreate() {
		startForeground(1, new Notification());
		if(inflater==null) inflater = (LayoutInflater)getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		floatingScreenView = new FloatingScreenView(this,getInitialWidth(),getInitialHeight());
		floatingScreenView.setDrawingCacheEnabled(false);
		floatingScreenView.attachToScreen();
	}
	
	
	
	public void setContentView(int resourceId){setContentView(inflater.inflate(resourceId, null));}
	public void setContentView(View view){
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		floatingScreenView.addContentView(view);
	}
	
	
	public static class FloatingScreenServiceBinder extends Binder{
		private FloatingScreenService service;
		public FloatingScreenServiceBinder(FloatingScreenService service) {this.service=service;}
		public FloatingScreenService getFloatingScreenService(){return service;}
	}
	
	
}


class FloatingScreenView extends LinearLayout{
	
	private static FloatingScreenView frontFloatingScreenView = null;
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private double statusBarHeight;
	
	private FloatingScreenTitleView titleView;
	private View contentView;
	
	public WindowManager.LayoutParams getWindowLayoutParams(){
		return params;
	}
	
	public FloatingScreenView(Context context,int width,int height) {
		this(context,getFloatingScreenLayoutParams(width,height));
	}
	
	public FloatingScreenView(Context context,WindowManager.LayoutParams params) {
		super(context);
		this.params = params;
		this.windowManager = (WindowManager)context.getSystemService(Service.WINDOW_SERVICE);
		this.statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
		setFocusableInTouchMode(true);
		setBackgroundColor(0xff000000);
	}
	
	public void attachToScreen(){
		if(frontFloatingScreenView!=null){
			frontFloatingScreenView.clearFocus();
		}
		windowManager.addView(this, params);
		requestFocus();
		frontFloatingScreenView=this;
	}
	
	public void detachFromScreen(){
		windowManager.removeView(this);
	}
	
	private float startX=0,startY=0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		invalidate();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:

			startX = event.getX();
			startY = event.getY();
		case MotionEvent.ACTION_MOVE:
			params.x = (int) ((event.getRawX())-startX);
			params.y = (int) (((event.getRawY())-startY)-statusBarHeight);
			windowManager.updateViewLayout(this, params);
			return true;
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


class FloatingScreenTitleView extends LinearLayout{

	public FloatingScreenTitleView(Context context) {
		super(context);
	}
	
}

class FloatingScreenContentLayout extends FrameLayout{

	public FloatingScreenContentLayout(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
}

