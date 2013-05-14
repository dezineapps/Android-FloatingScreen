package com.axe1lyze.floatingscreen;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.axe1lyze.floatingscreen.app.FloatingScreenService;

public class TestFloatingScreen2Service extends FloatingScreenService {

	@Override
	public FloatingScreenService getSelf(Intent intent) {
		return this;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();
		
		setContentView(new EditText(this));
	}


}
