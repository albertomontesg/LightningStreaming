package com.lightningstreaming.main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import com.lightningstreaming.R;
import com.lightningstreaming.asynctask.DownloadPlaylist;
import com.lightningstreaming.asynctask.DownloadSegments;
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
		Tasca t= new Tasca();
		   t.execute((Object[])null);
		/*
		Button bSegments = (Button) this.findViewById(R.id.button1);
		Button bMaster = (Button) this.findViewById(R.id.button2);
		Button bPlay = (Button) this.findViewById(R.id.button3);
		Button bDownload = (Button) this.findViewById(R.id.button4);
		bSegments.setOnClickListener(buttonA);
		bMaster.setOnClickListener(buttonB);
		bDownload.setOnClickListener(buttonC);
		bPlay.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "https://devimages.apple.com.edgekey.net/resources/http-streaming/examples/bipbop_4x3/gear1/prog_index.m3u8";
				//String url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
				//String url = "http://meta.video.qiyi.com/255/dfbdc129b8d18e10d6c593ed44fa6df9.m3u8";
				//String url = "http://3glivehntv.doplive.com.cn/video1/index_128k.m3u8";
				//String url = "file:/mnt/sdcard/LightningStreaming/Segmentos/fileSequence0.ts";
				
				Intent i = new Intent(getApplicationContext(), VideoActivity.class);
				i.setData(Uri.parse(url));
				i.putExtra("displayName", "Prueba");
				startActivity(i);
			}
			
		});
		
		File directory = new File(getResources().getString(R.string.app_path));
		directory.mkdirs();
		*/
	}
	
	class Tasca extends AsyncTask{
		  protected void onPostExecute(Object[] result){
			  Intent newi = new Intent(this, Principal.class);
			  //passar result
			   startActivity(newi);
		   }
		  protected Object doInBackground(Object... arg0) {
			  Connection c=null;
		    try {
				c=new Connection(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				c.Connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Object[] o=new Object[2];
		    o[0]=c.name;
		    o[1]=c.urls;
		    return o;
		   }
		  }

	
	/*

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
		
	}
	
	
	
	
	
	
	private OnClickListener buttonC = new OnClickListener() {
		public void onClick(View v){
			String path = getResources().getString(R.string.app_path);
			TextView textView = (TextView) findViewById(R.id.textView4);
			DownloadSegments d = new DownloadSegments();
			d.execute(path);
			try {
				d.get();
				textView.setText(getResources().getString(R.string.segments_download));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	};
	
	
	
	*/
}

