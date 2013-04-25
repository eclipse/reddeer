package org.jboss.reddeer.workbench.exception;

public class EditorNotFoundException extends RuntimeException {

	/**
	 * Exception for cases, when editor couldn't be found (e.g. when no editor is currently open)
	 */
	private static final long serialVersionUID = -1674119668470372812L;

	public EditorNotFoundException() {
		super("Editor was not found");
	}
	
	public EditorNotFoundException(String message){
		super(message);
	}
	
}
