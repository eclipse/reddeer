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
package org.eclipse.reddeer.common.logging;

/**
 * Provides some utility methods useful when creating log messages. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class LoggingUtils {
	
	/**
	 * Format array of Strings to list of items separated by comma.
	 *
	 * @param items array of string items
	 * @return string of items separated by commas
	 */
	public static String format(int[] items){
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < items.length; i++){
			sb.append(items[i]);
			if (i != items.length - 1){
				sb.append(", ");
			}
		}

		return sb.toString();
	}
	
	/**
	 * Format array of Objects to list of items separated by comma.
	 *
	 * @param items array of objects items
	 * @return string of individual toString object representation as separated by commas
	 */
	public static String format(Object[] items){
		StringBuilder sb = new StringBuilder(items != null ? "[Length: " + items.length + "] " : "<null>");

		for (int i = 0; i < items.length; i++){
			sb.append("'" + items[i] + "'");
			if (i != items.length - 1){
				sb.append(", ");
			}
		}

		return sb.toString();
	}
}
