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
package org.jboss.reddeer.eclipse.test.jdt.ui.junit;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.jdt.ui.junit.JUnitView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@RunWith(RedDeerSuite.class)
public class JUnitViewTest {

	@BeforeClass
	public static void clearAllTestRuns() {
		JUnitView junitView = new JUnitView();
		junitView.open();
		junitView.removeAllRuns();
	}

	@Test
	public void getRunsTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals("0/0", jUnitView.getRunStatus());
	}

	@Test
	public void getErrorsTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals(0, jUnitView.getNumberOfErrors());
	}

	@Test
	public void getFailuresTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals(0, jUnitView.getNumberOfFailures());
	}

	@Test
	public void getViewStatusTest() {
		JUnitView jUnitView = new JUnitView();
		jUnitView.open();
		assertEquals("", jUnitView.getViewStatus());
	}
}
