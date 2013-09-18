package org.jboss.reddeer.requirements.exception;

/**
 * Thrown when some error appeared on requirements layer. Typically when
 * requirement couldn't be fulfilled (even though canFulfill returned true)
 * 
 * @author rhopp
 */

public class RequirementsLayerException extends RuntimeException {

	private static final long serialVersionUID = 6490745570893239529L;
	

	public RequirementsLayerException(String message) {
		super(message);
	}
	
	public RequirementsLayerException(String message, Throwable cause) {
		super(message, cause);
	}

}
