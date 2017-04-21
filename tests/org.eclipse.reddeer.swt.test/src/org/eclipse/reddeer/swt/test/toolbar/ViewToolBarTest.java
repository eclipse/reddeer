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
package org.eclipse.reddeer.swt.test.toolbar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTooltipTextMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.ToolBar;
import org.eclipse.reddeer.swt.api.ToolItem;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.impl.toolbar.ViewToolBar;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
import org.eclipse.tools.reddeer.swt.test.model.TestModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ViewToolBarTest {

	@Before
	public void prepare() {
		new WorkbenchView("RedDeer SWT").open();
	}
	
	@Test
	public void testViewToolBar() {
		new WorkbenchShell();
		ToolBar t = new ViewToolBar();
		assertNotNull(t);
	}
	
	@Test
	public void testToolItemInViewToolBarFound() {
		ToolItem i = new DefaultToolItem("RedDeer SWT ViewToolItem");
		assertEquals("RedDeer SWT ViewToolItem", i.getToolTipText());
	}

	@Test
	public void testToolItemInViewToolBarClicked() {
		ToolItem i = new DefaultToolItem("RedDeer SWT ViewToolItem");
		i.click();		
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());		
	}

	@Test
	public void testToolItemInViewToolBarRegexClicked() {
		WithTooltipTextMatcher rm = new WithTooltipTextMatcher(
				new RegexMatcher("RedDeer SWT View.*"));
		ToolItem i = new DefaultToolItem(rm);
		i.click();
		assertTrue("ToolItem should be clicked", TestModel.getClickedAndReset());		
	}	
}
