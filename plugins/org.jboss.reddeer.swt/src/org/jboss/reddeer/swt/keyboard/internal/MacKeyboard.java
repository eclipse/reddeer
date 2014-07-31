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
	
	public void writeToClipboard(boolean cut) {

		log.info("Write to clipboard (Mac keyboard)");
		press(SWT.COMMAND);
		if (cut) {
			type('x');
		} else {
			type('c');
		}
		release(SWT.COMMAND);
	}

	public void pasteFromClipboard() {
		log.info("Paste from clipboard (Mac keyboard");
		press(SWT.COMMAND);
		type('v');
		release(SWT.COMMAND);
	}

}
