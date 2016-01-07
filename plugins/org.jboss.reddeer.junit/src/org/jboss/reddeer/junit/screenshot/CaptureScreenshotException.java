/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.screenshot;

import org.jboss.reddeer.common.logging.Logger;

/**
 * Purpose of this exception is controlling flow of JUnit tests. While capturing screenshot occurs any exception, this exception should be thrown and 
 * processed before test failure.
 * 
 * @author mlabuda
 * @since 0.5
 */
public class CaptureScreenshotException extends Exception {

	private static final long serialVersionUID = -6781761262068464965L;

	/**
	 * Instantiates a new capture screenshot exception.
	 */
	public CaptureScreenshotException() {
		super();
	}
	
	/**
	 * Instantiates a new capture screenshot exception.
	 *
	 * @param message the message
	 */
	public CaptureScreenshotException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new capture screenshot exception.
	 *
	 * @param cause the cause
	 */
	public CaptureScreenshotException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Instantiates a new capture screenshot exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CaptureScreenshotException(String message, Throwable cause) {
		super(message, cause);
	}	
	
	/**
	 * Prints the info.
	 *
	 * @param logger the logger
	 */
	public void printInfo(Logger logger) {
		logger.error("Capturing screenshot failed because of following error: " + getMessage());
		logger.error("Error was caused by: " + "\n" + getCause());
	}
}
