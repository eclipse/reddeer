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
package org.eclipse.reddeer.junit.test.util;

import static org.eclipse.reddeer.junit.util.ReflectionUtil.getValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.eclipse.reddeer.junit.requirement.RequirementException;
import org.junit.Test;

/**
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class ReflectionUtilTest {

	@Test
	public void testGettingSimpleValue() {
		A a = new A();
		a.setName("aaa");

		assertEquals("aaa", getValue(a, "name"));
	}

	@Test
	public void testGettingInheritedValue() {
		B b = new B();
		b.setName("bbb");

		assertEquals("bbb", getValue(b, "name"));
	}

	@Test
	public void testGettingComplexValue() {
		A a = new A();
		a.setName("aaa");
		B b = new B();
		b.setName("bbb");
		b.setA(a);

		assertEquals("aaa", getValue(b, "a.name"));
	}

	@Test
	public void testGettingObjectValue() {
		A a = new A();
		a.setName("aaa");
		B b = new B();
		b.setName("bbb");
		b.setA(a);

		assertEquals(a, getValue(b, "a"));
	}

	@Test
	public void testGettingNonAttributeValue() {
		A a = new A();
		a.setName("aaa");

		assertEquals(A.class, getValue(a, "class"));
	}

	@Test
	public void testGettingNullAttributeValue() {
		A a = new A();

		assertNull(getValue(a, "name"));
	}

	@Test
	public void testHandlingNonExistingAttributeValue() {
		A a = new A();
		a.setName("aaa");

		try {
			getValue(a, "surname");
		} catch (RequirementException e) {
			assertEquals("Cannot access surname in " + A.class.getCanonicalName(), e.getMessage());
			return;
		}
		fail("A requirement exception was expected");
	}

	@Test
	public void testHandlingNPE() {
		B b = new B();

		try {
			getValue(b, "a.name");
		} catch (RequirementException e) {
			assertEquals("Cannot access a.name since a was resolved as null", e.getMessage());
			return;
		}
		fail("A requirement exception was expected");
	}

	public static class A {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public static class B extends A {

		private A a;

		public A getA() {
			return a;
		}

		public void setA(A a) {
			this.a = a;
		}

	}
}
