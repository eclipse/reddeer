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

	public ServersViewException(String message) {
		super(message);
	}

	public ServersViewException(String message, Throwable cause) {
		super(message, cause);
	}
}
