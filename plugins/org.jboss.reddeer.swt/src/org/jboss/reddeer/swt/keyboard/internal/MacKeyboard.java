package org.jboss.reddeer.swt.keyboard.internal;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.keyboard.Keyboard;

/**
 * Keyboard implementation for MacOS specific behavior.
 * 
 * @author rhopp
 *
 */

public class MacKeyboard extends Keyboard {

	public void writeToClipboard(boolean cut) {

		log.debug("Writing to clipboard");
		press(SWT.COMMAND);
		if (cut) {
			type('x');
		} else {
			type('c');
		}
		release(SWT.COMMAND);
	}

	public void pasteFromClipboard() {
		log.debug("Pasting from clipboard");
		press(SWT.COMMAND);
		type('v');
		release(SWT.COMMAND);
	}

}
