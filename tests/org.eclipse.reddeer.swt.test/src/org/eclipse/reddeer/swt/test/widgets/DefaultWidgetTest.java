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
package org.eclipse.reddeer.swt.test.widgets;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ShellMenu;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.widgets.DefaultWidget;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultWidgetTest extends DefaultWidgetAndControlBase {

	@Test
	public void defaultWidgetTest() {
		DefaultShell shell = new DefaultShell(SHELL_TITLE);
		PushButton button = new PushButton(shell);
		ShellMenu menu = new ShellMenu(shell);

		DefaultWidget<Button> widgetFromControl = new DefaultWidget<Button>(this.control);
		assertEquals(widgetFromControl.getSWTWidget(), button.getSWTWidget());

		DefaultWidget<Menu> widgetFromWidget = new DefaultWidget<Menu>(this.widget);
		assertEquals(widgetFromWidget.getSWTWidget(), menu.getSWTWidget());
	}
}
