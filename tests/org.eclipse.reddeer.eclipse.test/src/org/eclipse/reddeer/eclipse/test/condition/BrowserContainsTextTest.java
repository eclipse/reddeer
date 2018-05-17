/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
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

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.eclipse.condition.BrowserContainsText;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.ui.browser.BrowserEditor;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.browser.InternalBrowser;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class BrowserContainsTextTest {

	private BrowserEditor browserEditor;
	private static final String URL = "http://www.w3.org/";
	private static final String URL_ECLIPSE = "http://www.eclipse.org/";
	private static final String TEXT_TO_TEST = "World Wide Web Consortium (W3C)";
	private static final String TEXT_TO_TEST_ECLIPSE = "eclipse";
	
	@Before
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
	
	@After
	public void tearDown() {
		if (browserEditor != null) {
			browserEditor.close();
			browserEditor = null;
		}
	}
	
	@Test
	public void testText() {
		BrowserContainsText condition = new BrowserContainsText(TEXT_TO_TEST);
		assertTrue(condition.test());
	}
	
	@Test
	public void testConstructorWithInternalBrowser() {
		InternalBrowser browser = new InternalBrowser();
		browser.setURL(URL_ECLIPSE);
		BrowserContainsText condition = new BrowserContainsText(browser, TEXT_TO_TEST_ECLIPSE);
		assertTrue(condition.test());
	}

}
