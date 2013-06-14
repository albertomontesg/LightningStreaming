package com.lightningstreaming.activity;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lightningstreaming.R;
import com.lightningstreaming.exceptions.ParsingException;
import com.lightningstreaming.main.MainActivity;
import com.lightningstreaming.playlist.MasterPlaylist;
import com.lightningstreaming.regex.Regex;
import com.yixia.zi.utils.StringHelper;
import com.yixia.zi.utils.ToastHelper;

public class VideoListActivity extends ListActivity {

	private ArrayList<String> support=new ArrayList<String>();
	private ListView listView;
	
	@SuppressWarnings({ "deprecation" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = getListView();
		Bundle bundle = getIntent().getExtras();
		ArrayList<String> list=bundle.getStringArrayList("names");
	    ArrayList<String> urls=bundle.getStringArrayList("urls");
	    
	    String [] values1 = list.toArray(new String[list.size()]);
	    String [] values2 = urls.toArray(new String[urls.size()]);
	    String [] values = new String[values1.length];
	    String [] info = new String[values1.length];
	    
	    if (values.length == 0) {
	    	TextView text = new TextView(getBaseContext());
	    	text.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	    	text.setTextSize(40);
	    	text.setTextColor(getResources().getColor(R.color.black));
	    	text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
	    	text.setText("0 Videos");
	    	text.setVisibility(View.GONE);
	    	((ViewGroup) listView.getParent()).addView(text);
			listView.setEmptyView(text);
	    }
	    final List<String[]> listVideos = new LinkedList<String[]>();
	    
	    for (int i=0;i<values1.length; i++){
	    	File f = new File(Environment.getExternalStorageDirectory().toString()+getString(R.string.app_path)+values1[i]+".m3u8");
	    	values[i]=values1[i];
	    	support.add(values2[i]);
	    	info[i]=getInfo(f, i);
	    	
	        listVideos.add(new String[] { values[i], info[i] });
	    }
	    
	    setListAdapter(new ArrayAdapter<String[]>(
	            this,
	            android.R.layout.simple_list_item_2,
	            android.R.id.text1,
	            listVideos) {
	            	 
	                @Override
	                public View getView(int position, View convertView, ViewGroup parent) {
	                	
	                    View view = super.getView(position, convertView, parent);
	                    
	                    String[] entry = listVideos.get(position);
	                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
	                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
	                    text1.setText(entry[0]);
	                    text2.setText(entry[1]);
	                    return view;
	                }
	            });
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i1 = new Intent(this, SettingActivity.class);
			i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i1);
			return true;
		case R.id.action_refresh:
			Intent i2 = new Intent(this, MainActivity.class);
			i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i2);
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
	}

	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
		
		// Check the connectivity available to play or not the video depending on the settings
		ConnectivityManager cm = (ConnectivityManager)this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isConnected = false;
		if (mWifi != null) {
			isConnected = mWifi.isConnected();
		}
		if (!isConnected) ToastHelper.showToast(this, Toast.LENGTH_LONG, R.string.not_internet_connection);
		else {
			String name = Regex.extractFileName(support.get(position));
			String path = Environment.getExternalStorageDirectory().toString()+getString(R.string.app_path)+name;
			String url= support.get(position);
		    
		    Intent i = new Intent(getApplicationContext(), VideoActivity.class);
			i.setData(Uri.parse(path));
			i.putExtra("UrlPlaylist", url);
			startActivity(i);
		}
	}
	
	private String getInfo(File index, int i) {
		String info = getString(R.string.duration) + ": ";
		MasterPlaylist playlist = null;
		try {
			playlist = MasterPlaylist.parse(index, new URL(support.get(i)));
			info = info + StringHelper.generateTime((long)playlist.getTotalDuration()*1000);
			if (!playlist.isOnlySegmentPlaylist()) info = info + "        " + getString(R.string.qualities_available) + ": " + playlist.getNumStreams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return info;
	}

}
