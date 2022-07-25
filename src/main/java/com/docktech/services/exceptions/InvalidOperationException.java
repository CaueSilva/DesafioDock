package com.docktech.services.exceptions;

public class InvalidOperationException  extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidOperationException(String exception) {
		super(exception);
	}
	
	public InvalidOperationException(String exception, Throwable cause) {
		super(exception,cause);
	}

}
