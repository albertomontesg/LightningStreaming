package com.lightningstreaming.activity;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lightningstreaming.R;
import com.lightningstreaming.main.MainActivity;

public class VideoListActivity extends ListActivity {

	private ArrayList<String> support=new ArrayList<String>();
	//private ListView listView;
	private TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//listView = getListView();
		Bundle bundle = getIntent().getExtras();
		ArrayList<String> list=bundle.getStringArrayList("names");
	    ArrayList<String> urls=bundle.getStringArrayList("urls");
	    
	    XmlPullParser parser = getResources().getXml(R.xml.text);
	    AttributeSet attributes = Xml.asAttributeSet(parser);
	    text = new TextView(this, attributes);
	    
	    String [] values1 = list.toArray(new String[list.size()]);
	    String [] values2 = urls.toArray(new String[urls.size()]);
	    String [] values = new String[values1.length];
	    
	    if (values.length == 0) {
	    	text.setVisibility(View.VISIBLE);
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
			startActivity(new Intent(this, SettingActivity.class));
			return true;
		case R.id.action_refresh:
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
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
	    String url= support.get(position);
	    Bundle b=new Bundle();
		b.putString("url", url);
	    Intent newi = new Intent(VideoListActivity.this, VideoActivity.class);
		newi.putExtras(b);
		//Toast.makeText(this, "URL: "+ url, Toast.LENGTH_LONG).show();
		startActivity(newi);
	}

}
