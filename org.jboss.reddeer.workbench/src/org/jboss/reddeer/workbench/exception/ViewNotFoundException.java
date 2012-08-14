package org.jboss.reddeer.workbench.exception;

/**
 * View is not found due to not registered in workbench or view category
 * 
 * @author jjankovi
 *
 */
public class ViewNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ViewNotFoundException(String message) {
		super(message);
	}
}
