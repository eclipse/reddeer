package org.jboss.reddeer.swt.keyboard.internal;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.swt.keyboard.Keyboard;

/**
 * Keyboard implementation for non MacOS specific behavior.
 * 
 * @author rhopp
 *
 */

public class DefaultKeyboard extends Keyboard {

	public void writeToClipboard(boolean cut) {

		log.debug("Writing to clipboard");
		press(SWT.CONTROL);
		if (cut) {
			type('x');
		} else {
			type('c');
		}
		release(SWT.CONTROL);
	}

	public void pasteFromClipboard() {
		log.debug("Pasting from clipboard");
		press(SWT.CONTROL);
		type('v');
		release(SWT.CONTROL);
	}

}
