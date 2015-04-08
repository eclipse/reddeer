package org.jboss.reddeer.eclipse.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * Thrown when an error can be identified on the Eclipse layer (e.g. something is not found on a view)
 * 
 * @author Lucia Jelinkova
 *
 */
public class EclipseLayerException extends RedDeerException {

	private static final long serialVersionUID = 3457199665187648827L;

	public EclipseLayerException(String message) {
		super(message);
	}

	public EclipseLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
