package org.jboss.reddeer.swt.exception;

/**
 * WidgetNotAvailableException means widget is not available at all
 * @author Jiri Peterka
 *
 */
public class MultipleMatchException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public MultipleMatchException(String message) {
		super(message);
	}
}
