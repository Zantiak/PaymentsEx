package com.clip.payments.ex.exceptions;

public class ValidationException extends Exception{

	public ValidationException() { super("Incorrect parameters!"); }
	public ValidationException(String message) { super(message); }
	/**
	 * 
	 */
	private static final long serialVersionUID = 4855106579900567316L;

}
