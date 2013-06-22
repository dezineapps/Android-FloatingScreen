package com.axe1lyze.android.floatingscreen;

import android.content.Intent;
import android.widget.Button;

import com.axe1lyze.android.app.FloatingScreenService;

public class TestFloatingScreenService extends FloatingScreenService {

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
	

}
