package com.lightningstreaming.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.firstpart.Connection;
import com.example.firstpart.Llista;
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
			Intent newi = new Intent(this, Llista.class);
			  //passar result
			  Bundle b=new Bundle();
			  b.putStringArrayList("names", (ArrayList<String>) result[0]);
			  b.putStringArrayList("urls", (ArrayList<String>) result[1]);
			   startActivity(newi);
		 }
		
		@Override
		protected Object doInBackground(Object... arg0) {
			Connection c=null;
			try {
				c=new Connection(new URL("192.168.1.146/old_web/upload/"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    
			try {
				c.Connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	   Object [] arrays=new Object[2];
	    arrays[0]=c.name;
	    arrays[1]=c.urls;
	    return arrays;
		}
	}
}
