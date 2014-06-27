package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Text} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class TextHandler {

	private static TextHandler instance;

	private TextHandler() {

	}

	/**
	 * Gets instance of TextHandler.
	 * 
	 * @return instance of TextHandler.
	 */
	public static TextHandler getInstance() {
		if (instance == null) {
			instance = new TextHandler();
		}
		return instance;
	}

	/**
	 * Sets specified text into {@link Text}.
	 * 
	 * @param textWidget text widge to handle
	 * @param text text to be set
	 */
	public void setText(final Text textWidget, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				Text textField = (Text) textWidget;
				if (!textField.getEditable()) {
					throw new SWTLayerException("Text field is not editable");
				}
				textField.setText(text);
			}
		});
	}

	/**
	 * Gets text from specified {@link Text} widget.
	 * 
	 * @param textWidget text widget to handle
	 * @return text from the text widget
	 */
	public String getMessage(final Text textWidget) {
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return textWidget.getMessage();
			}
		});
	}

	/**
	 * Finds out whether specified {@link Text} widget is read-only or not.
	 * Readability means whether is widget editable or not.
	 * 
	 * @param textWidget text widget to handle
	 * @return true if text widget is read only, false otherwise
	 */
	public boolean isReadOnly(final Text textWidget) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return (textWidget.getStyle() & SWT.READ_ONLY) != 0;
			}
		});
	}
}
