package org.jboss.reddeer.requirements.test.autobuilding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.direct.preferences.PreferencesUtil;
import org.jboss.reddeer.junit.internal.configuration.NullTestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
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
