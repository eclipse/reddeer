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
package org.eclipse.reddeer.requirements.test.autobuilding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky
 *
 */
@RunWith(RedDeerSuite.class)
public class AutoBuildingRequirementTest {

	private static boolean autoBuilding;

	@BeforeClass
	public static void getOriginalValueOfAutoBuilding() {
		autoBuilding = PreferencesUtil.isAutoBuildingOn();
	}

	@AfterClass
	public static void setAutoBuildingToOriginalValue() {
		if (autoBuilding) {
			PreferencesUtil.setAutoBuildingOn();
		} else {
			PreferencesUtil.setAutoBuildingOff();
		}
	}

	@Test
	public void autoBuildRequirementOnTest() {
		PreferencesUtil.setAutoBuildingOff();
		Requirements requirements = getRequirements(AutoBuildingRequirementOnTest.class);
		requirements.fulfill();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
		requirements.cleanUp();
		assertFalse(PreferencesUtil.isAutoBuildingOn());
	}

	@Test
	public void autoBuildRequirementOffTest() {
		PreferencesUtil.setAutoBuildingOn();
		Requirements requirements = getRequirements(AutoBuildingRequirementOffTest.class);
		requirements.fulfill();
		assertFalse(PreferencesUtil.isAutoBuildingOn());
		requirements.cleanUp();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
	}

	@Test
	public void autoBuildRequirementOffWithoutCleanupTest() {
		PreferencesUtil.setAutoBuildingOff();
		Requirements requirements = getRequirements(AutoBuildingRequirementWithoutCleanupTest.class);
		requirements.fulfill();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
		requirements.cleanUp();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
	}

	private Requirements getRequirements(Class<?> klass) {
		RequirementsBuilder reqBuilder = new RequirementsBuilder();
		RequirementConfigurationSet configSet = new RequirementConfigurationSet();
		return reqBuilder.build(configSet, klass);
	}

}
