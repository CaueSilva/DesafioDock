package com.docktech.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationLogger {
	
	private static Logger LOGGER;
	
	public ApplicationLogger(Class<?> classe) {
		LOGGER = LoggerFactory.getLogger(classe);
	}
	
	public void logMessage(String msg) {
		LOGGER.info(msg);
	}
	
}
