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

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.ui.browser.BrowserEditor;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class BrowserEditorTitleTest {
	
	private static final String BROWSER_TITLE = "[test-new] Test Title:.+";

	@Before
	public void openBrowser() {
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
								null, BROWSER_TITLE, "b")
						.openURL(new URL("http://www.redhat.com"));
				} catch (PartInitException pie) {
					throw new EclipseLayerException("Unable to create browser editor", pie);
				} catch (MalformedURLException mue) {
					throw new EclipseLayerException("URL opening in browser is malformed", mue);
				}
			}
		});
	}

	@Test
	public void testEditor() {
		new DefaultEditor(BROWSER_TITLE);
		assertEquals("b", new BrowserEditor(BROWSER_TITLE).getTitleToolTip());
	}

}
