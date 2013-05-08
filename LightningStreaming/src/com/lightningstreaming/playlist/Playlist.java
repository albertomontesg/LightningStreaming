package com.lightningstreaming.playlist;

import java.io.File;
import java.net.URI;
import java.net.URL;

public abstract class Playlist {
	private float targetDuration;
	private float totalDuration;
	private int mediaSequence;
	private int numSegments;
	private String name;
	private URI path;
	private URL url;
	
	//private int currentSegment;
	
	public Playlist (float targetDuration, int mediaSequence) {
		this.setMediaSequence(mediaSequence);
		this.setTargetDuration(targetDuration);
	}
	
	public abstract Playlist parse(File file, URL url);
	
	public float getTargetDuration() {
		return targetDuration;
	}
	public void setTargetDuration(float targetDuration) {
		this.targetDuration = targetDuration;
	}
	public int getNumSegments() {
		return numSegments;
	}
	public void setNumSegments(int numSegments) {
		this.numSegments = numSegments;
	}
	public float getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(float totalDuration) {
		this.totalDuration = totalDuration;
	}
	public int getMediaSequence() {
		return mediaSequence;
	}
	public void setMediaSequence(int mediaSequence) {
		this.mediaSequence = mediaSequence;
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
	
	
	
	
}
