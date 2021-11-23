package com.revature.exceptions;

public class ReimbursementNotFoundExcpetion extends Exception {

	public ReimbursementNotFoundExcpetion() {
		super();
	}

	public ReimbursementNotFoundExcpetion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementNotFoundExcpetion(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementNotFoundExcpetion(String message) {
		super(message);
	}

	public ReimbursementNotFoundExcpetion(Throwable cause) {
		super(cause);
	}
	

}
