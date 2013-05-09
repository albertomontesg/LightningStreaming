package com.lightningstreaming.playlist;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.lightningstreaming.regex.Regex;


public class SegmentPlaylist {
	
	private String tag;
	private URL url;
	private URI relativePath;
	private int mediaSequence;
	private float targetDuration;
	
	List<Segment> segments;
	
	
	
	public SegmentPlaylist(float targetDuration, int mediaSequence, URL url, String tag, List<Segment> segments) {
		this.setTargetDuration(targetDuration);
		this.setMediaSequence(mediaSequence);
		this.setUrl(url);
		this.setTag(tag);
		this.segments = segments;
		
		// Condicionals for extrange values of targetduration with throws
	}

	
	/**
	 * Parses a SegmentPlaylist passed through the File downloaded.
	 * @param file
	 * @param url
	 * @return Returns a Segment Playlist parsed.
	 */
	public static SegmentPlaylist parse(File file, URL url) {
		
		String data = Regex.fileToString(file);
		
		float t = -1;
		int m = -1;
		
		if (data.contains("#EXT-X-TARGETDURATION")) {
			t = Float.parseFloat(Regex.extractString(data, "#EXT-X-TARGETDURATION:", "\n"));
		}
		
		if (data.contains("#EXT-X-MEDIA-SEQUENCE")) {
			m = Integer.parseInt(Regex.extractString(data, "#EXT-X-MEDIA-SEQUENCE:", "\n"));
		}
		
		String tag = file.getName();
		
		List<Segment> s = new ArrayList<Segment>();
		int numSegments = Regex.count(data, "#EXTINF:");
		if (numSegments > 0) {
			Vector<String> seg = new Vector<String>(Arrays.asList(data.split("#EXTINF")));
			seg.remove(0);
			for (int i = m; i < (numSegments + m); i++)
				s.add(Segment.parse(seg.get(i), url, i));
		}
		
		SegmentPlaylist playlist = new SegmentPlaylist (t, m, url, tag, s);
		
		return playlist;
	}

	public int getMediaSequence() {
		return mediaSequence;
	}

	public void setMediaSequence(int mediaSequence) {
		this.mediaSequence = mediaSequence;
	}

	public float getTargetDuration() {
		return targetDuration;
	}

	public void setTargetDuration(float targetDuration) {
		this.targetDuration = targetDuration;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public URI getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(URI relativePath) {
		this.relativePath = relativePath;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
	public int getNumSegments() {
		return this.segments.size();
	}
	
	public float getTotalDuration() {
		float totalDuration = 0;
		for (int i = 0; i < this.getNumSegments(); i++) totalDuration += this.segments.get(i).getDuration();
		return totalDuration;
	}
	
}
