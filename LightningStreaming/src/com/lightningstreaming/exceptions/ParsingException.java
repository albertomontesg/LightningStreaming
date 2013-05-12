package com.lightningstreaming.exceptions;

public class ParsingException extends Exception{

	/**
	 * 
	 */
	private String message="This is not an m3u8 file";
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
