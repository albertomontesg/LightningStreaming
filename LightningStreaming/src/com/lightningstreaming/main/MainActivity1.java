package com.lightningstreaming.main;

import java.io.IOException;

import android.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity1);
		Tasca t= new Tasca();
	   t.execute((Object[])null);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity1, menu);
		return true;
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
}
