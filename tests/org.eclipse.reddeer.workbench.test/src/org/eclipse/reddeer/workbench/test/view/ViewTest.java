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
package org.eclipse.reddeer.workbench.test.view;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.workbench.api.View;
import org.eclipse.reddeer.workbench.exception.WorkbenchLayerException;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
import org.eclipse.reddeer.workbench.test.Activator;
import org.eclipse.reddeer.workbench.test.ui.views.DirtyLabelView;
import org.eclipse.reddeer.workbench.test.ui.views.LabelView;

import static org.junit.Assert.*;
import java.util.function.Supplier;

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
	
	@Test(expected = WorkbenchLayerException.class)
	public void testInitializeRegisteredViewWithRegex() {
		new WorkbenchView("Workbench Test.*");
	}
	
	@Test
	public void getTitleTest(){
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		assertEquals("Workbench Test", customView.getTitle());
	}
	
	@Test
	public void getTitleToolTipTest(){
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		assertEquals(LabelView.TOOLTIP, customView.getTitleToolTip());
	}
	
	@Test
	public void testActiveView(){
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		
		assertTrue(customView.isActive());
		View markersView = new WorkbenchView("Markers");
		markersView.open();
		assertFalse(customView.isActive());
		customView.activate();
		assertTrue(customView.isActive());
		customView.close();
	}
	
	@Test
	public void testClosedGetTitle(){
		testClosedViewMethod(new WorkbenchView("Workbench Test")::getTitle);
	}
	
	@Test
	public void testClosedGetToolTip(){
		testClosedViewMethod(new WorkbenchView("Workbench Test")::getTitleToolTip);
	}
	
	@Test
	public void testClosedGetImage(){
		testClosedViewMethod(new WorkbenchView("Workbench Test")::getTitleImage);
	}
	
	@Test
	public void testClosedIsActive(){
		testClosedViewMethod(new WorkbenchView("Workbench Test")::isActive);
	}
	
	@Test
	public void testClosedActivate(){
		try{
			new WorkbenchView("Workbench Test").activate();
			fail("Exception should have been thrown because view is not open");
		} catch (WorkbenchLayerException e) {
			e.getMessage().contains("is not open");
		}
	}
	
	
	private void testClosedViewMethod(Supplier<?> method){
		try{
			method.get();
			fail("Exception should have been thrown because view is not open");
		} catch (WorkbenchLayerException e) {
			e.getMessage().contains("is not open");
		}
	}
	
	@Test
	public void testViewIsOpen(){
		View customView = new WorkbenchView("Workbench Test");
		assertFalse(customView.isOpen());
		customView.open();
		assertTrue(customView.isOpen());
		customView.close();
		assertFalse(customView.isOpen());
	}
	
	@Test
	public void getTitleImage(){
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		assertNotNull(customView.getTitleImage());
		assertEquals(Activator.getDefault().getImageRegistry().get(Activator.REDDEER_ICON), customView.getTitleImage());
	}

	@Test
	public void testOpenAndCloseView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();
	}

	@Test
	public void testOpenAndCloseViewFullPath() {
		View customView = new WorkbenchView("RedDeer Test Workbench", "Workbench Test");
		customView.open();
		customView.close();
	}

	@Test
	public void testOpenClosedView() {
		View customView = new WorkbenchView("Workbench Test");
		customView.open();
		customView.close();

		customView.open();
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

		markersView.close();
	}

	/* Tests with a dirty view */
	
	@After
	public void restoreDefaultDirtyValue() {
		setDefaultDirtyValue(DirtyLabelView.DEFAULT_DIRTY_VALUE);
		View customView = new WorkbenchView("Workbench Test");
		if(customView.isOpen()){
			customView.close();
		}
	}

	@Test
	public void testOpenAndCloseDirtyView() {
		setDefaultDirtyValue(true);
		View customView = new WorkbenchView("Workbench Dirty Test");
		customView.open();
		customView.close();
	}
	
	@Test
	public void viewAsReferencedComposite(){
		View customView = new WorkbenchView("Workbench Dirty Test");
		customView.open();
		//view used as referenced composite
		new LabeledText(customView, "Test field: ");
	}

	@Test
	public void testActivateDirtyAndNonDirtyView() {
		View customView = new WorkbenchView("Workbench Dirty Test");
		customView.open();
		new LabeledText("Test field: ").setText("hello");
		customView.activate();
		new ShellMenuItem("File", "Save").select();
		customView.activate();
		customView.close();
	}

	private void setDefaultDirtyValue(boolean isDirty) {
		System.setProperty(DirtyLabelView.DEFAULT_DIRTY_PROPERTY, String.valueOf(isDirty));
	}

}