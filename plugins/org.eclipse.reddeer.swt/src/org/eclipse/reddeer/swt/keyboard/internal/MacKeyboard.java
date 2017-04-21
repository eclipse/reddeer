/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.keyboard.internal;

import org.eclipse.swt.SWT;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.swt.keyboard.Keyboard;

/**
 * Keyboard implementation for MacOS specific behavior.
 * 
 * @author rhopp
 *
 */

public class MacKeyboard extends Keyboard {
	private static final Logger log = Logger.getLogger(MacKeyboard.class);
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.swt.keyboard.Keyboard#writeToClipboard(boolean)
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
	 * @see org.eclipse.reddeer.swt.keyboard.Keyboard#pasteFromClipboard()
	 */
	public void pasteFromClipboard() {
		log.debug("Paste from clipboard");
		press(SWT.COMMAND);
		type('v');
		release(SWT.COMMAND);
	}

}
