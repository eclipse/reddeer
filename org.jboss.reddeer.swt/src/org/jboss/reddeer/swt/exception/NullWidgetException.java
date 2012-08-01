package org.jboss.reddeer.swt.exception;

/**
 * Widget is null 
 * @author Jiri Peterka
 *
 */
public class NullWidgetException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NullWidgetException(String message) {
		super(message);
	}
}
