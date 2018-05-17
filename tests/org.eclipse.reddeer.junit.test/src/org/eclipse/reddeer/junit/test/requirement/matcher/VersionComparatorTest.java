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
package org.eclipse.reddeer.junit.test.requirement.matcher;

import static org.junit.Assert.*;

import org.eclipse.reddeer.common.matcher.VersionComparator;
import org.junit.BeforeClass;
import org.junit.Test;

public class VersionComparatorTest {

	private static VersionComparator comparator;
	
	private static String V1_0 = "1.0";
	private static String V1_0_0 = "1.0.0";
	private static String V1_0_1 = "1.0.1";
	private static String V1_1 = "1.1";
	private static String V1_1_0 = "1.1.0";
	private static String V1_1_1 = "1.1.1";
	
	@BeforeClass
	public static void setup() {
		comparator = new VersionComparator();
	}
	
	@Test
	public void testVersions1() {
		assertTrue(comparator.compare(V1_0, V1_1) < 0);
	}
	
	@Test
	public void testVersions2() {
		assertTrue(comparator.compare(V1_1_0, V1_1) == 0);
	}
	
	@Test
	public void testVersions3a() {
		assertTrue(comparator.compare(V1_0, V1_0_1) < 0);
	}
	
	@Test
	public void testVersions3b() {
		assertTrue(comparator.compare(V1_0_1, V1_0) > 0);
	}
	
	@Test
	public void testVersions4() {
		assertTrue(comparator.compare(V1_0, V1_0_0) == 0);
	}

	@Test
	public void testVersions5() {
		assertTrue(comparator.compare(V1_1_1, V1_0_0) > 0);
	}
	
	@Test
	public void testVersions6() {
		assertTrue(comparator.compare(V1_0_0, V1_0_1) < 0);
	}
	
	@Test
	public void testVersions7() {
		assertTrue(comparator.compare(V1_0_1, V1_0_1) == 0);
	}
}
