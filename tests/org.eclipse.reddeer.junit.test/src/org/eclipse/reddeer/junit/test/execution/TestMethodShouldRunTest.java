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
package org.eclipse.reddeer.junit.test.execution;

import static org.junit.Assert.fail;

import org.eclipse.reddeer.junit.execution.annotation.RunIf;
import org.eclipse.reddeer.junit.execution.annotation.RunIf.Operation;
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
	
	@Test
	@RunIf(conditionClass={ShouldRun.class, ShouldRun.class}, operation=Operation.AND)
	public void testShouldRun3() {
		// PASSED
	}
	
	@Test
	@RunIf(conditionClass={ShouldRun.class, ShouldRun.class}, operation=Operation.OR)
	public void testShouldRun4() {
		// PASSED
	}
	
	@Test
	@RunIf(conditionClass={ShouldRun.class, ShouldNotRun.class}, operation=Operation.OR)
	public void testShouldRun5() {
		// PASSED
	}
	
	@Test
	@RunIf(conditionClass={ShouldRun.class, ShouldNotRun.class}, operation=Operation.AND)
	public void testShouldNotRun5() {
		fail("Test was not supposed to run because run if condition was not met.");
	}
	
	@Test
	@RunIf(conditionClass={ShouldNotRun.class, ShouldNotRun.class}, operation=Operation.OR)
	public void testShouldNotRun6() {
		fail("Test was not supposed to run because run if condition was not met.");
	}
	
	@Test
	@RunIf(conditionClass={ShouldNotRun.class, ShouldNotRun.class}, operation=Operation.AND)
	public void testShouldNotRun7() {
		fail("Test was not supposed to run because run if condition was not met.");
	}

	@Test
	@RunIf(conditionClass={ShouldRun.class, ShouldNotRun.class})
	public void testShouldNotRun8() {
		fail("Test was not supposed to run because run if condition was not met.");
	}
}
