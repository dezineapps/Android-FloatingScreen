package com.axe1lyze.floatingscreen.app;

import com.axe1lyze.floatingscreen.widget.FloatingScreenView;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public abstract class FloatingScreenService extends Service{
	
	public abstract FloatingScreenView getContentView();
	
	
	@Override
	public void onCreate() {
		startForeground(1, new Notification());
		getContentView().attachToScreen();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
}

