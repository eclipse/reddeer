/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.swt.keyboard.internal;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.keyboard.Keyboard;

/**
 * Keyboard implementation for non MacOS specific behavior.
 * 
 * @author rhopp
 *
 */

public class DefaultKeyboard extends Keyboard {
	private static final Logger log = Logger.getLogger(DefaultKeyboard.class);
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.keyboard.Keyboard#writeToClipboard(boolean)
	 */
	public void writeToClipboard(boolean cut) {

		log.info("Write to clipboard");
		press(SWT.CONTROL);
		if (cut) {
			type('x');
		} else {
			type('c');
		}
		release(SWT.CONTROL);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.keyboard.Keyboard#pasteFromClipboard()
	 */
	public void pasteFromClipboard() {
		log.info("Paste from clipboard");
		press(SWT.CONTROL);
		type('v');
		release(SWT.CONTROL);
	}

}
