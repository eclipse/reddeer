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
package org.eclipse.reddeer.common.matcher;

import java.util.Comparator;

/**
 * Compare versions. Versions has to be with dot "." notation. E.g. 1.0.1, 1.1.1 etc.
 * 
 * @author mlabuda
 *
 */
public class VersionComparator implements Comparator<String>{

	@Override
	public int compare(String v1, String v2) {
		String[] version1 = v1.split("\\.");
		String[] version2 = v2.split("\\.");
		int smallerLength = version1.length < version2.length ? version1.length: version2.length;
		for (int i=0; i < smallerLength; i++) {
			int number1 = Integer.valueOf(version1[i]).intValue();
			int number2 = Integer.valueOf(version2[i]).intValue();
			int comparedValue = Integer.compare(number1, number2);
			if (comparedValue != 0) {
				return comparedValue;
			}
		}
		String[] biggerArray = version1.length == smallerLength? version2 : version1;
		for (int i=smallerLength; i < biggerArray.length; i++) {
			if (Integer.valueOf(biggerArray[i]) > 0) {
				if (biggerArray == version1) {
					return 1;
				} else {
					return -1;
				}
			}
		}
		return 0;
	}
}
