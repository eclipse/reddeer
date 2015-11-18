package org.jboss.reddeer.junit.requirement.inject;

/**
 * Exception thrown during Red Deer requirements injection processing. 
 * 
 * @author jjankovi
 *
 */
public class RequirementInjectionException extends RuntimeException {

	private static final long serialVersionUID = -2739126612283805953L;

	/**
	 * Instantiates a new requirement injection exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RequirementInjectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new requirement injection exception.
	 *
	 * @param message the message
	 */
	public RequirementInjectionException(String message) {
		super(message);
	}

}
