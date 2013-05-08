package com.lightningstreaming.playlist;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Dictionary;

public class MasterPlaylist {

	private float totalDuration;
	private int numSegments;
	

	private String name;
	private URI path;
	private URL url;
	private int numStreams;
	
	private Dictionary<Integer, SegmentPlaylist> streams;
	
	public MasterPlaylist (String name, URI path, URL url, Dictionary<Integer, SegmentPlaylist> streams) {
		this.setName(name);
		this.setPath(path);
		this.setUrl(url);
		this.setStreams(streams);
		this.setNumStreams(streams.size());
	}

	public static MasterPlaylist parse(File file, URL url) {
		
		String n = file.getName();
		URI p = new URI(file.getAbsolutePath());
		
		
		
		return null;
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



	public Dictionary<Integer, SegmentPlaylist> getStreams() {
		return streams;
	}



	public void setStreams(Dictionary<Integer, SegmentPlaylist> streams) {
		this.streams = streams;
	}

	public int getNumStreams() {
		return numStreams;
	}

	public void setNumStreams(int numStreams) {
		this.numStreams = numStreams;
	}

}
