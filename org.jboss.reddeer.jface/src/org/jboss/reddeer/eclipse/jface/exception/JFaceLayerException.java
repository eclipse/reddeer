package org.jboss.reddeer.eclipse.jface.exception;

/**
 * Thrown when an error can be identified on the JFace layer
 * 
 * @author Lucia Jelinkova
 *
 */
public class JFaceLayerException extends RuntimeException {

	private static final long serialVersionUID = -3641202955039921211L;

	public JFaceLayerException(String message) {
		super(message);
	}

	public JFaceLayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
