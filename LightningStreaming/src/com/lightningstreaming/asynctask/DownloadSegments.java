package com.lightningstreaming.asynctask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class DownloadSegments extends AsyncTask<Object, Object, Object> {

	@SuppressWarnings({ "resource", "unused" })
	@Override
	protected Object doInBackground(Object... arg0) {
		List<String> urls = new ArrayList<String>();
		String path = (String) arg0[0];
		String dir = path+"Segmentos/";
		File directory = new File(dir);
		directory.mkdir();
		
		for (int i = 0; i < 3; i++) {
			urls.add("http://devimages.apple.com/iphone/samples/bipbop/gear1/fileSequence"+i+".ts");
		}
		for (int i = 3; i < 6; i++) {
			urls.add("http://devimages.apple.com/iphone/samples/bipbop/gear4/fileSequence"+i+".ts");
		}
		
		String d = dir + "fileSequenceTotal.ts";
		
		File file = new File(d);
		
        FileOutputStream output = null;
		try {
			output = new FileOutputStream(file.getPath());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		int totalBytesRead = 0, incrementalBytesRead = 0;
		for (int i = 0; i < urls.size(); i++) {
			URL url = null;
			try {
				url = new URL(urls.get(i));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			
			try {
				
				
				URLConnection connection = url.openConnection();
	            connection.connect();
	            // int fileLength = connection.getContentLength();
	            
	            

	            // Download the file
	            InputStream input = new BufferedInputStream(url.openStream());

	            byte data[] = new byte[1024];
	            
	            do {
	                int numread = input.read(data);   
	                if (numread <= 0)   
	                    break; 
	                output.write(data, 0, numread);
	                totalBytesRead += numread;
	                incrementalBytesRead += numread;
	            } while(true);
	            
	            input.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			
			
		}
		
		return null;
	}

}
