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

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;
import static org.eclipse.reddeer.common.wait.WaitProvider.waitWhile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.eclipse.reddeer.common.test.wait.CustomWaitCondition.sleep;

import org.eclipse.reddeer.common.condition.WaitCondition;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWrapper;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author odockal
 *
 */
public class GroupWaitTest {
	
	private WaitCondition falseCondition;
	private WaitCondition trueCondition;
	private WaitWrapper waitUntil;
	private WaitWrapper waitWhile;
	
	@Before
	public void setup() {
		falseCondition = new CustomWaitCondition(false, 3, () -> sleep(1000));
		trueCondition = new CustomWaitCondition(true, 3, () -> sleep(1000));
		waitUntil = waitUntil(falseCondition);
		waitWhile = waitWhile(trueCondition);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_groupWaitNullWaitCondition() {
		new GroupWait(TimePeriod.SHORT, (WaitWrapper[]) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_groupWaitValidAndNullWaitCondition() {
		new GroupWait(waitUntil, null);
	}
	
	@Test
	public void test_groupWaitTimeoutDefault() {
		try {
			 new GroupWait(waitUntil);
			 assertTrue(waitUntil.getWaitCondition().getResult());
		} catch (WaitTimeoutExpiredException waitExc) {
			 fail("GroupWait should have not thrown WaitTimeExpiredException");
		}
	}
	
	@Test
	public void test_groupWaitTimeoutMedium() {
		try {
			 new GroupWait(TimePeriod.SHORT, waitUntil);
			 fail("GroupWait should have thrown WaitTimeExpiredException");
		} catch (WaitTimeoutExpiredException waitExc) {
			assertFalse(waitUntil.getWaitCondition().getResult());
		}
	}

	@Test
	public void test_groupWaitWaitUntilAndWhileFulfilled() {
		waitUntil = waitUntil(new CustomWaitCondition(false, 3, () -> sleep(1000)));
		waitWhile = waitWhile(new CustomWaitCondition(true, 5, () -> sleep(1000)));		
		try {
			 new GroupWait(waitUntil, waitWhile);
			 assertTrue(waitUntil.getWaitCondition().getResult());
			 assertTrue(waitWhile.getWaitCondition().getResult());
		} catch (WaitTimeoutExpiredException waitExc) {
			fail("GroupWait should have not thrown WaitTimeExpiredException");
		}
	}
	
	@Test
	public void test_groupWaitWaitWhileFailed() {
		waitUntil = waitUntil(new CustomWaitCondition(false, 1, () -> sleep(1000)));
		waitWhile = waitWhile(new CustomWaitCondition(true, 5, () -> sleep(1000)));		
		try {
			 new GroupWait(TimePeriod.MEDIUM, waitUntil, waitWhile);
		} catch (WaitTimeoutExpiredException waitExc) {
			assertTrue(waitUntil.getWaitCondition().getResult());
			assertFalse(waitWhile.getWaitCondition().getResult());
		}
	}
	
	@Test(expected=WaitTimeoutExpiredException.class)
	public void test_groupWaitBothConditionsFailed() {
		new GroupWait(TimePeriod.MEDIUM, waitUntil, waitWhile);
	}
	
	@Test(expected=WaitTimeoutExpiredException.class)
	public void test_groupWaitOneFailed() {
		waitWhile = waitWhile(new CustomWaitCondition(true, 1, () -> sleep(1000)));
		waitUntil = waitUntil(new CustomWaitCondition(false, 5, () -> sleep(1000)));
		new GroupWait(TimePeriod.MEDIUM, waitUntil, waitWhile);
	}
}
