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
package org.jboss.reddeer.junit.test.execution;

import static org.junit.Assert.fail;

import org.jboss.reddeer.junit.execution.annotation.RunIf;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ExecutionTestRedDeerSuite.class)
public class TestMethodShouldRunTest {
	
	@Test
	public void testShouldRun1() {
		// PASSED
	}
	
	@Test
	@RunIf(conditionClass=ShouldRun.class)
	public void testShouldRun2() {
		// PASSED
	}
	
	@Test
	@RunIf(conditionClass=ShouldNotRun.class)
	public void testShouldNotRun1() {
		fail("Test was not supposed to run because run if condition was not met.");
	}
	
	@Test
	@Ignore
	public void testShouldNotRun2() {
		fail("Test was not supposed to run because @Ignore annotation is presented.");
	}
	
	@Test
	@RunIf(conditionClass=ShouldRun.class)
	@Ignore
	public void testShouldNotRun3() {
		fail("Test was not supposed to run because @Ignore annotation is presented, altough run condition was met.");
	}
	
	@Test
	@RunIf(conditionClass=ShouldNotRun.class)
	@Ignore
	public void testShouldNotRun4() {
		fail("Test was not supposed to run because @Ignore annotation is presented and run condition was not met.");
	}
}
