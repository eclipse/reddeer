/*******************************************************************************
 * Copyright (c) 2022 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package org.eclipse.reddeer.eclipse.test.ui.browser;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.direct.preferences.Preferences;
import org.eclipse.reddeer.eclipse.ui.browser.WebBrowserPreferencePage;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author odockal
 *
 */
public class WebBrowserPreferencePageTest {

	private static String BROWSER_PLUGIN = "org.eclipse.ui.browser";
	private static String BROWSER_KEY = "browser-choice";
	
	private WebBrowserPreferencePage page;
	private WorkbenchPreferenceDialog dialog;
	
	@Before
	public void setup() {
		dialog = new WorkbenchPreferenceDialog();
		page = new WebBrowserPreferencePage(dialog);
		dialog.open();
		
		dialog.select(page);
	}
	
	@Test
	public void testBrowserPreferencePage() {
		page.toggleExternalBrowser();
		page.apply();
		assertEquals("1", Preferences.get(BROWSER_PLUGIN, BROWSER_KEY));
		page.toggleInternalBrowser();
		page.apply();
		assertEquals("0", Preferences.get(BROWSER_PLUGIN, BROWSER_KEY));
	}	
	
	@After
	public void cleanup() {
		dialog.cancel();
	}
	
}
