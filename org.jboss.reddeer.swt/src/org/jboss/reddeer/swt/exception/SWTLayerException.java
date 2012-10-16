package org.jboss.reddeer.swt.exception;

/**
 * WidgetNotEnabledException means widget is not enabled
 * @author Vlado Pakan
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
