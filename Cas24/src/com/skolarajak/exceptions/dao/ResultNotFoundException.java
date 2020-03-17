package com.skolarajak.exceptions.dao;
/**
 * Custom exception
 * @author Goran
 *
 */
public class ResultNotFoundException extends Exception { 
	public ResultNotFoundException() {
		super();
	}

	public ResultNotFoundException(String message) {
		super(message);
	}
}
