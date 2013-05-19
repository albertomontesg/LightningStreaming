package com.lightningstreaming.exceptions;

import android.util.Log;

public class CouldNotDownloadFilesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void printStackTrace() {
		super.printStackTrace();
		Log.e("Parsing", "Could not download the playlist files");
	}
	
	

}
