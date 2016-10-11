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
package org.jboss.reddeer.workbench.test.view;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.workbench.api.View;
import org.jboss.reddeer.workbench.exception.WorkbenchLayerException;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.jboss.reddeer.workbench.test.ui.views.DirtyLabelView;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ViewTest {

	@Test
	public void testInitializeRegisteredView() {
		new WorkbenchView("Workbench Test");
	}

	@Test(expected = WorkbenchLayerException.class)
	public void testInitializeNonregisteredView() {
		new WorkbenchView("Nonexist View");
	}

	@Test
	public void testOpenAndCloseView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();
	}

	@Test
	public void testOpenAndCloseViewFullPath() {
		View customView = new WorkbenchView("Red Deer Test Workbench", "Workbench Test");
		customView.open();
		customView.close();
	}

	@Test
	public void testOpenClosedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();

		customView.open();
		customView.close();
	}

	@Test
	public void testCloseNonFocusedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		View markersView = new WorkbenchView("Markers");
		markersView.open();

		customView.close();
		markersView.close();
	}

	@Test(expected = WorkbenchLayerException.class)
	public void testCloseNoninitializedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.close();
	}

	@Test(expected = WorkbenchLayerException.class)
	public void testActivateNoninitializedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.activate();
	}

	@Test
	public void testActivateNonFocusedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();

		View markersView = new WorkbenchView("Markers");
		markersView.open();

		customView.activate();

		customView.close();
		markersView.close();
	}

	@Test
	public void testMaximizeMinimalizedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.minimize();
		customView.maximize();
		customView.restore();
		customView.close();
	}

	/* Tests with a dirty view */
	
	@After
	public void restoreDefaultDirtyValue() {
		setDefaultDirtyValue(DirtyLabelView.DEFAULT_DIRTY_VALUE);
	}

	@Test
	public void testOpenAndCloseDirtyView() {
		setDefaultDirtyValue(true);
		View customView = new WorkbenchView("Workbench Dirty Test");
		customView.open();
		customView.close();
	}

	@Test
	public void testActivateDirtyAndNonDirtyView() {
		View customView = new WorkbenchView("Workbench Dirty Test");
		customView.open();
		new LabeledText("Test field: ").setText("hello");
		customView.activate();
		new ShellMenu("File", "Save").select();
		customView.activate();
		customView.close();
	}

	private void setDefaultDirtyValue(boolean isDirty) {
		System.setProperty(DirtyLabelView.DEFAULT_DIRTY_PROPERTY, String.valueOf(isDirty));
	}

}