/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.internal.configuration.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

	private String requirementName;
	
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
	 * Sets the requirement name.
	 * 
	 * @param requirementName the name of requirement
	 */
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
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
	
	/**
	 * Gets the requirement name.
	 * @return Name of the requirement that is unique
	 */
	@XmlAttribute(name = "name")
	public String getRequirementName() {
		return requirementName;
	}
}
