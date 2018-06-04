/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.ui.test.run;

import static org.junit.Assert.fail;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.ui.test.wizard.RedDeerWizardTestCase;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class covers testing of presence of Run As/Debug as -> RedDeer Test actions 
 * for workbench shell menu Run 
 * and context menu option called over test class. 
 * @author odockal
 *
 */
public class RedDeerTestClassRunMenuOptionsTest extends RedDeerWizardTestCase {
	
	@BeforeClass
	public static void setup() {
		projectName = PLUGIN_ID;
		createRedDeerPluginProject();
	}
	
	@Override
	public String getWizardText() {
		return "New RedDeer Test Plugin";
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testContextMenu() {
		DefaultProject project = getProject(PLUGIN_ID);
		project.getProjectItem(EXAMPLE_TEST_CLASS_PATH).select();
		try {
			new ContextMenuItem(new DefaultTree(),
				new WithTextMatcher(new RegexMatcher(".*Run As.*")), 
				new WithTextMatcher(new RegexMatcher(".*RedDeer Test.*")));
		} catch (CoreLayerException exc) {
			fail("ContextMenuItem Run As -> RedDeer Test is missing");
		}
		
		try {
			new ContextMenuItem(new DefaultTree(),
				new WithTextMatcher(new RegexMatcher(".*Debug As.*")), 
				new WithTextMatcher(new RegexMatcher(".*RedDeer Test.*")));
		} catch (CoreLayerException exc) {
			fail("ContextMenuItem Debug As -> RedDeer Test is missing");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testShellMenu() {
		DefaultProject project = getProject(PLUGIN_ID);
		project.getProjectItem(EXAMPLE_TEST_CLASS_PATH).open();
		try {
			new ShellMenuItem(new WithTextMatcher("Run"), 
					new WithTextMatcher("Run As"), 
					new WithTextMatcher(new RegexMatcher(".*RedDeer Test.*")));
		} catch (CoreLayerException exc) {
			fail("ShellMenuItem Run -> Run As -> RedDeer Test is missing");
		}
		
		try {
			new ShellMenuItem(new WithTextMatcher("Run"), 
					new WithTextMatcher("Debug As"), 
					new WithTextMatcher(new RegexMatcher(".*RedDeer Test.*")));
		} catch (CoreLayerException exc) {
			fail("ShellMenuItem Debug As -> RedDeer Test is missing");
		}
	}
}
