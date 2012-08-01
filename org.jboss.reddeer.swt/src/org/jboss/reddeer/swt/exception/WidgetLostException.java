package org.jboss.reddeer.swt.exception;


/**
 * WidgetLostException means that available Widget is not present anymore
 * @author Jiri Peterka
 *
 */
public class WidgetLostException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public WidgetLostException(String message) {
		super(message);
	}
}
