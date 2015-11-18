package org.jboss.reddeer.eclipse.wst.server.ui.view;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;

/**
 * Thrown when an unexpected state or operation is detected on {@link ServersView} 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ServersViewException extends EclipseLayerException {

	private static final long serialVersionUID = -5850409602777204031L;

	/**
	 * Instantiates a new servers view exception.
	 *
	 * @param message the message
	 */
	public ServersViewException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new servers view exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ServersViewException(String message, Throwable cause) {
		super(message, cause);
	}
}
