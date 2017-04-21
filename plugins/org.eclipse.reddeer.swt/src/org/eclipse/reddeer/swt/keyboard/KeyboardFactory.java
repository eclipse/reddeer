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
package org.eclipse.reddeer.swt.keyboard;

import org.eclipse.reddeer.common.platform.RunningPlatform;
import org.eclipse.reddeer.swt.keyboard.internal.DefaultKeyboard;
import org.eclipse.reddeer.swt.keyboard.internal.MacKeyboard;

/**
 * Factory class determining which keyboard will be used based on current OS.
 * 
 * @author rhopp
 *
 */

public class KeyboardFactory {

	/**
	 * Gets the keyboard.
	 *
	 * @return the keyboard
	 */
	public static Keyboard getKeyboard() {
		if (RunningPlatform.isOSX()) {
			return new MacKeyboard();
		} else {
			return new DefaultKeyboard();
		}
	}

}
