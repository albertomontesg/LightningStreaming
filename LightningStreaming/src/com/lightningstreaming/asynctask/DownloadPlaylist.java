package com.lightningstreaming.asynctask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.lightningstreaming.regex.Regex;

import android.os.AsyncTask;

<<<<<<< HEAD
/**
 * AsyncTask that Downloads a Playlist from an URL and save it on a specific directory
 * @author Alberto Montes
 *
 */
public class DownloadPlaylist extends AsyncTask<URL, Integer, File>{
=======
public class DownloadPlaylist extends AsyncTask<Object, Integer, Object>{
>>>>>>> player

	@SuppressWarnings("unchecked")
	@Override
<<<<<<< HEAD
	protected File doInBackground(URL... urls) {

		URL url = urls[0];
		URL path = (URL) urls[1];
		String fileName = Regex.extractFileName(url.getFile());
		String dir = path.getPath() + fileName;
=======
	protected Object doInBackground(Object... params) {
		List<URL> urls = new ArrayList<URL>();
		List<URL> paths = new ArrayList<URL>();
		boolean successDownload = true;
>>>>>>> player
		
		if (params[0] instanceof ArrayList && params[1] instanceof ArrayList) {
			urls = (List<URL>) params[0];
			paths = (List<URL>) params[1];
		}
		else if (params[0].getClass().equals(URL.class) && params[1].getClass().equals(URL.class)) {
			urls.add((URL) params[0]);
			paths.add((URL) params[1]);
		} else return null;
		
		List<File> files = new ArrayList<File>();
		for (int i = 0; i < urls.size(); i++) {	
			URL url = urls.get(i);
			URL p = paths.get(i);
			
			String path = Regex.getDirectory(p.getPath());
			String fileName = Regex.extractFileName(url.toString());
			
<<<<<<< HEAD
			URLConnection connection = url.openConnection();
            connection.connect();
            // int fileLength = connection.getContentLength();

=======
			File directory = new File(path);
			directory.mkdir();
			File file = new File(path+fileName);
			
			if (!file.exists())
				successDownload = downloadFile(url, file);
			files.add(file);
			
		}
		if (!successDownload) return null;
		else return files;
	}

	@SuppressWarnings("unused")
	private boolean downloadFile(URL url, File outputFile) {
		try {
			
			URLConnection connection = url.openConnection();
            connection.connect();
            
>>>>>>> player
            // Download the file
            InputStream input = new BufferedInputStream(url.openStream());
            FileOutputStream output = new FileOutputStream(outputFile.getPath());
            
            byte data[] = new byte[1024];
<<<<<<< HEAD

            int count;
            count = input.read(data);
            output.write(data, 0, count);

=======
            
            int totalBytesRead = 0, incrementalBytesRead = 0;
            do {
                int numread = input.read(data);   
                if (numread <= 0)   
                    break; 
                output.write(data, 0, numread);
                totalBytesRead += numread;
                incrementalBytesRead += numread;
            } while(true);
            
            input.close();
>>>>>>> player
            output.flush();
            output.close();
            
            return true;
            
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
<<<<<<< HEAD
		
		return file;

=======
>>>>>>> player
	}
	
}
