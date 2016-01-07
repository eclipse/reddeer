/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.test.ui.browser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jboss.reddeer.eclipse.ui.browser.BrowserView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

//@Ignore	// https://github.com/jboss-reddeer/reddeer/issues/219
@RunWith(RedDeerSuite.class)
public class BrowserViewTest {
	protected static BrowserView browserView;

	protected static final String FIRST_PAGE = "https://www.google.cz/?q=redhat";
	protected static final String SECOND_PAGE = "https://www.google.cz/?q=jboss";
	
	@Before
	public void openBrowser(){
		browserView = new BrowserView();
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
		assertThat(browserView.getPageURL(), is(url));
	}
	
}
