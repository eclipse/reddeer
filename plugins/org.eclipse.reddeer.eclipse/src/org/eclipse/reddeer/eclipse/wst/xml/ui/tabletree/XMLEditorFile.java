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
package org.eclipse.reddeer.eclipse.wst.xml.ui.tabletree;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.reddeer.common.util.XPathEvaluator;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.workbench.api.EditorFile;
import org.xml.sax.SAXException;

/**
 * Represents an XML file associated to an editor.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class XMLEditorFile implements EditorFile {

	private EditorFile editorFile;

	/**
	 * Constructs an XML editor file from a given file associated to an editor.
	 * 
	 * @param editorFile
	 *            A file associated to an editor
	 */
	public XMLEditorFile(EditorFile editorFile) {
		this.editorFile = editorFile;
	}

	@Override
	public InputStream getInputStream() {
		return editorFile.getInputStream();
	}

	@Override
	public String getRelativePath() {
		return editorFile.getRelativePath();
	}

	@Override
	public String getAbsolutePath() {
		return editorFile.getAbsolutePath();
	}

	/**
	 * Evaluates the given XPath expression and returns its result. The
	 * evaluation is namespace-aware.
	 * 
	 * @param xPathExpression
	 *            XPath expression
	 * @return Result of the XPath expression
	 */
	public String xpath(String xPathExpression) {
		return xpath(xPathExpression, true);
	}

	/**
	 * Evaluates the given XPath expression and returns its result.
	 * 
	 * @param xPathExpression
	 *            XPath expression
	 * @param namespaceAware
	 *            If false then all namespaces are ignored
	 * @return Result of the XPath expression
	 */
	public String xpath(String xPathExpression, boolean namespaceAware) {
		try {
			XPathEvaluator xPathEvaluator = new XPathEvaluator(getInputStream(), namespaceAware);
			return xPathEvaluator.evaluateXPath(xPathExpression);
		} catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
			throw new EclipseLayerException("Cannot evaluate xPath " + xPathExpression, e);
		}
	}

}
