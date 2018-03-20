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
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.wait.WaitType;
import org.junit.Test;

/**
 * 
 * @author odockal
 *
 */
public class WaitProviderTest {

	@Test(expected=IllegalArgumentException.class)
	public void test_nullCondition() {
		waitUntil(null);
	}
	
	@Test
	public void test_WaitTypeUntil() {
		assertTrue(waitUntil(new CustomWaitCondition(true, 1)).getWaitType().equals(WaitType.UNTIL));
	}
	
	@Test
	public void test_WaitTypeWhile() {
		assertTrue(waitWhile(new CustomWaitCondition(true, 1)).getWaitType().equals(WaitType.WHILE));
	}	
}
