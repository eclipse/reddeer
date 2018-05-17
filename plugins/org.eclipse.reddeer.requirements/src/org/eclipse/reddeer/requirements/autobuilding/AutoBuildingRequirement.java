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
package org.eclipse.reddeer.requirements.autobuilding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.requirements.autobuilding.AutoBuildingRequirement.AutoBuilding;

/**
 * This requirement ensures that the setting for auto building is set on/off. During the cleanup phase the change is set
 * back.
 * 
 * @author Andrej Podhradsky
 *
 */
public class AutoBuildingRequirement extends AbstractRequirement<AutoBuilding> {

	private boolean originalValue;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface AutoBuilding {

		/**
		 * Value.
		 *
		 * @return true if the auto building is on, false otherwise
		 */
		boolean value() default true;

		/**
		 * Cleanup. The default value is true.
		 *
		 * @return true if the cleanup is required, false otherwise
		 */
		boolean cleanup() default true;
	}

	@Override
	public void fulfill() {
		originalValue = PreferencesUtil.isAutoBuildingOn();
		if (annotation.value()) {
			PreferencesUtil.setAutoBuildingOn();
		} else {
			PreferencesUtil.setAutoBuildingOff();
		}
	}

	@Override
	public void cleanUp() {
		if (!annotation.cleanup()) {
			return;
		}
		if (originalValue) {
			PreferencesUtil.setAutoBuildingOn();
		} else {
			PreferencesUtil.setAutoBuildingOff();
		}
	}
}
