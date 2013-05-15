package com.lightningstreaming.asynctask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.lightningstreaming.regex.Regex;

import android.os.AsyncTask;

public class DownloadSegments extends AsyncTask<Object, Object, Object> {

	@Override
	protected Object doInBackground(Object... arg0) {
		List<String> urls = new ArrayList<String>();
		String path = (String) arg0[0];
		String dir = path+"Segmentos/";
		File directory = new File(dir);
		directory.mkdir();
		
		for (int i = 0; i < 5; i++) {
			urls.add("https://devimages.apple.com.edgekey.net/resources/http-streaming/examples/bipbop_4x3/gear1/fileSequence"+i+".ts");
		}
		
		for (int i = 0; i < urls.size(); i++) {
			URL url = null;
			try {
				url = new URL(urls.get(i));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String d = dir + Regex.extractFileName(url.toString());
			
			File file = new File(d);
			
			try {
				
				
				URLConnection connection = url.openConnection();
	            connection.connect();
	            // int fileLength = connection.getContentLength();
	            
	            

	            // Download the file
	            InputStream input = new BufferedInputStream(url.openStream());
	            OutputStream output = new FileOutputStream(file.getPath());

	            byte data[] = new byte[1024];
	            
	            int count;
	            //count = input.read(data);
	            //output.write(data, 0, count);
	            while ((count = input.read(data)) != -1) output.write(data, 0, count);

	            output.flush();
	            output.close();
	            input.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			
			
		}
		
		return null;
	}

}
