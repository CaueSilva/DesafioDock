package com.docktech.services.exceptions;

public class DataBaseException  extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataBaseException(String exception) {
		super(exception);
	}
	
	public DataBaseException(String exception, Throwable cause) {
		super(exception,cause);
	}

}
