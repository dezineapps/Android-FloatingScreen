package com.axe1lyze.android.floatingscreen;

import com.axe1lyze.android.app.FloatingScreenService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//startService(new Intent(this,TestFloatingScreenService.class));
		startService(new Intent(this,TestFloatingScreen2Service.class));
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
