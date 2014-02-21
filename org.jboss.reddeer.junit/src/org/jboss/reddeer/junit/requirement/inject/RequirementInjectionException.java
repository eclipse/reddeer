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
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public RequirementInjectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public RequirementInjectionException(String message) {
		super(message);
	}

}
