package org.jboss.reddeer.swt.keyboard.internal;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.keyboard.Keyboard;

/**
 * Keyboard implementation for MacOS specific behavior.
 * 
 * @author rhopp
 *
 */

public class MacKeyboard extends Keyboard {
	private static final Logger log = Logger.getLogger(MacKeyboard.class);
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.keyboard.Keyboard#writeToClipboard(boolean)
	 */
	public void writeToClipboard(boolean cut) {

		log.debug("Write to clipboard");
		press(SWT.COMMAND);
		if (cut) {
			type('x');
		} else {
			type('c');
		}
		release(SWT.COMMAND);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.keyboard.Keyboard#pasteFromClipboard()
	 */
	public void pasteFromClipboard() {
		log.debug("Paste from clipboard");
		press(SWT.COMMAND);
		type('v');
		release(SWT.COMMAND);
	}

}
