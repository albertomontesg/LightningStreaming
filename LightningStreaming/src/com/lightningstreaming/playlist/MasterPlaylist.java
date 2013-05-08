package com.lightningstreaming.playlist;

import java.io.File;
import java.net.URL;

public class MasterPlaylist extends Playlist {

	public MasterPlaylist(float targetDuration, int mediaSequence) {
		super(targetDuration, mediaSequence);
	}

	@Override
	public Playlist parse(File file, URL url) {
		// TODO Auto-generated method stub
		return null;
	}

}
