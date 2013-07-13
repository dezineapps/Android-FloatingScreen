package com.axe1lyze.android.app;

import com.axe1lyze.android.widget.FloatingScreenView;

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
		//floatingScreenView.setDrawingCacheEnabled(false);
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


