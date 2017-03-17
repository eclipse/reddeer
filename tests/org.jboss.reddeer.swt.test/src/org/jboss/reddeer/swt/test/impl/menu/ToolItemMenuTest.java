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
package org.jboss.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.matcher.WithTooltipTextMatcher;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.impl.menu.ToolItemMenu;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ToolItemMenuTest {

	@Test
	public void ViewToolItemMenuTest() {
		new ConsoleView().open();
		ToolItem item = new DefaultToolItem("Open Console");
		ToolItemMenu menu = new ToolItemMenu(item, new RegexMatcher(
				".*Console View"));
		menu.select();
		new ProblemsView().open();
		menu.select();
	}

	@Test
	public void WorkbenchToolItemMultiLayeredMenuTest() {
		ToolItem ti = new DefaultToolItem(new WorkbenchShell(),
				new WithTooltipTextMatcher(new RegexMatcher("Run.*")));
		ToolItemMenu menu = new ToolItemMenu(ti, "Run As", "(none applicable)");
		assertEquals("(none applicable)", menu.getText());
	}
}