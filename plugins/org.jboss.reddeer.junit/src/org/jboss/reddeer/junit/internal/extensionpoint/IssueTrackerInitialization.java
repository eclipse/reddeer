/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.junit.internal.extensionpoint;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IIssueTracker;

/**
 * Handles Initialization of all issue tracker extensions. Has to be separate class
 * because it's using eclipse libraries and will not run for normal pure JUnit
 * tests
 * 
 * @author Andrej Podhradsky
 */
public class IssueTrackerInitialization {

	public static final String EXTENSION_ID = "org.jboss.reddeer.junit.issue.tracker";

	private static final Logger log = Logger.getLogger(IssueTrackerInitialization.class);

	/**
	 * Initializes all issue tracker extensions
	 * 
	 * @return list of issue trackers
	 */
	public static List<IIssueTracker> initialize() {
		LinkedList<IIssueTracker> issueTrackers = new LinkedList<IIssueTracker>();
		IConfigurationElement[] configElements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_ID);
		log.debug("Number of found extensions for extension point " + EXTENSION_ID + "=" + configElements.length);
		for (IConfigurationElement e : configElements) {
			try {
				final Object object = e.createExecutableExtension("class");
				if (object instanceof IIssueTracker) {
					issueTrackers.add((IIssueTracker) object);
				} else {
					log.warn("Invalid class used for extension point " + EXTENSION_ID + ":" + object.getClass());
				}
			} catch (CoreException ex) {
				ex.printStackTrace();
				log.error("Error when processing extension for " + EXTENSION_ID, ex.getMessage());
			}
		}

		return issueTrackers;
	}
}
