package org.jboss.reddeer.requirements.test.autobuilding;

import static org.junit.Assert.assertFalse;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.autobuilding.AutoBuildingRequirement.AutoBuilding;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky
 *
 */
@AutoBuilding(false)
@RunWith(RedDeerSuite.class)
public class AutoBuildingRequirementOffTest {

	@Test
	public void autoBuildRequirementOffTest() {
		assertFalse(new ShellMenu("Project", "Build Automatically").isSelected());
	}

}
