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
package org.eclipse.reddeer.workbench.test.view.impl;

import static org.junit.Assert.*;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.api.View;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
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
