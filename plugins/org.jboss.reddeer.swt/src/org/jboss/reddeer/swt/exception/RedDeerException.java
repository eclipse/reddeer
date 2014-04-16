package org.jboss.reddeer.swt.exception;

import java.util.LinkedList;

public abstract class RedDeerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private LinkedList<String> messageDetails;
	
	public RedDeerException(String message) {
		super(message);
	}
	
	public RedDeerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RedDeerException(String message, Throwable cause , String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null){
			for (String messageDetail : aMessageDetails){
				addMessageDetail(messageDetail);
			}
		}
		
	}
	public RedDeerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null){
			for (String messageDetail : aMessageDetails){
				addMessageDetail(messageDetail);
			}
		}
		
	}
	/**
	 * Adds messageDetail to message details
	 * @param messageDetail
	 */
	public void addMessageDetail(String messageDetail){
		if (this.messageDetails == null){
			this.messageDetails = new LinkedList<String>();
		}
		this.messageDetails.addLast(messageDetail);
	}
	@Override
	public String toString(){
		String result = super.toString();
		String formattedMessageDetails = getFormattedMessageDetails();
		if (formattedMessageDetails != null && formattedMessageDetails.length() > 0){
			result += "\nException details:" + formattedMessageDetails;
		}
		return result;
	}
	/**
	 * Formats message details for output
	 * @return
	 */
	private String getFormattedMessageDetails(){
		StringBuffer sbMessageDetails = new StringBuffer();
		if (this.messageDetails != null){
			for (String messageDetail : this.messageDetails){
				sbMessageDetails.append("\n\t");
				sbMessageDetails.append(messageDetail);
			}
			
		}
		return sbMessageDetails.toString();
	}
}
