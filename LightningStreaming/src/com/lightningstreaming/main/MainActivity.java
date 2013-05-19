package com.lightningstreaming.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.lightningstreaming.R;
import com.lightningstreaming.activity.VideoActivity;
import com.lightningstreaming.asynctask.DownloadPlaylist;
import com.lightningstreaming.asynctask.DownloadSegments;
import com.lightningstreaming.exceptions.CouldNotDownloadFilesException;
import com.lightningstreaming.playlist.MasterPlaylist;

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
		Button bDownload = (Button) this.findViewById(R.id.button4);
		bSegments.setOnClickListener(buttonA);
		bMaster.setOnClickListener(buttonB);
		
		bPlay.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				//String url = "file:/mnt/sdcard/LightningStreaming/Segmentos/fileSequenceTotal.ts";
				String url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
				//String url = "http://meta.video.qiyi.com/255/dfbdc129b8d18e10d6c593ed44fa6df9.m3u8";
				//String url = "http://3glivehntv.doplive.com.cn/video1/index_128k.m3u8";
				//String url = "file:/mnt/sdcard/LightningStreaming/Segmentos/fileSequence0.ts";
				String nameVideo = "Marco";
				Intent i = new Intent(getApplicationContext(), VideoActivity.class);
				i.setData(Uri.parse(url));
				i.putExtra("displayName", nameVideo);
				i.putExtra("UrlPlaylist", url);

				
				startActivity(i);
			}
			
		});
		
		bDownload.setOnClickListener( new OnClickListener() {
		public void onClick(View v){
			String path = getResources().getString(R.string.app_path);
			DownloadSegments d = new DownloadSegments();
			d.execute(path);
			try {
				d.get();
				Button b = (Button) findViewById(R.id.button4);
				b.setText(getResources().getString(R.string.segements_downloaded));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
		
		@SuppressWarnings("unchecked")
		@Override
		protected Object doInBackground(Object... params) {
			//List<URL> urls = new ArrayList<URL>();
			//List<URL> dirs = new ArrayList<URL>();
			
			
			
			URL url = null;
			URL dir = null;
			String input = (String) params[0];
			
			try {
				if (input.contentEquals("M")) {
					url = new URL("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8");
					dir = new URL("file", null, getResources().getString(R.string.app_path)+"MasterPlaylist/");
				} else {
					url = new URL("http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8");
					dir = new URL("file", null, getResources().getString(R.string.app_path)+"SegmentPlaylist/");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			DownloadPlaylist d = new DownloadPlaylist();
			d.execute(url,dir);
			MasterPlaylist playlist = null;
			List<File> files = null;
			try {
				files = (List<File>) d.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (files != null) {
				try {
					playlist = MasterPlaylist.parse(files.get(0), url);
					playlist.setName("hola");
				} catch (CouldNotDownloadFilesException e) {
					e.printStackTrace();
				}
			}
			
			return input;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

