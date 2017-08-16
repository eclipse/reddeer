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
package org.eclipse.reddeer.junit.requirement.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.reddeer.junit.requirement.RequirementException;

/**
 * Requirement configuration represents a data holder, container for data
 * required to executed more advanced logic with requirements. Requirement
 * configuration consists of several attributes obtained from a source for a
 * test run.
 * 
 * @author mlabuda@redhat.com
 * @since 2.0
 */
public interface RequirementConfiguration {

	/**
	 * Gets ID, unique description of a requirement configuration
	 * 
	 * @return ID/unique description of configuration
	 */
	String getId();

	/**
	 * Gets text value of a configuration attribute by its name.
	 * 
	 * @param attributeName
	 *            name of configuration attribute.
	 * @return attribute value
	 */
	default String getAttribute(String attributeName) {
		return (String) getAttributeValue(attributeName);
	}

	/**
	 * Gets object value of a configuration attribute by its name and class of
	 * attribute.
	 * 
	 * @param clazz
	 *            class of an attribute
	 * @param attributeName
	 *            attribute name to get its value
	 * @return attribute object
	 */
	default <T extends Object> T getAttribute(Class<T> attributeClass, String attributeName) {
		Object attribute = getAttributeValue(attributeName);
		if (attributeClass.isAssignableFrom(attribute.getClass())) {
			return attributeClass.cast(attribute);
		} else {
			throw new RequirementException("Attribute class of attribute " + attributeName
					+ " does not match required attribute class" + attributeClass);
		}
	}

	/**
	 * Gets an object from configuration with given attribute name by invocation the
	 * appropriate "get" method.
	 * 
	 * @param attributeName
	 *            attribute name
	 * @return attribute object
	 */
	default Object getAttributeValue(String attributeName) {
		String methodName = "get" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
		try {
			Method method = this.getClass().getMethod(methodName);
			return method.invoke(this);
		} catch (NoSuchMethodException nsfe) {
			throw new RequirementException(
					"Cannot find method " + methodName + " in configuration class " + this.getClass().getName(), nsfe);
		} catch (InvocationTargetException ite) {
			throw new RequirementException(
					"Cannot invoke method " + methodName + " in configuration class " + this.getClass().getName(), ite);
		} catch (IllegalArgumentException e) {
			throw new RequirementException("Something went wrong when it should not get wrong.", e);
		} catch (IllegalAccessException e) {
			throw new RequirementException("Cannot access attribute " + attributeName + " in configuration instance "
					+ this.getClass().getName(), e);
		}
	}

	/**
	 * Gets nested attribute. Pattern for nested attribute is with dot notation.
	 * All attributes between the top level configuration and the desired
	 * attribute must implement this interface as well.
	 * 
	 * @param attributePattern
	 *            attribute pattern, e.g.
	 *            "nestedConfig.anotherNestedConfig.attributeName"
	 * @return string value of attribute
	 */
	default String getNestedAttribute(String attributePattern) {
		RequirementConfiguration requirementConfig = this;
		String[] attributePath = attributePattern.split("\\.");
		for (int i = 0; i < attributePath.length - 1; i++) {
			requirementConfig = requirementConfig.getAttribute(RequirementConfiguration.class, attributePath[i]);
		}
		return requirementConfig.getAttribute(attributePath[attributePath.length - 1]);
	}
}
