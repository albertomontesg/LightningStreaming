package com.lightningstreaming.activity;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.lightningstreaming.R;
import com.lightningstreaming.main.MainActivity;
import com.lightningstreaming.regex.Regex;

public class VideoListActivity extends ListActivity {

	private ArrayList<String> support=new ArrayList<String>();
	private ListView listView;
	
	@SuppressWarnings("deprecation")
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
	    
	    for (int i=0;i<values1.length; i++){
	    	values[i]=values1[i]+"\n"+values2[i];
	    	support.add(values2[i]);
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	    
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
        // Do something when a list item is clicked
		// String item = (String) getListAdapter().getItem(position);
	    
		// Check the connectivity available to play or not the video depending on the settings
		ConnectivityManager cm = (ConnectivityManager)this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = false, isWiFi = false;
		if (activeNetwork != null) {
			isConnected = activeNetwork.isConnectedOrConnecting();
			isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
		}
		//if (isConnected && )
		String name = Regex.extractFileName(support.get(position));
		String path = Environment.getExternalStorageDirectory().toString()+getString(R.string.app_path)+name;
		String url= support.get(position);
	    Bundle b=new Bundle();
		b.putString("url", url);
		b.putString("UrlPlaylist", path);
	    Intent newi = new Intent(VideoListActivity.this, VideoActivity.class);
		newi.putExtras(b);
		startActivity(newi);
	}

}
