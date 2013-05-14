package com.lightningstreaming.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import com.lightningstreaming.R;
import com.lightningstreaming.asynctask.DownloadPlaylist;
import com.lightningstreaming.playlist.MasterPlaylist;

import io.vov.vitamio.activity.VideoActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button bSegments = (Button) this.findViewById(R.id.button1);
		Button bMaster = (Button) this.findViewById(R.id.button2);
		Button bPlay = (Button) this.findViewById(R.id.button3);
		bSegments.setOnClickListener(buttonA);
		bMaster.setOnClickListener(buttonB);
		bPlay.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "https://devimages.apple.com.edgekey.net/resources/http-streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8";
				
				Intent i = new Intent(getApplicationContext(), VideoActivity.class);
				i.setData(Uri.parse(url));
				i.putExtra("displayName", "Prueba");
				startActivity(i);
			}
			
		});
		
		File directory = new File(getResources().getString(R.string.app_path));
		directory.mkdirs();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnClickListener buttonA = new OnClickListener() {
		public void onClick(View v){
			TextView text = (TextView) findViewById(R.id.textView1);
			text.setText("Downloading SegmentPlaylist...");
			
			PressedButton pressedAction = new PressedButton();
			pressedAction.execute("S");
		}
	};
	
	private OnClickListener buttonB = new OnClickListener() {
		public void onClick(View v){
			TextView text = (TextView) findViewById(R.id.textView1);
			text.setText("Downloading MasterPlaylist...");
			
			PressedButton pressedAction = new PressedButton();
			pressedAction.execute("M");
		}
	};

	class PressedButton extends AsyncTask<Object, Object, Object> {

		@Override
		protected void onPostExecute(Object result) {
			TextView text = (TextView) findViewById(R.id.textView1);
			text.append("Done");
		}
		
		@Override
		protected Object doInBackground(Object... params) {
			URL url = null;
			URL dir = null;
			String input = (String) params[0];
			
			try {
				if (input.contentEquals("M")) {
					url = new URL("https://devimages.apple.com.edgekey.net/resources/http-streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8");
					dir = new URL("file", null, getResources().getString(R.string.app_path)+"MasterPlaylist/");
				} else {
					url = new URL("https://devimages.apple.com.edgekey.net/resources/http-streaming/examples/bipbop_4x3/gear1/prog_index.m3u8");
					dir = new URL("file", null, getResources().getString(R.string.app_path)+"SegmentPlaylist/");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			DownloadPlaylist d = new DownloadPlaylist();
			d.execute(url,dir);
			MasterPlaylist playlist = null;
			try {
				playlist = MasterPlaylist.parse(d.get(), url);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			playlist.setName("hola");
			
			
			return input;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

