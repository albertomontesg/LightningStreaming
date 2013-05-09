package com.lightningstreaming.playlist;

import java.net.URI;
import java.net.URL;

import com.lightningstreaming.regex.Regex;

public class Segment {

	private float duration;
	private int id;

	private URI relativePath;
	private URL url;
	private float currentPosition;
	

	public Segment (float duration, URI relativePath, URL url) {
		this.setDuration(duration);
		this.setRelativePath(relativePath);
		this.setUrl(url);
	}

	public Segment (int id, float duration, URI relativePath, URL url) {
		this.setDuration(duration);
		this.setRelativePath(relativePath);
		this.setUrl(url);
		this.setCurrentPosition(id);
	}
	
	public static Segment parse(String input, URL url, int id) {
		float dur = Float.parseFloat(Regex.extractString(input, ":", ","));
		URL urlSegment = null;
		URI rPath = null;
		String u = Regex.extractString(input, "\n", "\n");
		try {
			
			if (Regex.count(u, "www") == 0)
				urlSegment = new URL(Regex.getUrlPath(url) + u);
			else
				urlSegment = new URL(u);
			
			
			if (Regex.count(urlSegment.toString(), "/") > 0) 
				rPath = url.toURI().relativize(urlSegment.toURI());
			else
				rPath = new URI(urlSegment.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Segment seg = new Segment(id, dur, rPath, urlSegment);
		
		return seg;
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
