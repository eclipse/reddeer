package org.jboss.reddeer.uiforms.exception;

import org.jboss.reddeer.common.exception.RedDeerException;

/**
 * UIFormLayerException is general exception for errors in UIForms Layer.
 * 
 * @author rhopp
 *
 */

public class UIFormLayerException extends RedDeerException {

	private static final long serialVersionUID = 1L;

	public UIFormLayerException() {
		super("Exception in RedDeer UIForm layer");
	}

	public UIFormLayerException(String message) {
		super(message);
	}

	public UIFormLayerException(String message, Throwable cause) {
		super(message, cause);
	}
	public UIFormLayerException(String message, Throwable cause , String[] aMessageDetails) {
		super(message, cause);
		if (aMessageDetails != null){
			for (String messageDetail : aMessageDetails){
				addMessageDetail(messageDetail);
			}
		}
		
	}
	public UIFormLayerException(String message, String[] aMessageDetails) {
		super(message);
		if (aMessageDetails != null){
			for (String messageDetail : aMessageDetails){
				addMessageDetail(messageDetail);
			}
		}
		
	}

}
