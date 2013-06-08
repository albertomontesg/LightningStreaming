package com.lightningstreaming.main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lightningstreaming.R;
import com.lightningstreaming.activity.VideoListActivity;

public class MainActivity extends Activity implements OnClickListener {

	private ProgressBar _progressBar = null;
	private TextView _textView = null;
	private ArrayList<String> names;
	private ArrayList<String> urls;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		_progressBar.setMax(100);
		_textView = (TextView) findViewById(R.id.textView2);
		names = new ArrayList<String>();
		urls = new ArrayList<String>();
		
		DownloadIndex downloadIndex= new DownloadIndex();
		downloadIndex.execute((Object[])null);
		
		File dir = new File(Environment.getExternalStorageDirectory().toString()+getString(R.string.app_path));
		if (!dir.exists()) dir.mkdir();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		_progressBar=null;
		_textView = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	
	class DownloadIndex extends AsyncTask<Object, Integer, Integer>{
		
		protected void onPostExecute(Integer result){
			Intent i = new Intent(MainActivity.this, VideoListActivity.class);
			//passar result
			if (result < 1) {
				Context context = getApplicationContext();
				String text = null;
				if (result == 0) text = "No videos at the server!";
				else if(result == -1) text = "Server Unavailable!";
				Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				toast.show();
			}
			Bundle b=new Bundle();
			b.putStringArrayList("names", (ArrayList<String>) names);
			b.putStringArrayList("urls", (ArrayList<String>) urls);
			i.putExtras(b);
			startActivity(i);
		 }
		
		@Override
		protected Integer doInBackground(Object... arg0) {
			Connection c=null;
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			String u = sharedPrefs.getString("pref_ip", null);
			try {
				URL url = new URL(u);	//Server at university: 192.168.1.146/old_web/upload/
				c=new Connection(url);
				c.Connect();
			}catch(Exception e) {
				e.printStackTrace();
				return -1;
			}
			
			this.publishProgress(10);
			
			// download the m3u8 file for each video
			//int l = c.name.size();
			
			
			if (c.name.size() == 0) {
				return 0;
			} else {
				names=c.name;
				urls=c.urls;
			    return 1;
			}
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
