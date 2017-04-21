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
package org.eclipse.reddeer.junit.test.integration.runner.injection;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class InjectRequirementsRedDeerSuite extends RedDeerSuite {

	protected static final String LOCATIONS_ROOT_DIR = "resources/org/eclipse/reddeer/junit/integration/runner/order/fileC.xml";
	
	public InjectRequirementsRedDeerSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(heck(clazz), builder);
	}

	protected InjectRequirementsRedDeerSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}
	
	/**
	 * Hecky hook for setting the system property.
	 * @param clazz
	 * @return
	 */
	private static Class<?> heck(Class<?> clazz){
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), LOCATIONS_ROOT_DIR);
		return clazz;
	}
}
