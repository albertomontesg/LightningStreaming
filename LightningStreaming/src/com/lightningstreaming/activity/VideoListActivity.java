package com.lightningstreaming.activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.support.v4.app.*;
import android.support.v4.content.Loader;

import com.lightningstreaming.R;

public class VideoListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_list, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
	}

	@Override
	public void onBackPressed() {
		// TODO Make that when back pressed it goes out of the app and not to the main activity. Main activity only is when start. 
		// Allows to refresh the list inside the app.
	}

}
