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

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.ui.browser.BrowserEditor;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class BrowserEditorTest {

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
								null, "a", "b")
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
		new DefaultEditor("a");
		assertEquals("b", new BrowserEditor("a").getTitleToolTip());
	}

}
