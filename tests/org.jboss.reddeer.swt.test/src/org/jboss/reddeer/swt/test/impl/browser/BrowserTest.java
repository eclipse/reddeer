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
package org.jboss.reddeer.swt.test.impl.browser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.test.utils.LabelTestUtils;
import org.junit.Ignore;
import org.junit.Test;

public class BrowserTest extends SWTLayerTestCase {

	private static final String BROWSER_LABEL = "Test browser:";

	private static TestBrowser testBrowser;

	@Override
	protected void createControls(Shell shell) {
		LabelTestUtils.createLabel(shell, BROWSER_LABEL);
		testBrowser = new TestBrowser(shell, SWT.NONE);
		testBrowser.setSize(400, 400);

		// nasty hack because of SWT.IE bug:
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=465822
		if (RunningPlatform.isWindows()) {
			testBrowser.setUrl(testBrowser.getUrl());
		}
	}

	@Test
	public void evaluateTest() {
		String evaluatedString = (String) new InternalBrowser(0).evaluate("testEvaluate();return \"test\";");
		assertTrue(testBrowser.wasEvaluated());
		assertEquals("test", evaluatedString);
	}

	@Test
	public void executeTest() {
		new InternalBrowser().execute("testExecute()");
		assertTrue(testBrowser.wasExecuted());
	}

	@Test
	@Ignore
	public void findBrowserByLabel() {
		new InternalBrowser(BrowserTest.BROWSER_LABEL);
	}

	@Test
	public void findBrowserByIndex() {
		new InternalBrowser(0);
	}

	@Test(expected = CoreLayerException.class)
	public void findNonExistingBrowserByLabel() {
		new InternalBrowser("@#NON_EXISITNG_LABEL%$");
	}

	@Test(expected = CoreLayerException.class)
	public void findNonExistingBrowserByIndex() {
		new InternalBrowser(1);
	}

	@Test
	public void navigate() {
		Browser browser = new InternalBrowser(0);
		browser.setURL("http://www.eclipse.org/swt/snippets/");
		final String snippetsPageContent = "<title>SWT Snippets</title>";
		assertTrue("Browser has to contain text '" + snippetsPageContent + "' but it doesn't",
				browser.getText().contains(snippetsPageContent));
		browser.setURL("http://www.eclipse.org/swt/widgets/");
		final String widgetsPageContent = "<title>SWT Widgets</title>";
		assertTrue("Browser has to contain text '" + widgetsPageContent + "' but it doesn't",
				browser.getText().contains(widgetsPageContent));
		browser.back();
		assertTrue("Browser has to contain text '" + snippetsPageContent + "' but it doesn't",
				browser.getText().contains(snippetsPageContent));
		browser.forward();
		assertTrue("Browser has to contain text '" + widgetsPageContent + "' but it doesn't",
				browser.getText().contains(widgetsPageContent));
	}

	private class TestBrowser extends org.eclipse.swt.browser.Browser {

		private boolean executed = false;
		private boolean evaluated = false;

		@SuppressWarnings("unused")
		public TestBrowser(Composite parent, int style) {
			super(parent, style);

			BrowserFunction executeFunction = new BrowserFunction(this, "testExecute") {
				@Override
				public Object function(Object[] arguments) {
					executed = true;
					return null;
				};
			};

			BrowserFunction evaluateFunction = new BrowserFunction(this, "testEvaluate") {
				@Override
				public Object function(Object[] arguments) {
					evaluated = true;
					return null;
				}
			};
		}

		@Override
		protected void checkSubclass() {

		}

		public boolean wasExecuted() {
			return executed;
		}

		public boolean wasEvaluated() {
			return evaluated;
		}
	}
}
