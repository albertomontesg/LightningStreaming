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
import com.lightningstreaming.exceptions.ParsingException;
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
	
	private int currentSegment;
	private SegmentPlaylist currentStream;
	private int currentQuality;
	
	public MasterPlaylist (String name, URI path, URL url, TreeMap<Integer, SegmentPlaylist> streams) {
		this.setName(name);
		this.setPath(path);
		this.setUrl(url);
		this.setStreams(streams);
		this.setNumStreams(streams.size());
		List<Integer> keys = new ArrayList<Integer>(streams.keySet());
		this.setQualities(keys);
		this.setTotalDuration(streams.get(this.getQualities().get(0)).getTotalDuration());
		this.setNumSegments(this.getNumSegments());
		this.setCurrentQuality(this.getStreams().lastKey());
		this.setCurrentStream(this.getStream(this.getCurrentQuality()));
		this.setCurrentSegment(this.getCurrentStream().getMediaSequence());
	}

	/**
	 * Parses the File passed through.
	 * @param file
	 * @param url
	 * @return Return a MasterPlaylist parsed from the file given
	 */
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
		
		try{
			if(Regex.count(data, "#EXTM3U")!=1);
			{
				throw new ParsingException();
			}
		}
		catch(ParsingException e){
			System.out.println(e.getMessage());
		}
		if (Regex.count(data, "EXTINF") > 0) {
			sp = SegmentPlaylist.parse(file, url);
			s.put(0,sp);
		}
		else if (Regex.count(data, "EXT-X-STREAM") > 0) {
			Vector<String> str = new Vector<String>(Arrays.asList(data.split("#EXT-X-STREAM-INF:")));
			str.remove(0);
			DownloadPlaylist downloadStreamIndex = new DownloadPlaylist();
			
			
			for (int i = 0; i < str.size(); i++) {
				int bandwidth = Integer.parseInt(Regex.extractString(str.get(i), "BANDWIDTH=", ","));
				URL urlStream = null;
				String stream = Regex.extractString(str.get(i), "\n", "\n");
				try {
					if (Regex.count(str.get(i), "www") == 0) {
						urlStream = new URL(url.toString().replace(n, stream));
					}
					else urlStream = new URL(stream);
					
					String streamPath = file.getPath().replace(file.getName(), Regex.extractFileName(urlStream.toString()));
							
					downloadStreamIndex.execute(urlStream, new URL("file", null, streamPath));
					sp = SegmentPlaylist.parse(downloadStreamIndex.get(), urlStream);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				s.put(bandwidth, sp);
			}
		}
		else {
			return null;
		}
		
		MasterPlaylist playlist = new MasterPlaylist(n, p, url, s);
		return playlist;
	}
	public void ChangeStream(int quality){
		currentStream=streams.get(quality);
		
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
	
	public SegmentPlaylist getStream (int i) {
		return this.streams.get(i);
	}

	public int getCurrentSegment() {
		return currentSegment;
	}

	public void setCurrentSegment(int currentSegment) {
		this.currentSegment = currentSegment;
	}

	public SegmentPlaylist getCurrentStream() {
		return currentStream;
	}

	public void setCurrentStream(SegmentPlaylist currentStream) {
		this.currentStream = currentStream;
	}

	public void increaseQuality() {
		int q = qualities.indexOf(this.getCurrentQuality());
		if (q >= 0 && q < this.getNumStreams() - 1) q++;
		this.setCurrentQuality(qualities.get(q));
	}
	
	public void decreaseQuality() {
		int q = qualities.indexOf(this.getCurrentQuality());
		if (q > 0 && q <= this.getNumStreams() - 1) q--;
		this.setCurrentQuality(qualities.get(q));
	}
	
	public boolean isOnlySegmentPlaylist() {
		return (this.getNumStreams() == 1);
	}

	public int getCurrentQuality() {
		return currentQuality;
	}

	public void setCurrentQuality(int currentQuality) {
		this.currentQuality = currentQuality;
		if (qualities.contains(currentQuality))
			this.currentStream = streams.get(currentQuality);
	}
	
}
