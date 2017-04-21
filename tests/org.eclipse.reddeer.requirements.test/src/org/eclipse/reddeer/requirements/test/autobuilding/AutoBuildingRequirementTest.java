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
package org.eclipse.reddeer.requirements.test.autobuilding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.junit.internal.configuration.NullTestRunConfiguration;
import org.eclipse.reddeer.junit.internal.configuration.TestRunConfiguration;
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
		assertTrue(requirements.canFulfill());
		requirements.fulfill();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
		requirements.cleanUp();
		assertFalse(PreferencesUtil.isAutoBuildingOn());
	}

	@Test
	public void autoBuildRequirementOffTest() {
		PreferencesUtil.setAutoBuildingOn();
		Requirements requirements = getRequirements(AutoBuildingRequirementOffTest.class);
		assertTrue(requirements.canFulfill());
		requirements.fulfill();
		assertFalse(PreferencesUtil.isAutoBuildingOn());
		requirements.cleanUp();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
	}

	@Test
	public void autoBuildRequirementOffWithoutCleanupTest() {
		PreferencesUtil.setAutoBuildingOff();
		Requirements requirements = getRequirements(AutoBuildingRequirementWithoutCleanupTest.class);
		assertTrue(requirements.canFulfill());
		requirements.fulfill();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
		requirements.cleanUp();
		assertTrue(PreferencesUtil.isAutoBuildingOn());
	}

	private Requirements getRequirements(Class<?> klass) {
		RequirementsBuilder reqBuilder = new RequirementsBuilder();
		TestRunConfiguration config = new NullTestRunConfiguration();
		return reqBuilder.build(klass, config.getRequirementConfiguration(), config.getId());
	}

}
