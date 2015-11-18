package org.jboss.reddeer.junit.requirement;

/**
 * Exception thrown during requirements processing. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementException extends RuntimeException {

	private static final long serialVersionUID = 8655257087643600787L;

	/**
	 * Instantiates a new requirement exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RequirementException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new requirement exception.
	 *
	 * @param message the message
	 */
	public RequirementException(String message) {
		super(message);
	}
}
