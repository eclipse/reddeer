package org.jboss.reddeer.swt.exception;

/**
 * SWT Layer Exception that indicates something wrong went on SWT Layer
 * @author Jiri Peterka
 *
 */
public class SWTLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

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
}
