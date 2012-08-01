package org.jboss.reddeer.junit.internal.configuration.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.reddeer.junit.configuration.RedDeerConfigurationException;
import org.jboss.reddeer.junit.requirement.Requirement;

/**
 * JAXB annotated class for reading the default (property based) configuration. 
 * 
 * @author Lucia Jelinkova
 *
 */
@XmlRootElement(name="requirement", namespace="http://www.jboss.org/NS/Req")
public class PropertyBasedConfiguration {

	private List<Property> properties = new ArrayList<Property>();
	
	private String clazz;
	
	private Class<? extends Requirement<?>> className;
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	@XmlElement(name="property", namespace="http://www.jboss.org/NS/Req")
	public List<Property> getProperties() {
		return properties;
	}
	
	public void setRequirementClassName(String clazz) {
		this.clazz = clazz;
	}
	
	@XmlAttribute(name="class")
	public String getRequirementClassName() {
		return clazz;
	}
	
	public Class<? extends Requirement<?>> getRequirementClass() {
		if (className == null){
			className = createClass(getRequirementClassName());
		}
		return className;
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends Requirement<?>> createClass(String name) {
		try {
			return (Class<? extends Requirement<?>>) Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new RedDeerConfigurationException("The requirement class defined in the XML configuration file cannot be created. See details below", e);
		}
	}
}
