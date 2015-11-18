package org.jboss.reddeer.jface.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * Thrown when an error can be identified on the JFace layer
 * 
 * @author Lucia Jelinkova
 * @since 0.6
 *
 */
public class JFaceLayerException extends RedDeerException {

	private static final long serialVersionUID = -3641202955039921211L;

	/**
	 * Instantiates a new j face layer exception.
	 *
	 * @param message the message
	 */
	public JFaceLayerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new j face layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public JFaceLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
