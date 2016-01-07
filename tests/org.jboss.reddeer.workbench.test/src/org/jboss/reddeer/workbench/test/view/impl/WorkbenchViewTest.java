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
package org.jboss.reddeer.workbench.test.view.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WorkbenchViewTest  {

	private final static String MARKERS_VIEW_TITLE = "Markers";
	private final static String PROJECT_EXPLORER_VIEW_TITLE = "Project Explorer";

	private View markersView;
	private View projectExplorerView;

	@Before
	public void setUp() {
		markersView = new WorkbenchView(MARKERS_VIEW_TITLE);
		try {
			markersView.close();
		} catch (WorkbenchLayerException uoe) {
			// Markes view is not opened, do nothing here
		}

		projectExplorerView = new WorkbenchView(PROJECT_EXPLORER_VIEW_TITLE);
		try {
			projectExplorerView.close();
		} catch (WorkbenchLayerException uoe) {
			// Project Explorer view is not opened, do nothing here
		}
	}

	@Test
	public void testNotActiveView() {

		markersView.open();
		markersView.close();
		
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testIsActiveView() {

		markersView.open();
		assertTrue(markersView.isActive());
		
		projectExplorerView.open();
		assertFalse(markersView.isActive());
		
	}

	@Test
	public void testActiveView() {

		projectExplorerView.open();
		projectExplorerView.close();
		
	}
	
	@Test(expected=WorkbenchLayerException.class)
	public void testCloseNonInstantiatedView() {
		
		markersView.close();
		
	}
	
	@Test(expected=WorkbenchLayerException.class)
	public void testCloseClosedView() {
		
		markersView.open();
		markersView.close();
		markersView.close();
		
	}
}
