package com.lightningstreaming.playlist;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class SegmentPlaylist extends Playlist {
	
	String tag;
	URI relativePath;
	List<Segment> segments;
	
	public SegmentPlaylist(float targetDuration, int mediaSequence, URL url, String tag, List<Segment> segments) {
		super(targetDuration, mediaSequence);
		super.setNumSegments(segments.size());
		this.tag = tag;
		
		
		// Condicionals for extrange values of targetduration with throws
	}

	@Override
	public Playlist parse(File file, URL url) {
		return null;
	}
	
}
