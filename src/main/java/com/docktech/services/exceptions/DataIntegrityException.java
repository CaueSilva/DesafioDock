package com.docktech.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String exception) {
		super(exception);
	}
	
	public DataIntegrityException(String exception, Throwable cause) {
		super(exception,cause);
	}

}
