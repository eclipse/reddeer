package org.jboss.reddeer.swt.exception;


/**
 * WidgetLostException means that available Widget is not present anymore
 * @author Jiri Peterka
 * @deprecated - use SWTLayerException as a general exception on SWT Layer *
 *
 */
public class WidgetLostException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public WidgetLostException(String message) {
		super(message);
	}
}
