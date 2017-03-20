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
package org.jboss.reddeer.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XPath Evaluator
 * 
 * @author Lucia Jelinkova (ljelinko@redhat.com)
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 */
public class XPathEvaluator {

	private Document doc;
	private Map<String, String> namespaces;

	/**
	 * Builds the XML document from the input stream and sets the property
	 * 'namespaceAware' to a given value. Once the document is built the input
	 * stream is closed.
	 * 
	 * @param inputStream
	 *            Input stream from which the document is built
	 * @param namespaceAware
	 *            If false then all namespaces are ignored
	 * @throws ParserConfigurationException
	 *             If a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 * @throws SAXException
	 *             If any parse errors occur.
	 * @throws IOException
	 *             If any IO errors occur.
	 */
	public XPathEvaluator(InputStream inputStream, boolean namespaceAware)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(namespaceAware);
		DocumentBuilder builder = factory.newDocumentBuilder();
		doc = builder.parse(new InputSource(inputStream));
		inputStream.close();

		namespaces = new HashMap<String, String>();
	}

	/**
	 * Sets a user defined namespace.
	 * 
	 * @param prefix
	 *            Prefix
	 * @param uri
	 *            URI
	 */
	public void setNamespace(String prefix, String uri) {
		namespaces.put(prefix, uri);
	}

	/**
	 * Evaluates the XPath expression. Ignoring namespaces must be specified in
	 * the constructor. Use ":" for a default namespace.
	 *
	 * @param xPathExpression
	 *            XPath expression
	 * @return Result of the given XPath expression
	 * @throws XPathExpressionException
	 *             If the expression cannot be compiled or evaluated.
	 */
	public String evaluateXPath(String xPathExpression) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		xPath.setNamespaceContext(new UniversalNamespaceResolver(doc, namespaces));
		return xPath.compile(xPathExpression).evaluate(doc);
	}

	/**
	 * Prints the document to a given target.
	 * 
	 * @param target
	 *            A target where to print the document
	 * @throws IOException in case input/output exception occurred
	 * @throws TransformerException if exception occurs during transformation process
	 */
	public void printDocument(Result target) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		transformer.transform(new DOMSource(doc), target);
	}

	@Override
	public String toString() {
		StringWriter writer = new StringWriter();
		try {
			printDocument(new StreamResult(writer));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * Universal namespace resolver that delegates namespace lookup to
	 * underlying document.
	 *
	 */
	private class UniversalNamespaceResolver implements NamespaceContext {

		private Document sourceDocument;
		private Map<String, String> userDefinedNamespaces;

		public UniversalNamespaceResolver(Document document, Map<String, String> namespaces) {
			sourceDocument = document;
			userDefinedNamespaces = namespaces;
		}

		public String getNamespaceURI(String prefix) {
			if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
				return sourceDocument.lookupNamespaceURI(null);
			}
			if (userDefinedNamespaces != null && userDefinedNamespaces.containsKey(prefix)) {
				return userDefinedNamespaces.get(prefix);
			} else {
				return sourceDocument.lookupNamespaceURI(prefix);
			}
		}

		public String getPrefix(String namespaceURI) {
			return sourceDocument.lookupPrefix(namespaceURI);
		}

		public Iterator<?> getPrefixes(String namespaceURI) {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	}
}
