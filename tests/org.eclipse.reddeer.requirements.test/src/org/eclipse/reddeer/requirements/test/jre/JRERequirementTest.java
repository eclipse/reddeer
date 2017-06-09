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
package org.eclipse.reddeer.requirements.test.jre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.reddeer.eclipse.jdt.debug.ui.jres.JREsPreferencePage;
import org.eclipse.reddeer.eclipse.jdt.ui.preferences.JREItem;
import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.jre.JRERequirement;
import org.eclipse.reddeer.requirements.jre.JRERequirement.JRE;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.Test;
import org.junit.runner.RunWith;

@JRE(1.7)
@RunWith(RedDeerSuite.class)
public class JRERequirementTest {

	@InjectRequirement
	JRERequirement requirement;

	@Test
	public void testConfigurationValues() {
		assertEquals("testName", requirement.getConfiguration().getName());
		assertEquals("1.7", requirement.getConfiguration().getVersion(), "1.7");
		File file = new File(requirement.getPath());
		assertTrue(file.exists());
		assertTrue(file.isDirectory());
	}
	
	@Test
	public void testConfiguredValues(){
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		JREsPreferencePage page = new JREsPreferencePage();
		dialog.open();
		dialog.select(page);
		List<JREItem> jres = page.getJREs();
		assertTrue(matchJREs(jres, requirement.getConfiguration().getName(), requirement.getPath()));
		dialog.cancel();
	}
	
	
	/**
	 * 
	 * @return returns true if any of items matches name and path.
	 */
	private boolean matchJREs(List<JREItem> items, String name, String path){
		String absolutePath = new File(path).getAbsolutePath();
		for (JREItem item : items) {
			String absolutePath2 = new File(item.getLocation()).getAbsolutePath();
			if (item.getName().equals(name) && absolutePath.equals(absolutePath2)){
				return true;
			}
		}
		return false;
	}
}
