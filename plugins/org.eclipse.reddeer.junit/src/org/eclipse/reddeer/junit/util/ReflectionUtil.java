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
package org.eclipse.reddeer.junit.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.junit.requirement.RequirementException;

/**
 * Util class which provides methods for a better manipulation with Java
 * reflection.
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class ReflectionUtil {

	/**
	 * Gets a value specified by property access syntax, e.g. "person.name".
	 * 
	 * @param propertySyntax
	 *            property syntax
	 * @return value of the given property syntax
	 */
	public static Object getValue(Object obj, String propertySyntax) {
		if (obj == null) {
			throw new IllegalArgumentException("Object cannot be null");
		}
		if (propertySyntax == null || propertySyntax.length() == 0) {
			throw new IllegalArgumentException("Property syntax cannot be null or empty");
		}
		Object result = obj;
		String[] properties = propertySyntax.split("\\.");
		List<String> propertyPath = new ArrayList<>();
		for (String property : properties) {
			if (result == null) {
				throw new RequirementException("Cannot access " + propertySyntax + " since "
						+ String.join(".", propertyPath) + " was resolved as null");
			}
			String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
			try {
				Method method = result.getClass().getMethod(methodName);
				result = method.invoke(result);
			} catch (NoSuchMethodException | InvocationTargetException | IllegalArgumentException
					| IllegalAccessException e) {
				throw new RequirementException(
						"Cannot access " + property + " in " + result.getClass().getCanonicalName(), e);
			}
			propertyPath.add(property);
		}
		return result;
	}

}
