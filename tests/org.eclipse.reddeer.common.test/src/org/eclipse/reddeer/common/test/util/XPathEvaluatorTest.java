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
package org.eclipse.reddeer.common.test.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.eclipse.reddeer.common.util.XPathEvaluator;
import org.junit.Test;

/**
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class XPathEvaluatorTest {

	@Test
	public void testXPathWithoutNamespaces() throws Exception {
		String xml = "<a><b c='123'></b></a>";
		XPathEvaluator xpath = new XPathEvaluator(new ByteArrayInputStream(xml.getBytes()), true);
		assertEquals("123", xpath.evaluateXPath("/a/b/@c"));
	}

	@Test
	public void testXPathWithNamespaces() throws Exception {
		String xml = "<x:a xmlns:x='http://localhost/x' xmlns:y='http://localhost/y'><y:b c='123' ></y:b></x:a>";
		XPathEvaluator xpath = new XPathEvaluator(new ByteArrayInputStream(xml.getBytes()), true);
		assertEquals("123", xpath.evaluateXPath("/x:a/y:b/@c"));
	}

	@Test
	public void testXPathWithAndWithoutNamespaces() throws Exception {
		String xml = "<a xmlns:y='http://localhost/y'><y:b c='123' ></y:b></a>";
		XPathEvaluator xpath = new XPathEvaluator(new ByteArrayInputStream(xml.getBytes()), true);
		assertEquals("123", xpath.evaluateXPath("/a/y:b/@c"));
	}

	@Test
	public void testXPathWithUserDefinedNamespaces() throws Exception {
		String xml = "<x:a xmlns:x='http://localhost/x' xmlns:y='http://localhost/y'><y:b c='123' ></y:b></x:a>";
		XPathEvaluator xpath = new XPathEvaluator(new ByteArrayInputStream(xml.getBytes()), true);
		xpath.setNamespace("foo", "http://localhost/x");
		assertEquals("123", xpath.evaluateXPath("/foo:a/y:b/@c"));
	}

	@Test
	public void testXPathWithoutNamespacesAndIgnoringNamespaces() throws Exception {
		String xml = "<a><b c='123'></b></a>";
		XPathEvaluator xpath = new XPathEvaluator(new ByteArrayInputStream(xml.getBytes()), false);
		assertEquals("123", xpath.evaluateXPath("/a/b/@c"));
	}

	@Test
	public void testXPathWithNamespacesAndIgnoringNamespaces() throws Exception {
		String xml = "<x:a xmlns:x='http://localhost/x' xmlns:y='http://localhost/y'><y:b c='123' ></y:b></x:a>";
		XPathEvaluator xpath = new XPathEvaluator(new ByteArrayInputStream(xml.getBytes()), false);
		assertEquals("123", xpath.evaluateXPath("/a/b/@c"));
	}
}
