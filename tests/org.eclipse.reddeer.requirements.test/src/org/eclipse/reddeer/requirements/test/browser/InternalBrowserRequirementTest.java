/*******************************************************************************
 * Copyright (c) 2022 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package org.eclipse.reddeer.requirements.test.browser;

import static org.junit.Assert.assertEquals;

import java.lang.annotation.Annotation;

import org.eclipse.reddeer.direct.preferences.Preferences;
import org.eclipse.reddeer.eclipse.ui.browser.WebBrowserPreferencePage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.browser.InternalBrowserRequirement;
import org.eclipse.reddeer.requirements.browser.InternalBrowserRequirement.UseInternalBrowser;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author odockal
 *
 */
@RunWith(RedDeerSuite.class)
public class InternalBrowserRequirementTest {

	private static InternalBrowserRequirement internalRequirement;
	private static InternalBrowserRequirement externalRequirement;
	private static String BROWSER_PLUGIN = "org.eclipse.ui.browser";
	private static String BROWSER_KEY = "browser-choice";
	
	@BeforeClass
	public static void setup() {
		internalRequirement = new InternalBrowserRequirement();
		internalRequirement.setDeclaration(new UseInternalBrowser() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean cleanup() {
				return true;
			}
			
			@Override
			public String browserChoice() {
				return "0";
			}
		});
		
		externalRequirement = new InternalBrowserRequirement();
		externalRequirement.setDeclaration(new UseInternalBrowser() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean cleanup() {
				return false;
			}
			
			@Override
			public String browserChoice() {
				return "1";
			}
		});
	}
	
	@After
	public void cleanup() {
		Preferences.setDefault(BROWSER_PLUGIN, BROWSER_KEY);
	}
	
	@Test
	public void testInternalBrowser() {
		// default setup is to use external browser
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=577081
		// should be overridden by use of @UseInternalBrowser requirement
		internalRequirement.fulfill();
		assertEquals(true, internalBrowserUsed());
	}
	
	@Test
	public void testClenaupAction() {
		internalRequirement.fulfill();
		assertEquals("0", Preferences.get(BROWSER_PLUGIN, BROWSER_KEY));
		internalRequirement.cleanUp(); // clean up is turned on
		assertEquals("1", Preferences.get(BROWSER_PLUGIN, BROWSER_KEY));
		internalRequirement.fulfill();
		assertEquals("0", Preferences.get(BROWSER_PLUGIN, BROWSER_KEY));
		externalRequirement.cleanUp(); // clean up is turned off
		assertEquals("0", Preferences.get(BROWSER_PLUGIN, BROWSER_KEY));
	}
	
	@Test
	public void testExternalBrowserRequirement() {		
		internalRequirement.fulfill();
		assertEquals(true, internalBrowserUsed());
		externalRequirement.fulfill();
		assertEquals(false, internalBrowserUsed());
	}
	
	private boolean internalBrowserUsed() {
		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		WebBrowserPreferencePage page = new WebBrowserPreferencePage(dialog);
		
		dialog.select(page);
		boolean internalBrowserUsed = page.getInternalBrowserCheckBox().isSelected();
		
		dialog.cancel();
		return internalBrowserUsed;
	}
}
