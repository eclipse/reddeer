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
package org.eclipse.reddeer.eclipse.test.condition;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.eclipse.condition.BrowserHasURL;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.ui.browser.BrowserEditor;
import org.eclipse.reddeer.eclipse.ui.browser.WebBrowserView;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class BrowserHasURLTest {

	private WebBrowserView browser;
	private BrowserEditor browserEditor;
	private static final String BAD_URL = "https://www.redhat.com/en";
	private static final String URL = "http://www.w3.org/";
	private static final String REG_EXP_URL = ".*(www.w3.org).*";
	
	@After
	public void tearDown() {
		if (browser != null) {
			browser.close();
			browser = null;
		}
		if (browserEditor != null) {
			browserEditor.close();
			browserEditor = null;
		}
	}
	
	public void openBrowserEditor() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					PlatformUI
						.getWorkbench()
						.getBrowserSupport()
						.createBrowser(
								IWorkbenchBrowserSupport.AS_EDITOR
								| IWorkbenchBrowserSupport.NAVIGATION_BAR
								| IWorkbenchBrowserSupport.LOCATION_BAR,
								null, "browser", "browser")
						.openURL(new URL(URL));
				} catch (PartInitException pie) {
					throw new EclipseLayerException("Unable to create browser editor", pie);
				} catch (MalformedURLException mue) {
					throw new EclipseLayerException("URL opening in browser is malformed", mue);
				}
			}
		});
		browserEditor = new BrowserEditor("browser");
		browserEditor.openPageURL(URL);
	}
	
	public void openInternalBrowserViaMenu() {
		browser = new WebBrowserView();
		browser.open();
		browser.openPageURL(URL);
	}
	
	@Test
	public void testBrowserEditor() {
		openBrowserEditor();
		BrowserHasURL condition = new BrowserHasURL(browserEditor, URL);
		assertTrue("Expected URL: " + URL + " but was: " + browserEditor.getPageURL(), condition.test());
		assertEquals("Browser url and one obtained from getResult are not the same", URL, condition.getResult());
		condition = new BrowserHasURL(browserEditor, new RegexMatcher(REG_EXP_URL));
		assertTrue(condition.test());
		assertEquals("Browser url and one obtained from getResult are not the same", URL, condition.getResult());
		condition = new BrowserHasURL(browserEditor, BAD_URL);
		assertFalse(condition.test());
		assertNotEquals(BAD_URL, condition.getResult());		
		condition = new BrowserHasURL(browserEditor, new WithTextMatcher(BAD_URL));
		assertFalse(condition.test());
		assertNotEquals(BAD_URL, condition.getResult());
	}
	
	@Test
	public void testWebBrowserView() {
		openInternalBrowserViaMenu();
		BrowserHasURL condition = new BrowserHasURL(browser, URL);
		assertTrue("Expected URL:" + URL + " but was: " + browser.getPageURL(), condition.test());
		assertEquals("Browser url and one obtained from getResult are not the same", URL, condition.getResult());
		condition = new BrowserHasURL(browser, new RegexMatcher(REG_EXP_URL));
		assertTrue(condition.test());
		assertEquals("Browser url and one obtained from getResult are not the same", URL, condition.getResult());
		condition = new BrowserHasURL(browser, BAD_URL);
		assertFalse(condition.test());
		assertNotEquals(BAD_URL, condition.getResult());		
		condition = new BrowserHasURL(browser, new WithTextMatcher(BAD_URL));
		assertFalse(condition.test());
		assertNotEquals(BAD_URL, condition.getResult());
	}
}
