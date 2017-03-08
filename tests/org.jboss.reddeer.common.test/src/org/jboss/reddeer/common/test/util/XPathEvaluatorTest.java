/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.common.test.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.jboss.reddeer.common.util.XPathEvaluator;
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
