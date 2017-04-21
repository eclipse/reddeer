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
package org.eclipse.reddeer.direct.preferences;

import java.util.Arrays;

import org.eclipse.reddeer.common.logging.Logger;

/**
 * This is a util class for setting common preferences.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class PreferencesUtil {

	public static final String AUTO_BUILDING_PLUGIN = "org.eclipse.core.resources";
	public static final String AUTO_BUILDING_KEY = "description.autobuilding";
	public static final String OPEN_ASSOCIATED_PERSPECTIVE_PLUGIN = "org.eclipse.ui.ide";
	public static final String OPEN_ASSOCIATED_PERSPECTIVE_KEY = "SWITCH_PERSPECTIVE_ON_PROJECT_CREATION";

	private static final Logger log = Logger.getLogger(PreferencesUtil.class);

	/**
	 * Decides whether the auto building is on/off.
	 * 
	 * @return true if the auto building is on, false otherwise
	 */
	public static boolean isAutoBuildingOn() {
		return "true".equalsIgnoreCase(Preferences.get(AUTO_BUILDING_PLUGIN, AUTO_BUILDING_KEY));
	}

	/**
	 * Sets the auto building on.
	 */
	public static void setAutoBuildingOn() {
		log.info("Setting the auto building ON.");
		Preferences.set(AUTO_BUILDING_PLUGIN, AUTO_BUILDING_KEY, "true");
	}

	/**
	 * Sets the auto building off.
	 */
	public static void setAutoBuildingOff() {
		log.info("Setting the auto building OFF.");
		Preferences.set(AUTO_BUILDING_PLUGIN, AUTO_BUILDING_KEY, "false");
	}

	/**
	 * Returns the option for opening the associated perspective when creating a new project
	 * 
	 * @return the option for opening the associated perspective when creating a new project
	 */
	public static String getOpenAssociatedPerspective() {
		return Preferences.get(OPEN_ASSOCIATED_PERSPECTIVE_PLUGIN, OPEN_ASSOCIATED_PERSPECTIVE_KEY);
	}

	/**
	 * Sets the way of opening the associated perspective when creating a new project.
	 * 
	 * @param value
	 *            always|never|prompt
	 */
	public static void setOpenAssociatedPerspective(String value) {
		oneOf(value, "always", "never", "prompt");
		Preferences.set(OPEN_ASSOCIATED_PERSPECTIVE_PLUGIN, OPEN_ASSOCIATED_PERSPECTIVE_KEY, value);
	}

	/**
	 * Validates a given text whether it is one of specified strings.
	 * 
	 * @param text
	 *            a text to be validated
	 * @param strings
	 *            allowed strings including a null value
	 * @return the validated text
	 * @throws IllegalArgumentException
	 *             if the text is not one of the allowed strings
	 */
	private static String oneOf(String text, String... strings) {
		for (String string : strings) {
			if (string == null && text == null) {
				return text;
			}
			if (string != null && string.equals(text)) {
				return text;
			}
		}
		throw new IllegalArgumentException("Expected one of " + Arrays.toString(strings) + " but was '" + text + "'");
	}

}
