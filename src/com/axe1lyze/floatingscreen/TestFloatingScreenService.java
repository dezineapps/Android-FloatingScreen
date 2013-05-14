package com.axe1lyze.floatingscreen;

import android.content.Intent;
import android.widget.Button;

import com.axe1lyze.floatingscreen.app.FloatingScreenService;

public class TestFloatingScreenService extends FloatingScreenService {

	@Override
	public FloatingScreenService getSelf(Intent intent) {
		return this;
	}
	

}
