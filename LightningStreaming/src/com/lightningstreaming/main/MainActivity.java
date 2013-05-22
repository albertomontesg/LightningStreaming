package com.lightningstreaming.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.lightningstreaming.R;
import com.lightningstreaming.activity.VideoListActivity;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			wait(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Intent i = new Intent(this, VideoListActivity.class);
		startActivity(i);
		
		// Connectiong to the server and parse the html
		
		// change the textview content to (getting_index) and download the m3u8 of all the videos
		
		// Then pass the information to the video list activity.
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
	}
}