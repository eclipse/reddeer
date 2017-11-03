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
package org.eclipse.reddeer.swt.test.impl.label;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.core.matcher.WithIdMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.Label;
import org.eclipse.reddeer.swt.impl.label.DefaultLabel;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class DefaultLabelTest {

	@BeforeClass
	public static void openExplorer() {
		new WorkbenchView("RedDeer SWT Controls").open();
	}

	@Test
	public void defaultLabelTest() {
		Label defaultLabel = new DefaultLabel("Name:");
		assertTrue("Widget must be visible:", defaultLabel.isVisible());
	}

	@Test
	public void testFindingById() {
		Label label = new DefaultLabel(new WithIdMatcher("label1"));
		assertEquals("Name:", label.getText());
	}

}
