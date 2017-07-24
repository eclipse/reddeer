/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.test.handler.ParameterizedHandler;
import org.eclipse.reddeer.swt.test.handler.ViewActionWithId;
import org.eclipse.reddeer.workbench.impl.menu.WorkbenchPartMenuItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ViewMenuItemTest {

	@Test
	public void testErrorLogMenu() {
		new WorkbenchView("Error Log").open();
		new WorkbenchPartMenuItem("Filters...").select();
		new DefaultShell("Log Filters");
		new PushButton("OK").click();
		new WaitWhile(new ShellIsAvailable("Log Filters"));
		new WaitWhile(new ShellIsAvailable("Progress Information"));
	}

	@Test
	public void testCheckStyledMenus() {
		new WorkbenchView("Error Log").open();
		WorkbenchPartMenuItem filter = new WorkbenchPartMenuItem("Show text filter");
		boolean selected = filter.isSelected();
		if (selected) {
			filter.select();
			assertFalse(filter.isSelected() == selected);
			try {
				new DefaultText();
				fail();
			} catch (CoreLayerException ex) {
				// search text field is hidden = ok
			}
		} else {
			filter.select();
			assertFalse(filter.isSelected() == selected);
			try {
				new DefaultText();
			} catch (CoreLayerException ex) {
				fail();
			}
		}
	}

	@Test
	public void parameterizedViewMenuItemTest() {
		// open View
		new WorkbenchView("RedDeer SWT").open();

		// click menu item A
		new WorkbenchPartMenuItem("submenu", "parameterizedMenuA").select();
		assertTrue(ParameterizedHandler.isToggledA());
		assertFalse(ParameterizedHandler.isToggledB());

		// click menu item B
		new WorkbenchPartMenuItem("submenu", "parameterizedMenuB").select();
		assertTrue(ParameterizedHandler.isToggledA());
		assertTrue(ParameterizedHandler.isToggledB());
	}

	@Test
	public void actionWithIdViewMenuTest() {
		// open View
		new WorkbenchView("RedDeer SWT").open();
		
		// click Action With Id Menu
		assertFalse(ViewActionWithId.isToggled());
		new WorkbenchPartMenuItem("View Action with ID").select();
		new WaitUntil(new ViewWithActionIdIsToggled());
		assertTrue(ViewActionWithId.isToggled());
	}

	private class ViewWithActionIdIsToggled extends AbstractWaitCondition {

		@Override
		public boolean test() {
			return ViewActionWithId.isToggled();
		}

		@Override
		public String description() {
			return "ViewWithActionIsToggled.";
		}

	}

}
