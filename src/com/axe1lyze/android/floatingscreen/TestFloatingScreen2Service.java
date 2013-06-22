package com.axe1lyze.android.floatingscreen;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.axe1lyze.android.app.FloatingScreenService;

public class TestFloatingScreen2Service extends FloatingScreenService {

	@Override
	public FloatingScreenService getSelf(Intent intent) {
		return this;
	}
	
	@Override
	public int getInitialWidth() {
		return 500;
	}

	@Override
	public int getInitialHeight() {
		return 500;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();
		
		setContentView(new EditText(this));
	}


}
