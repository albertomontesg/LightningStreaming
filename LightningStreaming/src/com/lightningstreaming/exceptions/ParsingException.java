package com.lightningstreaming.exceptions;

public class ParsingException extends Exception{

	/**
	 * 
	 */
	private String message="Parsing ERROR";
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
