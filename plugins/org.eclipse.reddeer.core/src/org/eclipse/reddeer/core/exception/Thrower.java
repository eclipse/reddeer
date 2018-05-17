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
package org.eclipse.reddeer.core.exception;

/**
 * Thrower throws exceptions when condition is met.
 * 
 * @author Jiri Peterka
 *
 */
public class Thrower {

	/**
	 * Throws CoreLayerException with specified message if specified object is null.
	 * 
	 * @param object object to check its existence
	 * @param message message to throw if object is null
	 */
	public static void objectIsNull(Object object, String message) {
		if (object == null) {
			throw new CoreLayerException(message);
		}
	}

	/**
	 * Throws CoreLayerException if specified object is not an instance of specified class.
	 * 
	 * @param object object to check its type
	 * @param clazz matching class
	 */
	public static void typeIsWrong(Object object, Class<?> clazz) {
		if (!object.getClass().isInstance(object)) {
			throw new CoreLayerException("Unexpected type, expected "
					+ clazz.getName() + " actual " + object.getClass().getName());
		}
	}
}
