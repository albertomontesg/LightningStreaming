package com.lightningstreaming.activity;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lightningstreaming.R; 
import com.lightningstreaming.playlist.MasterPlaylist;

public class PlayerActivity extends Activity {

	VideoView mVideoView;
	MediaController mMediaController;
	
	MasterPlaylist mPlaylist;
	URL mUrl;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		mVideoView = (VideoView) findViewById(R.id.video);
		mMediaController = new MediaController(this);
		mVideoView.setMediaController(mMediaController);
		// manage the mediaPlayer...
		//passar un FileDescriptor form an outputStream
		
		Intent i = getIntent();
		setPlaylist(i);
		
		/*
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		mediaPlayer.setDataSource(inputStream.getFD());
		inputStream.close();
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}
	
	public void setPlaylist(Intent i) {
		String name = i.getStringExtra("displayName");
		try {
			mUrl = new URL(i.getStringExtra("UrlPlaylist"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String path = i.getDataString();
		File file = new File(path);
		mPlaylist = MasterPlaylist.parse(file, mUrl);
		mPlaylist.setName(name);
	}

}
