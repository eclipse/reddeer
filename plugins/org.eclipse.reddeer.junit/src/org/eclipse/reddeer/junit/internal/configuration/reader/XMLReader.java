/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.internal.configuration.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.configuration.RedDeerConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Reads the XML file and unmarshalls the requirements configuration.
 * 
 * @author Lucia Jelinkova
 * 
 */
public class XMLReader {

	private static final Logger log = Logger.getLogger(XMLReader.class);

	private File file;

	private Document doc;

	// TODO: change according to the real RedDeer namespace when published
	// e.g. http://www.jboss.org/reddeer/schema
	private static final String RED_DEER_NS = "http://www.jboss.org/NS/Req";

	/**
	 * Instantiates a new XML reader.
	 *
	 * @param file the file
	 */
	public XMLReader(File file) {
		this.file = file;
	}

	/**
	 * Gets the configuration.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @return the configuration
	 */
	public <T> List<T> getConfiguration(Class<T> clazz) {
		log.debug("Reading configuration for " + clazz);
		XmlRootElement root = getRoot(clazz);
		return getConfiguration(getNamespace(root), getName(root), clazz);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> getConfiguration(String namespace, String tag,
			Class<T> clazz) {
		NodeList list = getDocument().getElementsByTagNameNS(namespace, tag);
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (List<T>) unmarshall(unmarshaller, list);
		} catch (JAXBException e) {
			throw new RedDeerConfigurationException("Cannot unmarshall tag "
					+ tag + " in XML configuration file "
					+ file.getAbsolutePath(), e);
		}
	}

	private List<?> unmarshall(Unmarshaller unmarshaller, NodeList list)
			throws JAXBException {
		List<Object> objects = new ArrayList<Object>();
		for (int i = 0; i < list.getLength(); i++) {
			objects.add(unmarshaller.unmarshal(list.item(i)));
		}
		return objects;
	}

	private Document getDocument() {
		if (doc == null) {
			init();
		}
		return doc;
	}

	/**
	 * Parses and validates xml configuration of requirements according to their
	 * xsd schema.
	 * 
	 * @throws RedDeerConfigurationException
	 *             when the xml configuration is not valid according to the
	 *             specified xsd schema
	 */
	private void init() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		factory.setAttribute(
				"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new RedDeerErrorHandler());

			doc = builder.parse(file);

			if (!containsSchema(doc, RED_DEER_NS)) {
				throw new RedDeerConfigurationException(
						"Xml requirements configuration does not use RedDeer schema.");
			}
		} catch (ParserConfigurationException e) {
			throw createCannotLoadException(file, e);
		} catch (SAXException e) {
			throw createCannotLoadException(file, e);
		} catch (IOException e) {
			throw createCannotLoadException(file, e);
		}
	}

	private XmlRootElement getRoot(Class<?> clazz) {
		XmlRootElement root = clazz.getAnnotation(XmlRootElement.class);
		if (root == null) {
			throw new RedDeerConfigurationException(
					"The configuration object does not have "
							+ XmlRootElement.class.getName()
							+ " annotation defined.");
		}
		return root;
	}

	private String getName(XmlRootElement root) {
		String name = root.name();
		if ("##default".equals(name)) {
			throw new RedDeerConfigurationException(
					"RedDeer does not support default values in "
							+ XmlRootElement.class.getName() + " annotation");
		}
		return name;
	}

	private String getNamespace(XmlRootElement root) {
		String namespace = root.namespace();
		if ("##default".equals(namespace)) {
			throw new RedDeerConfigurationException(
					"RedDeer does not support default values in "
							+ XmlRootElement.class.getName() + " annotation");
		}
		return namespace;
	}

	private RedDeerConfigurationException createCannotLoadException(File file,
			Exception e) {
		return new RedDeerConfigurationException(
				"Cannot load XML configuration file " + file.getAbsolutePath(), e);
	}

	private boolean containsSchema(Document doc, String schemaNamespace) {
		boolean containsNS = false;

		Element rootEl = doc.getDocumentElement();

		NamedNodeMap attribs = rootEl.getAttributes();
		for (int i = 0; i < attribs.getLength(); i++) {
			if (attribs.item(i).getNodeValue()
					.equalsIgnoreCase(schemaNamespace)) {
				containsNS = true;
			}
		}

		return containsNS;
	}

	class RedDeerErrorHandler implements ErrorHandler {

		/* (non-Javadoc)
		 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
		 */
		public void warning(SAXParseException e) throws SAXException {
			log.warn(e.getMessage());
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
		 */
		public void error(SAXParseException e) throws SAXException {
			log.error(e.getMessage());
			throw new RedDeerConfigurationException(
					"Xml configuration is not valid.", e);
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
		 */
		public void fatalError(SAXParseException e) throws SAXException {
			log.error(e.getMessage());
			throw new RedDeerConfigurationException(
					"Xml configuration is not valid.", e);
		}
	}
}
