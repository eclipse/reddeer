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

import org.jboss.reddeer.eclipse.jdt.junit.ui.TestRunnerViewPart;
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
		TestRunnerViewPart junitView = new TestRunnerViewPart();
		junitView.open();
		junitView.removeAllRuns();
	}

	@Test
	public void getRunsTest() {
		TestRunnerViewPart jUnitView = new TestRunnerViewPart();
		jUnitView.open();
		assertEquals("0/0", jUnitView.getRunStatus());
	}

	@Test
	public void getErrorsTest() {
		TestRunnerViewPart jUnitView = new TestRunnerViewPart();
		jUnitView.open();
		assertEquals(0, jUnitView.getNumberOfErrors());
	}

	@Test
	public void getFailuresTest() {
		TestRunnerViewPart jUnitView = new TestRunnerViewPart();
		jUnitView.open();
		assertEquals(0, jUnitView.getNumberOfFailures());
	}

	@Test
	public void getViewStatusTest() {
		TestRunnerViewPart jUnitView = new TestRunnerViewPart();
		jUnitView.open();
		assertEquals("", jUnitView.getViewStatus());
	}
}
