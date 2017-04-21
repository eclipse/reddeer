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
package org.eclipse.reddeer.core.util;

/**
 * Simple instance validator.
 * 
 * @author Vlado Pakan
 * @author Jiri Peterka
 */
public class InstanceValidator {
	
	/**
	 * Simple validation method for non-nullable arguments.
	 * @param argument argument to check
	 * @param argumentName name for exception message
	 * @throws IllegalArgumentException if {@code argument} is {@code null}
	 */
	public static void checkNotNull(Object argument, String argumentName) {
		if (argument == null) {
			throw new IllegalArgumentException("Argument '" + argumentName 
					+ "' should not be null.");
		}
	}
}
