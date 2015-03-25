package org.jboss.reddeer.common.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * WaitTimeoutExpiredException indicates reaching timeout time period.
 * 
 * @author Vlado Pakan
 * 
 */
public class WaitTimeoutExpiredException extends RedDeerException {

	private static final long serialVersionUID = 5905873761753380173L;
	
	/**
	 * Creates WaitTimeoutExpiredException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public WaitTimeoutExpiredException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new WaitTimeoutExpiredException with the specified detail
	 * message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 */
	public WaitTimeoutExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
