package com.lightningstreaming.exceptions;

public class URLNotFoundException extends ParsingException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		String m = "URL not found at the m3u8 file";
		return super.getMessage() + ": " + m;
	}

}
