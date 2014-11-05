package org.jboss.reddeer.workbench.exception;


public class WorkbenchPartNotFound extends WorkbenchLayerException {

	/**
	 * Indicates, that given part couldn't be found.
	 */
	private static final long serialVersionUID = 3317042241308461846L;

	public WorkbenchPartNotFound() {
		super("WorkbenchPart was not found");
	}

	public WorkbenchPartNotFound(String message) {
		super(message);
	}

	public WorkbenchPartNotFound(String message, Throwable cause) {
		super(message, cause);
	}

}
