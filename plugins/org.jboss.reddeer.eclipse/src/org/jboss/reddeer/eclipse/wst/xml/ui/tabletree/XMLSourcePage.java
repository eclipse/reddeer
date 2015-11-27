package org.jboss.reddeer.eclipse.wst.xml.ui.tabletree;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.ui.texteditor.ITextEditor;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Represents the Source page of {@link XMLMultiPageEditor}. It is text editor
 * with special added features, e.g. for evaluating XPath. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class XMLSourcePage extends TextEditor {

	/**
	 * Creates instance from existing text editor reference. 
	 *
	 * @param editor the editor
	 */
	protected XMLSourcePage(ITextEditor editor) {
		super(editor);
	}

	/**
	 * Evaluate given XPath. The method can work with the namespaces too.</br>
	 * Examples: </br>
	 * <ul>
	 * 	<li> XML with no namespaces defined:<br>
	 *  	String result = page.evaluateXPath("/a/b1/c/text()");
	 * 	<li> XML with namespaces defined (note the ":" sign for default namespace)<br>
	 * 		String result = page.evaluateXPath("/:reddeer/:requirements/server:server-requirement/@name");
	 * </ul>
	 *
	 * @param xPathExpression the x path expression
	 * @return the string
	 */
	public String evaluateXPath(String xPathExpression){
		XPath xPath =  XPathFactory.newInstance().newXPath();

		try {
			Document doc = getDocument();
			xPath.setNamespaceContext(new UniversalNamespaceResolver(doc));
			return xPath.compile(xPathExpression).evaluate(doc);
		} catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
			throw new EclipseLayerException("Cannot evaluate xPath " + xPath, e);
		}
	}

	private Document getDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(getText()));
		return builder.parse(is);
	}

	/**
	 * Universal namespace resolver that delegates namespace lookup 
	 * to underlying document. 
	 *
	 */
	private class UniversalNamespaceResolver implements NamespaceContext {

		private Document sourceDocument;

		public UniversalNamespaceResolver(Document document) {
			sourceDocument = document;
		}

		public String getNamespaceURI(String prefix) {
			if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
				return sourceDocument.lookupNamespaceURI(null);
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
