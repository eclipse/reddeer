/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.jdt.ui.preferences;

import org.eclipse.reddeer.junit.execution.TestMethodShouldRun;
import org.junit.runners.model.FrameworkMethod;

/**
 * Condition class for tests to help run correct tests for correct Java version.
 * Used in BuildPathsPropertyPageTest class.
 * 
 * @author Josef Kopriva
 *
 */
public class JavaCondition implements TestMethodShouldRun {

	@Override
	public boolean shouldRun(FrameworkMethod method) {
		if (method.getName().contains("Path")) {
			return !System.getProperty("java.version").startsWith("1.");
		} else {
			return System.getProperty("java.version").startsWith("1."); 
		}
	}	
}	