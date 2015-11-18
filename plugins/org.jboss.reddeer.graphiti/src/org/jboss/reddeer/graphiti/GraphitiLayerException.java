package org.jboss.reddeer.graphiti;

import org.jboss.reddeer.gef.GEFLayerException;

/**
 * Graphiti Layer Exception
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class GraphitiLayerException extends GEFLayerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 */
	public GraphitiLayerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public GraphitiLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param aMessageDetails the a message details
	 */
	public GraphitiLayerException(String message, Throwable cause, String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Instantiates a new graphiti layer exception.
	 *
	 * @param message the message
	 * @param aMessageDetails the a message details
	 */
	public GraphitiLayerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
