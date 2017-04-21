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
package org.eclipse.reddeer.junit.internal.extensionpoint;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;

/**
 * Handles Initialization of all After Test extensions Has to be separate class
 * because it's using eclipse libraries and will not run for normal pure JUnit
 * tests
 * 
 * @author vlado pakan
 */
public class AfterTestInitialization {
	private static final Logger log = Logger.getLogger(AfterTestInitialization.class);

	/**
	 * Initializes all After Test extensions.
	 *
	 * @return the list
	 */
	public static List<IAfterTest> initialize() {
		final String afterTestExtensionID = "org.eclipse.reddeer.junit.after.test";
		LinkedList<IAfterTest> afterTestExts = new LinkedList<IAfterTest>();
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				afterTestExtensionID);
		try {
			log.debug("Number of found extensions for extension point " + afterTestExtensionID + "="
					+ configElements.length);
			for (IConfigurationElement e : configElements) {
				final Object object = e.createExecutableExtension("class");
				if (object instanceof IAfterTest) {
					afterTestExts.add((IAfterTest) object);
				} else {
					log.warn("Invalid class used for extension point " + afterTestExtensionID + ":" + object.getClass());
				}
			}
		} catch (CoreException ex) {
			log.error("Error when processing extension for org.jbossreddeer.junit.after.test", ex.getMessage());
		}

		return afterTestExts;
	}
}
