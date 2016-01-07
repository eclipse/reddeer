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
package org.jboss.reddeer.swt.test.impl.label;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Label;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
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
}
