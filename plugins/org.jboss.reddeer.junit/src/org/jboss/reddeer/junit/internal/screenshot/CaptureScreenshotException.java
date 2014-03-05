package org.jboss.reddeer.junit.internal.screenshot;

import org.jboss.reddeer.junit.logging.Logger;

/**
 * Purpose of this exception is controlling flow of JUnit tests. While capturing screenshot occurs any exception, this exception should be thrown and 
 * processed before test failure.
 * 
 * @author mlabuda
 * @since 0.5
 */
public class CaptureScreenshotException extends Exception {

	private static final long serialVersionUID = -6781761262068464965L;

	public CaptureScreenshotException() {
		super();
	}
	
	public CaptureScreenshotException(String message) {
		super(message);
	}
	
	public CaptureScreenshotException(Throwable cause) {
		super(cause);
	}
	
	public CaptureScreenshotException(String message, Throwable cause) {
		super(message, cause);
	}	
	
	public void printInfo(Logger logger) {
		logger.error("Capturing screenshot failed because of following error: " + getMessage());
		logger.error("Error was caused by: " + "\n" + getCause());
	}
}
