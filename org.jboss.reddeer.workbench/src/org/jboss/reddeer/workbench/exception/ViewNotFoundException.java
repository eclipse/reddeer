package org.jboss.reddeer.workbench.exception;

/**
 * View is not registered
 * @author Jiri Peterka
 *
 */
public class ViewNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ViewNotFoundException(String message) {
		super(message);
	}
}
