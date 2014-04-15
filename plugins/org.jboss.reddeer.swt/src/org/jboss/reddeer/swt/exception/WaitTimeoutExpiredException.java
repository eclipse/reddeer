package org.jboss.reddeer.swt.exception;

/**
 * Wait Timeout Exception indicates expired waiting timeout
 * 
 * @author Vlado Pakan
 * 
 */
public class WaitTimeoutExpiredException extends RedDeerException {

	private static final long serialVersionUID = 5905873761753380173L;
	/**
	 * Creates WaitTimeoutExpiredException with specified message
	 * @param message
	 */
	public WaitTimeoutExpiredException(String message) {
		super(message);
	}
	/**
	 * Creates WaitTimeoutExpiredException with specified message and cause
	 * @param message
	 * @param cause
	 */
	public WaitTimeoutExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
