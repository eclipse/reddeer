package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.custom.StyledText;
import org.jboss.reddeer.swt.util.Display;

/**
 * Contains methods that handle UI operations on {@link StyledText} widgets. 
 * @author Jiri Peterka
 *
 */
public class StyledTextHandler {

	private static StyledTextHandler instance;

	private StyledTextHandler() {
	}

	/**
	 * Returns StyledText handler instance
	 * 
	 * @return StyledText handler class instance
	 */
	public static StyledTextHandler getInstance() {
		if (instance == null)
			instance = new StyledTextHandler();
		return instance;
	}

	/**
	 * Set selection within StyledText
	 * @param styledText widget instance
	 * @param line line
	 * @param column column
	 */
	public void setSelection(final StyledText styledText, final int line,
			final int column) {

		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				int offset = styledText.getContent().getOffsetAtLine(line)
						+ column;
				styledText.setSelection(offset);
			}
		});

	}

	/**
	 * Insert text on current position
	 * @param styledText widget instance
	 * @param text 
	 */
	public void insertText(final StyledText styledText, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				styledText.insert(text);

			}
		});
	}

	public void insertText(StyledText styledText, int line, int column,
			String text) {
		setSelection(styledText, line, column);
		insertText(styledText, text);
	}
}
