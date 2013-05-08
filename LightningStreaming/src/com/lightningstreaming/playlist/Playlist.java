package com.lightningstreaming.playlist;

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
	
	//private int currentSegment;
	
	
}
