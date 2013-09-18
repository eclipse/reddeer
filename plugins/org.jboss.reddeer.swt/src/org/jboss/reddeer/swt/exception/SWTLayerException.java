package org.jboss.reddeer.swt.exception;

import java.util.LinkedList;
/**
 * SWT Layer Exception that indicates something wrong went on SWT Layer
 * @author Jiri Peterka
 *
 */
public class SWTLayerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<String> messageDetails;

	public SWTLayerException(String message) {
		super(message);
	}
	
	public SWTLayerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SWTLayerException(String message, Throwable cause , String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null){
			for (String messageDetail : aMessageDetails){
				addMessageDetail(messageDetail);
			}
		}
		
	}
	public SWTLayerException(String message, String[] aMessageDetails) {
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
