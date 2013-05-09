package com.lightningstreaming.main;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import com.lightningstreaming.R;
import com.lightningstreaming.asynctask.DownloadPlaylist;
import com.lightningstreaming.playlist.MasterPlaylist;




import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button bSegments = (Button) this.findViewById(R.id.button1);
		Button bMaster = (Button) this.findViewById(R.id.button2);
		bSegments.setOnClickListener(buttonA);
		bMaster.setOnClickListener(buttonB);
		
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
			URL url = null;
			URL dir = null;
			try {
				url = new URL("https://devimages.apple.com.edgekey.net/resources/http-streaming/examples/bipbop_4x3/gear1/prog_index.m3u8");
				dir = new URL("file", null, getResources().getString(R.string.app_path));
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
		}
	};
	
	private OnClickListener buttonB = new OnClickListener() {
		public void onClick(View v){
			URL url = null;
			URL dir = null;
			try {
				url = new URL("https://devimages.apple.com.edgekey.net/resources/http-streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8");
				dir = new URL("file", null, getResources().getString(R.string.app_path));
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
		}
	};


	@Override
	public void onClick(View arg0) {
		
	}

}
