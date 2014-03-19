package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;

/**
 * Contains methods that handle UI operations on {@link Text} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TextHandler {

	private static TextHandler instance;

	private TextHandler() {

	}

	/**
	 * Creates and returns instance of TextHandler class
	 * 
	 * @return
	 */
	public static TextHandler getInstance() {
		if (instance == null) {
			instance = new TextHandler();
		}
		return instance;
	}

	/**
	 * Set text to {@link Text} if it is editable
	 * 
	 * @param w
	 *            given widgets
	 * @param text
	 *            text to be set
	 */
	public void setText(final Text w, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Text textField = (Text) w;
				if(!textField.getEditable()) {
					throw new SWTLayerException("Text field is not editable");
				}
				textField.setText(text);
			}
		});
	}
}
