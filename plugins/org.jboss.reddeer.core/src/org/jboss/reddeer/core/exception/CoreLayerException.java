package org.jboss.reddeer.core.exception;

import org.jboss.reddeer.common.exception.RedDeerException;
/**
 * RedDeerCoreException indicates exceptional situation RedDeer core level
 * 
 * @author Jiri Peterka
 *
 */
public class CoreLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new RedDeerCoreException with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public CoreLayerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new RedDeerCoreException with the specified detail
	 * message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 */
	public CoreLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new RedDeerCoreException with the specified detail
	 * message, cause and messageDetails.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 * @param messageDetails details of message
	 */
	public CoreLayerException(String message, Throwable cause,
			String[] messageDetails) {
		super(message, cause);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Constructs a new RedDeerCoreException with the specified detail
	 * message and messageDetails.
	 * 
	 * @param message the detail message
	 * @param messageDetails details of message
	 */
	public CoreLayerException(String message, String[] messageDetails) {
		super(message);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
