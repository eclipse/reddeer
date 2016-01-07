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
package org.jboss.reddeer.direct.preferences;

import org.jboss.reddeer.common.logging.Logger;

/**
 * This is a util class for setting common preferences.
 * 
 * @author Andrej Podhradsky
 *
 */
public class PreferencesUtil {

	public static final String AUTO_BUILDING_PLUGIN = "org.eclipse.core.resources";
	public static final String AUTO_BUILDING_KEY = "description.autobuilding";

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
}
