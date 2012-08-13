package org.jboss.reddeer.junit.internal.configuration.reader;

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
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

	public XMLReader(File file) {
		this.file = file;
		
		validateXmlConfiguration();
	}
	
	public <T> List<T> getConfiguration(Class<T> clazz){
		log.debug("Reading configuration for " + clazz);
		XmlRootElement root = getRoot(clazz);
		return getConfiguration(getNamespace(root), getName(root), clazz);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> getConfiguration(String namespace, String tag, Class<T> clazz){
		NodeList list = getDocument().getElementsByTagNameNS(namespace, tag);
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (List<T>) unmarshall(unmarshaller, list);
		} catch (JAXBException e){
			throw new RedDeerConfigurationException("Cannot unmarshall tag " + tag + " in XML configuration file " + file.getAbsolutePath(), e);
		}
	}
	
	private List<?> unmarshall(Unmarshaller unmarshaller, NodeList list) throws JAXBException{
		List<Object> objects = new ArrayList<Object>();
		for (int i = 0; i < list.getLength(); i++){
			objects.add(unmarshaller.unmarshal(list.item(i)));			
		}
		return objects;
	}
	
	private Document getDocument() {
		if (doc == null){
			init();
		}
		return doc;
	}
	
	private void init() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
		} catch (ParserConfigurationException e) {
			throw createCannotLoadException(file, e);
		} catch (SAXException e) {
			throw createCannotLoadException(file, e);
		} catch (IOException e) {
			throw createCannotLoadException(file, e);
		}
	}
	
	private XmlRootElement getRoot(Class<?> clazz){
		XmlRootElement root = clazz.getAnnotation(XmlRootElement.class);
		if (root == null){
			throw new RedDeerConfigurationException("The configuration object does not have " + XmlRootElement.class.getName() + " annotation defined.");
		}
		return root;
	}
	
	private String getName(XmlRootElement root){
		String name = root.name();
		if ("##default".equals(name)){
			throw new RedDeerConfigurationException("Red Deer does not support default values in " + XmlRootElement.class.getName() + " annotation");
		}
		return name;
	}
	
	private String getNamespace(XmlRootElement root){
		String namespace = root.namespace();
		if ("##default".equals(namespace)){
			throw new RedDeerConfigurationException("Red Deer does not support default values in " + XmlRootElement.class.getName() + " annotation");
		}
		return namespace;
	}
	
	private RedDeerConfigurationException createCannotLoadException(File file, Exception e){
		return new RedDeerConfigurationException("Cannot load XML configuration file " + file.getAbsolutePath(), e);
	}
	
	/**
	 * Validates xml configuration of requirements according to their xsd
	 * schema.
	 * 
	 * @throws RedDeerConfigurationException
	 *             when the xml configuration is not valid according to the
	 *             specified xsd schema
	 */
	private void validateXmlConfiguration() {

		if (!containsSchema(RED_DEER_NS)) {
			throw new RedDeerConfigurationException(
					"Xml requirements configuration does not use RedDeer schema.");
		}

		// if so, validate the xml configuration
		try {
			// 1. Lookup a factory for the W3C XML Schema language
			SchemaFactory factory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");

			// 2b. Compile custom schemas.
			Schema schema = factory.newSchema();

			// 3. Get validators from the schemas specified
			Validator validator = schema.newValidator();

			// 4. Parse the document you want to check.
			Source source = new StreamSource(file);

			validator.validate(source);
		} catch (SAXException ex) {
				throw new RedDeerConfigurationException(
					"Xml configuration is not valid.", ex);
		} catch (IOException e) {
				throw new RedDeerConfigurationException(
					"Xml configuration is not valid.", e);
		}
	}

	private boolean containsSchema(String schemaNamespace) {
		boolean containsNS = false;

		Element rootEl = getDocument().getDocumentElement();

		NamedNodeMap attribs = rootEl.getAttributes();
		for (int i = 0; i < attribs.getLength(); i++) {
			if (attribs.item(i).getNodeValue().equalsIgnoreCase(schemaNamespace)) {
				containsNS = true;
			}
		}

		return containsNS;
	}
}
