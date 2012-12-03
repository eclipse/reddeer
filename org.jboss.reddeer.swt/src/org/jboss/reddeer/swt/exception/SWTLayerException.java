package org.jboss.reddeer.swt.exception;

/**
 * SWT Layer Exception that indicates something wrong went on SWT Layer
 * @author Jiri Peterka
 *
 */
public class SWTLayerException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public SWTLayerException(String message) {
		super(message);
	}
	
	public SWTLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
