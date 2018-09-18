package com.clip.payments.ex.exceptions;

public class PaymentOperationException extends Exception {

	public PaymentOperationException() { super("An error occurred during an operation"); }
	public PaymentOperationException(String message) { super(message); }
	/**
	 * 
	 */
	private static final long serialVersionUID = 911156839540976571L;

}
