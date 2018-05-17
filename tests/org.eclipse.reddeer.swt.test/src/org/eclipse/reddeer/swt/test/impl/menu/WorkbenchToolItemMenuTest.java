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
package org.eclipse.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTooltipTextMatcher;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.swt.api.ToolItem;
import org.eclipse.reddeer.swt.impl.menu.ToolItemMenuItem;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class WorkbenchToolItemMenuTest {
	
	@Test
	public void WorkbenchToolItemMultiLayeredMenuTest() {
		ToolItem ti = new DefaultToolItem(new WorkbenchShell(),
				new WithTooltipTextMatcher(new RegexMatcher("Run.*")));
		ToolItemMenuItem menu = new ToolItemMenuItem(ti, "Run As", "(none applicable)");
		assertEquals("(none applicable)", menu.getText());
	}

}
