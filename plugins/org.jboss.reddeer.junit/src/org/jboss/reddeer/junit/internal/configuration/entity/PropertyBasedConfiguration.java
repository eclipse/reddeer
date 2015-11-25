package org.jboss.reddeer.junit.internal.configuration.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * JAXB annotated class for reading the default (property based) configuration.
 * 
 * @author Lucia Jelinkova
 * @author sbunciak
 */
@XmlRootElement(name = "requirement", namespace = "http://www.jboss.org/NS/Req")
public class PropertyBasedConfiguration {

	private List<Property> properties = new ArrayList<Property>();

	private String clazz;

	private Class<? extends Requirement<?>> className;

	/**
	 * Sets the properties.
	 *
	 * @param properties the new properties
	 */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	/**
	 * Gets the properties.
	 *
	 * @return List of requirement properties
	 */
	@XmlElement(name = "property", namespace = "http://www.jboss.org/NS/Req")
	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * Sets the requirement class name.
	 *
	 * @param clazz the new requirement class name
	 */
	public void setRequirementClassName(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * Gets the requirement class name.
	 *
	 * @return Class name of the requirement
	 */
	@XmlAttribute(name = "class")
	public String getRequirementClassName() {
		return clazz;
	}
}
