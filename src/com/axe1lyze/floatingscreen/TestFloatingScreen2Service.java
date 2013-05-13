package com.axe1lyze.floatingscreen;

import com.axe1lyze.floatingscreen.app.FloatingScreenService;
import com.axe1lyze.floatingscreen.widget.FloatingScreenView;

public class TestFloatingScreen2Service extends FloatingScreenService {

	@Override
	public FloatingScreenView getContentView() {
		FloatingScreenView view = new FloatingScreenView(this);
		view.setBackgroundColor(0xff00ff00);
		return view ;
	}

}
