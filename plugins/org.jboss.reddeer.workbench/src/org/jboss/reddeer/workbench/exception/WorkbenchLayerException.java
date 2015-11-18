package org.jboss.reddeer.workbench.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * Universal exception for workbench layer.
 */
public class WorkbenchLayerException extends RedDeerException {

	private static final long serialVersionUID = 2815336044156846700L;
	
	/**
	 * Instantiates a new workbench layer exception.
	 *
	 * @param message the message
	 */
	public WorkbenchLayerException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new workbench layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public WorkbenchLayerException(String message, Throwable cause){
		super(message, cause);
	}

}
