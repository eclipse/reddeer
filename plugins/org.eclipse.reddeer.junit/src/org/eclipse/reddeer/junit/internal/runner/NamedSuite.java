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
package org.eclipse.reddeer.junit.internal.runner;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Suite with custom name. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class NamedSuite extends Suite {
	
	private final String suiteName;
	private final RunnerBuilder builder;

	/**
	 * Constructor used for suites. 
	 *
	 * @param clazz the clazz
	 * @param builder the builder
	 * @param name the name
	 * @throws InitializationError the initialization error
	 */
	public NamedSuite(Class<?> clazz, RunnerBuilder builder, String name) throws InitializationError {
		super(clazz, builder);
		this.builder = builder;
		this.suiteName = name;
	}
	
	/**
	 * Constructor used for separate independent classes. 
	 *
	 * @param classes the classes
	 * @param builder the builder
	 * @param name the name
	 * @throws InitializationError the initialization error
	 */
	public NamedSuite(Class<?>[] classes, RunnerBuilder builder, String name) throws InitializationError {
		super(builder, EmptySuite.class, classes);
		this.builder = builder;
		this.suiteName = name;
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#getName()
	 */
	@Override
	public String getName() {
		return suiteName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Suite '" + suiteName + "'";
	}
	
	/**
	 * Gets runner builder
	 * @return runner builder
	 */
	public RunnerBuilder getRunnerBuilder(){
		return builder;
	}
}
