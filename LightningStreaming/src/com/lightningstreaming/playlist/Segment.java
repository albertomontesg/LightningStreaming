package com.lightningstreaming.playlist;

import java.net.URI;
import java.net.URL;

public class Segment {

	private float duration;
	private int id;
	private URI relativePath;
	private URL url;
	private float currentPosition;
	
	public Segment (int id, float duration, URI relativePath, URL url) {
		this.setDuration(duration);
		this.setRelativePath(relativePath);
		this.setUrl(url);
		this.setCurrentPosition(id);
	}

	public URI getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(URI path) {
		this.relativePath = path;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(float currentPosition) {
		this.currentPosition = currentPosition;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
