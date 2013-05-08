package com.lightningstreaming.playlist;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.lightningstreaming.asynctask.DownloadPlaylist;
import com.lightningstreaming.regex.Regex;

public class MasterPlaylist {

	private float totalDuration;
	private int numSegments;
	

	private String name;
	private URI path;
	private URL url;
	private int numStreams;
	
	private TreeMap<Integer, SegmentPlaylist> streams;
	private List<Integer> qualities;
	
	//private int currentSegment;
	
	public MasterPlaylist (String name, URI path, URL url, TreeMap<Integer, SegmentPlaylist> streams) {
		this.setName(name);
		this.setPath(path);
		this.setUrl(url);
		this.setStreams(streams);
		this.setNumStreams(streams.size());
		List<Integer> keys = new ArrayList<Integer>(streams.keySet());
		this.setQualities(keys);
		this.setTotalDuration(streams.get(0).getTotalDuration());
	}

	public static MasterPlaylist parse(File file, URL url) {
		
		String data = Regex.fileToString(file);
		
		String n = file.getName();
		URI p = null;
		try {
			p = new URI(file.getAbsolutePath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		TreeMap <Integer, SegmentPlaylist> s = new TreeMap<Integer, SegmentPlaylist>();
		
		SegmentPlaylist sp = null;
		
		if (Regex.count(data, "EXTINF") > 0) {
			sp = SegmentPlaylist.parse(file, url);
			s.put(0,sp);
		}
		else if (Regex.count(data, "EXT-X-STREAM") > 0) {
			Vector<String> str = new Vector<String>(Arrays.asList(data.split("#EXTINF")));
			str.remove(0);
			DownloadPlaylist downloadStreamIndex = new DownloadPlaylist();
			
			
			for (int i = 0; i < str.size(); i++) {
				int bandwidth = Integer.parseInt(Regex.extractString(data, "BANDWIDTH=", ","));
				URL urlStream = null;
				String stream = Regex.extractString(str.get(i), "\n", "\n");
				try {
					if (Regex.count(str.get(i), "www") == 0) {
						urlStream = new URL(url.toString().replace(n, stream));
					}
					else urlStream = new URL(stream);
					
					String streamPath = file.getPath().replace(file.getName(), urlStream.getFile());
							
					downloadStreamIndex.execute(urlStream, new URL("file", null, streamPath));
					sp = SegmentPlaylist.parse(downloadStreamIndex.get(), urlStream);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				s.put(bandwidth, sp);
			}
		}
		else {
			
		}
		
		/*
		
		int numSegments = Regex.count(data, "#EXTINF:");
		if (numSegments > 0) {
			Vector<String> seg = new Vector<String>(Arrays.asList(data.split("#EXTINF")));
			seg.remove(0);
			for (int i = 0; i < numSegments; i++) {
				float dur = Float.parseFloat(Regex.extractString(seg.get(i), ":", ","));
				URL urlSegment = null;
				URI rPath = null;
				//String u;
				try {
					
					urlSegment = new URL(Regex.extractString(seg.get(i), "\n", "\n"));
					//u = Regex.extractString(seg.get(i), "\n", "\n");
					
					if (Regex.count(urlSegment.toString(), "/") > 0) 
						rPath = url.toURI().relativize(urlSegment.toURI());
					else
						rPath = new URI(urlSegment.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				s.add(new Segment (i, dur, rPath, urlSegment));
			}
		}
		*/
		
		
		MasterPlaylist playlist = new MasterPlaylist(n, p, url, s);
		return playlist;
	}

	public float getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(float totalDuration) {
		this.totalDuration = totalDuration;
	}

	public int getNumSegments() {
		return numSegments;
	}

	public void setNumSegments(int numSegments) {
		this.numSegments = numSegments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URI getPath() {
		return path;
	}

	public void setPath(URI path) {
		this.path = path;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public TreeMap<Integer, SegmentPlaylist> getStreams() {
		return streams;
	}



	public void setStreams(TreeMap<Integer, SegmentPlaylist> streams) {
		this.streams = streams;
	}

	public int getNumStreams() {
		return numStreams;
	}

	public void setNumStreams(int numStreams) {
		this.numStreams = numStreams;
	}

	public List<Integer> getQualities() {
		return qualities;
	}

	public void setQualities(List<Integer> qualities) {
		this.qualities = qualities;
	}

}
