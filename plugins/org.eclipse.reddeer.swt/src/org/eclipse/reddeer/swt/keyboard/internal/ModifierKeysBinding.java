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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.reddeer.common.platform.RunningPlatform;
import org.eclipse.reddeer.swt.exception.SWTLayerException;

/**
 * Util class for translating between names of modifier keys and its SWT integer
 * constants counterparts.
 * 
 * @author rhopp
 *
 */

public class ModifierKeysBinding {

	private static String ALT = "alt";

	private static String CTRL = "ctrl";

	private static String SHIFT = "shift";

	private Map<String, Integer> modifierKeysMap = new HashMap<String, Integer>();

	public ModifierKeysBinding() {
		modifierKeysMap.put(ALT, SWT.ALT);
		if (RunningPlatform.isOSX()) {
			modifierKeysMap.put(CTRL, SWT.COMMAND);
		} else {
			modifierKeysMap.put(CTRL, SWT.CTRL);
		}
		modifierKeysMap.put(SHIFT, SWT.SHIFT);
	}

	/**
	 * Translates modifier key name ("shift" for example) to it's SWT integer
	 * constant counterpart (SWT.SHIFT for example).
	 * 
	 * @param keyName name of the key
	 * @return integer of SWT constant for given keyName.
	 * @throws SWTLayerException when keyName is not recognized.
	 */

	public Integer getModifierKeyFromString(String keyName) {
		Integer keyModifierInt = modifierKeysMap.get(keyName.toLowerCase());
		if (keyModifierInt == null) {
			throw new SWTLayerException("keyName for modifier key not recognized: " + keyName);
		} else {
			return keyModifierInt;
		}
	}

}
