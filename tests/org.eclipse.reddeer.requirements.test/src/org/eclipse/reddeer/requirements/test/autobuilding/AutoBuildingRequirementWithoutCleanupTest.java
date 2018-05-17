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

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.autobuilding.AutoBuildingRequirement.AutoBuilding;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Andrej Podhradsky
 *
 */
@AutoBuilding(value = true, cleanup=false)
@RunWith(RedDeerSuite.class)
public class AutoBuildingRequirementWithoutCleanupTest {

	@Test
	public void autoBuildRequirementOnTest() {
		assertTrue(new ShellMenuItem("Project", "Build Automatically").isSelected());
	}

}
