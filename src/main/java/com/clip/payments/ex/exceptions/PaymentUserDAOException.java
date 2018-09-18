package com.clip.payments.ex.exceptions;

public class PaymentUserDAOException extends Exception {
	
	public PaymentUserDAOException() { super("An error occurred accessing data"); }
	public PaymentUserDAOException(String message) { super(message); }
	/**
	 * 
	 */
	private static final long serialVersionUID = -5698218733233333669L;

}
