package com.lightningstreaming.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lightningstreaming.R;
import com.lightningstreaming.activity.SettingActivity;
import com.lightningstreaming.activity.VideoListActivity;

public class MainActivity extends Activity implements OnClickListener {

	private ProgressBar _progressBar = null;
	private TextView _textView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		_progressBar.setMax(100);
		_textView = (TextView) findViewById(R.id.textView2);
		
		
		DownloadIndex downloadIndex= new DownloadIndex();
		downloadIndex.execute((Object[])null);
		
		// change the textview content to (getting_index) and download the m3u8 of all the videos
		
		// Then pass the information to the video list activity.
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		_progressBar=null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, SettingActivity.class));
			return true;
		}
		
		return false;
	}

	
	class DownloadIndex extends AsyncTask<Object, Integer, Object[]>{
		
		@SuppressWarnings({ "unchecked", "unused" })
		protected void onPostExecute(Object[] result){
			Intent i = new Intent(MainActivity.this, VideoListActivity.class);
			//passar result
			if (result[0].getClass() == Integer.class) {
				Context context = getApplicationContext();
				String text = "Server Unavailable!";
				Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				toast.show();	
			}
			else if (result != null) {
				Bundle b=new Bundle();
				b.putStringArrayList("names", (ArrayList<String>) result[0]);
				b.putStringArrayList("urls", (ArrayList<String>) result[1]);
				i.putExtras(b);
			} else {
				Context context = getApplicationContext();
				String text = "No videos at the server!";
				Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				toast.show();	
			}
			//startActivity(i);
		 }
		
		@Override
		protected Object[] doInBackground(Object... arg0) {
			Connection c=null;
			try {
				URL url = new URL("http://172.16.0.123/pbe/");	//Server at university: 192.168.1.146/old_web/upload/
				c=new Connection(url);
			}catch(IOException e1) {
				e1.printStackTrace();
				Object[] i= {0};
				return i;
			}
			try {
				c.Connect();
			} catch (IOException e2) {
				e2.printStackTrace();
				return null;
			}
			
			this.publishProgress(10);
			
			// download the m3u8 file for each video
			int l = c.name.size();
			
			
			
		
			Object[] arrays=new Object[2];
			arrays[0]=c.name;
			arrays[1]=c.urls;
		    return arrays;
		}
		
		@Override
		protected void onProgressUpdate(Integer...progress) {
			_progressBar.setProgress(progress[0]);
			if (progress[0]==10) {
				_textView.setText(getString(R.string.downloading_indexs));
			}
		}
	}


	@Override
	public void onClick(View v) {
	}
}
