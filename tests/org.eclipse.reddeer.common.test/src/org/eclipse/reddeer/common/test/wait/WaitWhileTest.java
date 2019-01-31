/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.common.test.wait;

import static org.eclipse.reddeer.common.test.wait.CustomWaitCondition.sleep;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.common.condition.WaitCondition;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author odockal
 *
 */
public class WaitWhileTest {

	private WaitCondition trueCondition;
	
	@Before
	public void setupCondition() {
		trueCondition = new CustomWaitCondition(true, 5, () -> sleep(1000));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_NullCondition() {
		new WaitWhile(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_NullTimePeriod() {
		new WaitWhile(trueCondition, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_IllegalTimePeriod() {
		new WaitWhile(trueCondition, TimePeriod.getCustom(-1));
	}
	
	@Test(expected=WaitTimeoutExpiredException.class)
	public void test_ThrowingException() {
		new WaitWhile(trueCondition, TimePeriod.SHORT);
	}
	
	@Test
	public void test_NotThrowingException() {
		try {
			new WaitWhile(trueCondition, TimePeriod.MEDIUM, false);
		} catch (WaitTimeoutExpiredException exc) {
			fail("WaitTimeoutExpiredException should not be thrown");
		}
	}
	
	@Test
	public void test_WaitWhileFulFilled() {
		try {
			new WaitWhile(trueCondition, TimePeriod.DEFAULT);
		} catch (WaitTimeoutExpiredException exc) {
			fail("WaitTimeoutExpiredException should not be thrown");
		}
	}
	
}
