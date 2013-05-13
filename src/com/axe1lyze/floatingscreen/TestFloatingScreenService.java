package com.axe1lyze.floatingscreen;

import com.axe1lyze.floatingscreen.app.FloatingScreenService;
import com.axe1lyze.floatingscreen.widget.FloatingScreenView;

public class TestFloatingScreenService extends FloatingScreenService {

	@Override
	public FloatingScreenView getContentView() {
		return new FloatingScreenView(this) ;
	}

}
