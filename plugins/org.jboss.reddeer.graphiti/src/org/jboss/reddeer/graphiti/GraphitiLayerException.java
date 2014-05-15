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

	public GraphitiLayerException(String message) {
		super(message);
	}

	public GraphitiLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	public GraphitiLayerException(String message, Throwable cause, String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	public GraphitiLayerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null) {
			for (String messageDetail : aMessageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}
}
