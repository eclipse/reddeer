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

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Reads the XML file and unmarshalls the requirements configuration.  
 * 
 * @author Lucia Jelinkova
 *
 */
public class XMLReader {

	private File file;

	private Document doc;

	public XMLReader(File file) {
		this.file = file;
	}
	
	public <T> List<T> getConfiguration(Class<T> clazz){
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
}
