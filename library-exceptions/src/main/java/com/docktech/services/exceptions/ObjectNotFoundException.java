package com.docktech.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String exception) {
		super(exception);
	}
	
	public ObjectNotFoundException(String exception, Throwable cause) {
		super(exception,cause);
	}

}
