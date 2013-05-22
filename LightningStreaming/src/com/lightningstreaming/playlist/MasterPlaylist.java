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
import com.lightningstreaming.exceptions.CouldNotDownloadFilesException;
import com.lightningstreaming.exceptions.ParsingException;
import com.lightningstreaming.regex.Regex;



public class MasterPlaylist {

	private float totalDuration;
	
	@SuppressWarnings("unused")
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
		this.numSegments = this.getNumSegments();
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
	@SuppressWarnings("unchecked")
	public static MasterPlaylist parse(File file, URL url) throws ParsingException {
		
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
		
		if(Regex.count(data, "#EXTM3U")!=1) throw new ParsingException();

		if (Regex.count(data, "EXTINF") > 0) {
			sp = SegmentPlaylist.parse(file, url);
			s.put(0,sp);
		}
		else if (Regex.count(data, "EXT-X-STREAM") > 0) {
			Vector<String> str = new Vector<String>(Arrays.asList(data.split("#EXT-X-STREAM-INF:")));
			str.remove(0);
			DownloadPlaylist downloadStreamIndex;
			
			List<URL> playlistUrls = new ArrayList<URL>();
			List<URL> playlistDirs = new ArrayList<URL>();
			List<Integer> playlistBandwidth = new ArrayList<Integer>();
			
			
			for (int i = 0; i < str.size(); i++) {
				int bandwidth = Integer.parseInt(Regex.extractString(str.get(i), "BANDWIDTH=", "\n"));
				playlistBandwidth.add(bandwidth);
				URL urlStream = null;
				String stream = Regex.extractString(str.get(i), "\n", "\n");
				try {
					if (Regex.count(str.get(i), "www") == 0) {
						urlStream = new URL(url.toString().replace(n, stream));
					}
					else urlStream = new URL(stream);
					
					String streamPath = file.getPath().replace(file.getName(), stream);
					
					URL dir = new URL("file", null, streamPath);
					
					playlistUrls.add(urlStream);
					playlistDirs.add(dir);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			downloadStreamIndex = new DownloadPlaylist();
			downloadStreamIndex.execute(playlistUrls, playlistDirs);
			List<File> filesDownloaded = null;
			try {
				filesDownloaded = (List<File>) downloadStreamIndex.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (filesDownloaded == null) throw new CouldNotDownloadFilesException();
			
			for (int i = 0; i < filesDownloaded.size(); i++) {
				sp = SegmentPlaylist.parse(filesDownloaded.get(i), playlistUrls.get(i));
				s.put(playlistBandwidth.get(i), sp);
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
		return this.streams.get(streams.firstKey()).getNumSegments();
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
		if (qualities.contains(currentQuality)) {
			this.currentQuality = currentQuality;
			this.currentStream = streams.get(currentQuality);
		}
	}
	
	public int getMaximumQuality() {
		return getQualities().get(getQualities().size()-1);
	}
	
	public int getMinimumQuality() {
		return getQualities().get(0);
	}
	
	public void setMaximumQuality() {
		int maximumQuality = getQualities().get(getQualities().size()-1);
		setCurrentQuality(maximumQuality);
	}
	
	public void setMinimumQuality() {
		int minimumQuality = getQualities().get(0);
		setCurrentQuality(minimumQuality);
	}
	
	public boolean isMaximumQuality() {
		return currentQuality == getMaximumQuality();
	}
	
	public boolean isMinimumQuality() {
		return currentQuality == getMinimumQuality();
	}
	
	public int getUpperQualityLimit() {
		if (isMaximumQuality())
			return Integer.MAX_VALUE;
		else {
			int q = getCurrentQuality();
			int up = getQualities().get(getQualities().indexOf(q)+1);
			return (q+up)/2;
		}	
	}
	
	public int getLowerQualityLimit() {
		if (isMinimumQuality())
			return Integer.MIN_VALUE;
		else {
			int q = getCurrentQuality();
			int down = getQualities().get(getQualities().indexOf(q)-1);
			return (q+down)/2;
		}	
	}
	
}
