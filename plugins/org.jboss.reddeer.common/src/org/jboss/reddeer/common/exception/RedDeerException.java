package org.jboss.reddeer.common.exception;

import java.util.LinkedList;

/**
 * General RedDeer runtime exception.
 * 
 * @author rawagner
 */
public class RedDeerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<String> messageDetails;

	/**
	 * Constructs a new RedDeer runtime exception with the specified detail
	 * message.
	 * 
	 * @param message the detail message
	 */
	public RedDeerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new RedDeer runtime exception with the specified detail
	 * message and cause.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 */
	public RedDeerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new RedDeer runtime exception with the specified detail
	 * message, cause and messageDetails.
	 * 
	 * @param message the detail message
	 * @param cause the cause of exception
	 * @param messageDetails details of message
	 */
	public RedDeerException(String message, Throwable cause,
			String[] messageDetails) {
		super(message, cause);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Constructs a new RedDeer runtime exception with the specified detail
	 * message and messageDetails.
	 * 
	 * @param message the detail message
	 * @param messageDetails details of message
	 */
	public RedDeerException(String message, String[] messageDetails) {
		super(message);
		if (messageDetails != null) {
			for (String messageDetail : messageDetails) {
				addMessageDetail(messageDetail);
			}
		}
	}

	/**
	 * Adds message detail to the message details.
	 * 
	 * @param messageDetail message detail to add
	 */
	public void addMessageDetail(String messageDetail) {
		if (this.messageDetails == null) {
			this.messageDetails = new LinkedList<String>();
		}
		this.messageDetails.addLast(messageDetail);
	}

	@Override
	public String toString() {
		String result = super.toString();
		String formattedMessageDetails = getFormattedMessageDetails();
		if (formattedMessageDetails != null
				&& formattedMessageDetails.length() > 0) {
			result += "\nException details:" + formattedMessageDetails;
		}
		return result;
	}

	/**
	 * Formats message details. This method is useful for output of details.
	 * 
	 * @return formatted message details
	 */
	private String getFormattedMessageDetails() {
		StringBuffer sbMessageDetails = new StringBuffer();
		if (this.messageDetails != null) {
			for (String messageDetail : this.messageDetails) {
				sbMessageDetails.append("\n\t");
				sbMessageDetails.append(messageDetail);
			}
		}
		return sbMessageDetails.toString();
	}
}
