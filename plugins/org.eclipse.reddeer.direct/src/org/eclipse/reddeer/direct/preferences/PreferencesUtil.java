/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
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
	public static final String CONSOLE_PLUGIN = "org.eclipse.debug.ui";
	public static final String CONSOLE_LIMIT_OUTPUT_KEY = "Console.limitConsoleOutput";
	public static final String CONSOLE_LIMIT_OUTPUT_LOW_KEY = "Console.lowWaterMark";
	public static final String CONSOLE_LIMIT_OUTPUT_HIGH_KEY = "Console.highWaterMark";
	public static final String CONSOLE_OPEN_ON_ERR_KEY = "DEBUG.consoleOpenOnErr";
	public static final String CONSOLE_OPEN_ON_OUT_KEY = "DEBUG.consoleOpenOnOut";
	public static final String AERI_PLUGIN = "org.eclipse.epp.logging.aeri.ide";
	public static final String AERI_SEND_MODE_KEY = "sendMode";

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
	 * Decides whether the console output is limited.
	 * 
	 * @return true if the console output is limited; false otherwise
	 */
	public static boolean isConsoleOutputLimited() {
		return "true".equalsIgnoreCase(Preferences.get(CONSOLE_PLUGIN, CONSOLE_LIMIT_OUTPUT_KEY));
	}

	/**
	 * Sets the console output to limited or unlimited.
	 * 
	 * @param limited
	 */
	public static void setConsoleOutputLimited(boolean limited) {
		Preferences.set(CONSOLE_PLUGIN, CONSOLE_LIMIT_OUTPUT_KEY, String.valueOf(limited));
	}

	/**
	 * Gets the console output size.
	 * 
	 * @return console output size
	 */
	public static int getConsoleOutputSize() {
		return Integer.valueOf(Preferences.get(CONSOLE_PLUGIN, CONSOLE_LIMIT_OUTPUT_LOW_KEY));
	}

	/**
	 * Sets the console output size.
	 * 
	 * @param size
	 */
	public static void setConsoleOutputSize(int size) {
		setConsoleOutputLimited(true);
		Preferences.set(CONSOLE_PLUGIN, CONSOLE_LIMIT_OUTPUT_LOW_KEY, String.valueOf(size));
		// This is how eclipse does it
		Preferences.set(CONSOLE_PLUGIN, CONSOLE_LIMIT_OUTPUT_HIGH_KEY, String.valueOf(size + 8000));
	}

	/**
	 * Decides whether the console is opened on error.
	 * 
	 * @return true if the console is opened on error; false otherwise
	 */
	public static boolean isConsoleOpenedOnError() {
		return "true".equalsIgnoreCase(Preferences.get(CONSOLE_PLUGIN, CONSOLE_OPEN_ON_ERR_KEY));
	}

	/**
	 * Decides whether the console is opened on standard output.
	 * 
	 * @return true if the console is opened on standard output; false otherwise
	 */
	public static boolean isConsoleOpenedOnOutput() {
		return "true".equalsIgnoreCase(Preferences.get(CONSOLE_PLUGIN, CONSOLE_OPEN_ON_OUT_KEY));
	}

	/**
	 * Sets the console open on standard output
	 */
	public static void setConsoleOpenedOnError(boolean openOnError) {
		log.info("Sets the console open on error to '" + openOnError + "'");
		Preferences.set(CONSOLE_PLUGIN, CONSOLE_OPEN_ON_ERR_KEY, String.valueOf(openOnError));
	}

	/**
	 * Sets the console open on standard output
	 */
	public static void setConsoleOpenedOnOutput(boolean openOnOutput) {
		log.info("Sets the console open on error to '" + openOnOutput + "'");
		Preferences.set(CONSOLE_PLUGIN, CONSOLE_OPEN_ON_OUT_KEY, String.valueOf(openOnOutput));
	}
	
	/**
	 * Sets AERI reporting send mode
	 * @param mode possible values are NOTIFY, NEVER and BACKGROUND
	 */
	public static void setAERISendMode(String mode) {
		log.info("Setting the aeri reporting to never send.");
		Preferences.set(AERI_PLUGIN, AERI_SEND_MODE_KEY, mode);
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
