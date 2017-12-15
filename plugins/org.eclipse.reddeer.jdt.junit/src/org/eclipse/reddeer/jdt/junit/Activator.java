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
package org.eclipse.reddeer.jdt.junit;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.reddeer.jdt.junit"; //$NON-NLS-1$

	public static final String APPLICATION_ID = PLUGIN_ID + ".reddeertestapplication"; //$NON-NLS-1$
	
	public static final String TESTABLE_OBJECT = "org.eclipse.ui.testing.TestableObject";

	// The shared instance
	private static Activator plugin;
	private BundleContext context;
	private ServiceTracker<Object, Object> serviceTracker;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		this.context = context;
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		this.context = null;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public Object getTestableObject() {
		if (context == null) {
			return null;
		}
		if (serviceTracker == null) {
			serviceTracker = new ServiceTracker<Object, Object>(context, TESTABLE_OBJECT, null);
			serviceTracker.open();
		}
		return serviceTracker.getService();
	}
}
