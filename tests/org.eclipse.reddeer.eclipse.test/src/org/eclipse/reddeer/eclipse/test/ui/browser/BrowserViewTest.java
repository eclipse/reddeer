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
package org.eclipse.reddeer.eclipse.test.ui.browser;

import static org.junit.Assert.*;

import org.eclipse.reddeer.eclipse.ui.browser.WebBrowserView;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

//@Ignore	// https://github.com/jboss-reddeer/reddeer/issues/219
@RunWith(RedDeerSuite.class)
public class BrowserViewTest {
	protected static WebBrowserView browserView;

	protected static final String FIRST_PAGE = "https://www.google.cz/?q=redhat";
	protected static final String SECOND_PAGE = "https://www.google.cz/?q=jboss";
	
	@Before
	public void openBrowser(){
		browserView = new WebBrowserView();
		browserView.open();
	}
	
	@After
	public void closeBrowser(){
		browserView.close();
	}
	
	@Test
	public void testNavigation() {
		browserView.openPageURL(FIRST_PAGE);
		browserView.openPageURL(SECOND_PAGE);
		
		browserView.back();
		assertPageIsOpened(FIRST_PAGE);
		
		browserView.forward();
		assertPageIsOpened(SECOND_PAGE);
	}
	
	@Test
	public void testRefreshPage() {
		browserView.openPageURL(FIRST_PAGE);
		
		browserView.refreshPage();
		assertPageIsOpened(FIRST_PAGE);
	}
	
	private void assertPageIsOpened(String url) {
		assertTrue(browserView.getPageURL().contains(url));
	}
	
}
