package com.lightningstreaming.main;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Tasca t= new Tasca();
		t.execute((Object[])null);
		
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
	
	class Tasca extends AsyncTask<Object, Integer, Object>{
		
		protected void onPostExecute(Object[] result){
			Intent newi = new Intent(MainActivity.this, VideoListActivity.class);
			//passar result
			startActivity(newi);
		 }
		
		@Override
		protected Object doInBackground(Object... arg0) {
			Connection c=null;
		    try {
				c=new Connection(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    try {
				c.Connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    Object[] o=new Object[2];
		    o[0]=c.name;
		    o[1]=c.urls;
		    return o;
		}
	}
}
