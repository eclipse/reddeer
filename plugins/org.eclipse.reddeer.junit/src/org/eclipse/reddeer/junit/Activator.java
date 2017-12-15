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
package org.eclipse.reddeer.junit;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.reddeer.junit"; //$NON-NLS-1$
	public static final String REQUIREMENTS_EXTENSION_POINT="org.eclipse.reddeer.junit.requirement";
	private static final Logger log = Logger.getLogger(Activator.class);

	// The shared instance
	private static Activator plugin;
	
	private static List<Requirement<Annotation>> requirements;
	
	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public static List<Requirement<Annotation>> getRequirements(){
		if(requirements == null){
			requirements = new ArrayList<>();
			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(REQUIREMENTS_EXTENSION_POINT);
			for (IConfigurationElement e : elements) {
				try {
					log.info("Found requirement "+e.getAttribute("class"));
					final Requirement<Annotation> requirement = (Requirement<Annotation>)e.createExecutableExtension("class");
					requirements.add(requirement);
				} catch (Exception ex) {
					log.error("Unable to instantiate requirement "+e.getAttribute("class"));
				}
			}
		}
		return requirements;
	}

}
