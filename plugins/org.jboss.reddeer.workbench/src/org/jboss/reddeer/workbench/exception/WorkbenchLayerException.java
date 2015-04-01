package org.jboss.reddeer.workbench.exception;

import org.jboss.reddeer.swt.exception.RedDeerException;

/**
 * Universal exception for workbench layer.
 */
public class WorkbenchLayerException extends RedDeerException {

	private static final long serialVersionUID = 2815336044156846700L;
	
	public WorkbenchLayerException(String message) {
		super(message);
	}
	
	public WorkbenchLayerException(String message, Throwable cause){
		super(message, cause);
	}

}
