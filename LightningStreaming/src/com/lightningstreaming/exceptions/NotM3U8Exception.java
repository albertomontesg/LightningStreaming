package com.lightningstreaming.exceptions;

public class NotM3U8Exception extends ParsingException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		String m = "This is not an m3u8 file";
		return super.getMessage() + ": " + m;
	}
	
}
