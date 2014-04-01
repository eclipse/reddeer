package org.jboss.reddeer.workbench.exception;

public class WorkbenchLayerException extends RuntimeException {

	/**
	 * Universal exception for workbench layer
	 */
	private static final long serialVersionUID = 2815336044156846700L;
	
	public WorkbenchLayerException(String message) {
		super(message);
	}
	
	public WorkbenchLayerException(String message, Throwable cause){
		super(message, cause);
	}
	
	public WorkbenchLayerException(Throwable cause){
		
	}

}
