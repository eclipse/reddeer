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
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
/**
 * Handles Initialization of all Before Test extensions
 * Has to be separate class because it's using eclipse libraries
 * and will not run for normal pure JUnit tests
 * @author vlado pakan
 */
public class BeforeTestInitialization {
	private static final Logger log = Logger.getLogger(BeforeTestInitialization.class);
	
	/**
	 * Initializes all Before Test extensions.
	 *
	 * @return the list
	 */
	public static List<IBeforeTest> initialize(){
		final String beforeTestExtensionID = "org.eclipse.reddeer.junit.before.test";
		LinkedList<IBeforeTest> beforeTestExts = new LinkedList<IBeforeTest>();
		IConfigurationElement[] configElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(beforeTestExtensionID);
		try {
			log.debug("Number of found extensions for extension point " + beforeTestExtensionID + "="
				+ configElements.length);
			for (IConfigurationElement e : configElements) {
				final Object object = e.createExecutableExtension("class");
				if (object instanceof IBeforeTest) {
					beforeTestExts.add((IBeforeTest)object);
				}
				else{
					log.warn("Invalid class used for extension point " + beforeTestExtensionID
						+ ":" + object.getClass());
				}
			}
		} catch (CoreException ex) {
			log.error(
					"Error when processing extension for org.jbossreddeer.junit.before.test",
					ex.getMessage());
		}
		
		return beforeTestExts;
	}
}
