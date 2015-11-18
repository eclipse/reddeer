package org.jboss.reddeer.gef;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * GEF Layer Exception
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class GEFLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 */
	public GEFLayerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public GEFLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param aMessageDetails the a message details
	 */
	public GEFLayerException(String message, Throwable cause, String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Instantiates a new GEF layer exception.
	 *
	 * @param message the message
	 * @param aMessageDetails the a message details
	 */
	public GEFLayerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
