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
package org.eclipse.reddeer.requirements.property;

/**
 * Utility class to get value of expanded property.
 * 
 * @author mlabuda@redhat.com
 *
 */
public class RequirementPropertyExpandor {

	/**
	 * Expands a specified property if such property exists. Otherwise return
	 * same text as was passed to this method.
	 * 
	 * @param property
	 *            property to expand, e.g. ${java.home}
	 * @return expanded property of passed value
	 */
	public static String getProperty(String property) {
		try {
			if (property.startsWith("${") && property.endsWith("}")) {
				String value = System.getProperty(property.substring(2, property.length() - 1));
				if (value == null) {
					return property;
				} else {
					return value;
				}
			} else {
				return property;
			}
		} catch (Exception ex) {
			return property;
		}
	}

}
