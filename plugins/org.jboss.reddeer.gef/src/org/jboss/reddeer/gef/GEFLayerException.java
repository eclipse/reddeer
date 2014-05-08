package org.jboss.reddeer.gef;

import org.jboss.reddeer.swt.exception.RedDeerException;

/**
 * GEF Layer Exception
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class GEFLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

	public GEFLayerException(String message) {
		super(message);
	}

	public GEFLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	public GEFLayerException(String message, Throwable cause, String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	public GEFLayerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
