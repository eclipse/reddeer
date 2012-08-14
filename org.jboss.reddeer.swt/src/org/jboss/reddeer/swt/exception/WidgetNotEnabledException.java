package org.jboss.reddeer.swt.exception;

/**
 * WidgetNotEnabledException means widget is not enabled
 * @author Vlado Pakan
 *
 */
public class WidgetNotEnabledException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public WidgetNotEnabledException(String message) {
		super(message);
	}
}
