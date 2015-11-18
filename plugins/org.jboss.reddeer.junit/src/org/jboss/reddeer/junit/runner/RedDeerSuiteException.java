package org.jboss.reddeer.junit.runner;

/**
 * Exception thrown during Red Deer test suite processing. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RedDeerSuiteException extends RuntimeException {

	private static final long serialVersionUID = 4399019522052308797L;

	/**
	 * Instantiates a new red deer suite exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RedDeerSuiteException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new red deer suite exception.
	 *
	 * @param message the message
	 */
	public RedDeerSuiteException(String message) {
		super(message);
	}
}
