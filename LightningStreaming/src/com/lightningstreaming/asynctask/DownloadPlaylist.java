package com.lightningstreaming.asynctask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.lightningstreaming.regex.Regex;

import android.os.AsyncTask;

public class DownloadPlaylist extends AsyncTask<URL, Integer, File>{

	@Override
	protected File doInBackground(URL... urls) {
		URL url = urls[0];
		URL path = (URL) urls[1];
		String fileName = Regex.extractFileName(url.getFile());
		String dir = path.getPath() + fileName;
		
		File file = new File(dir);
		
		try {
			
			
			URLConnection connection = url.openConnection();
            connection.connect();
            // int fileLength = connection.getContentLength();
            
            

            // Download the file
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];

            int count;
            count = input.read(data);
            output.write(data, 0, count);
            //while ((count = input.read(data)) != -1) output.write(data, 0, count);

            output.flush();
            output.close();
            input.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return file;
	}

}
