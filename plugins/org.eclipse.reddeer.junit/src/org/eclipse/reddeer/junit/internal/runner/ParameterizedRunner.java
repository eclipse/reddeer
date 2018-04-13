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
package org.eclipse.reddeer.junit.internal.runner;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Parameterized;
import org.junit.runners.model.Statement;

/**
 * This is parent runner for one parameterized test class. It's purpose is to
 * fulfill requirements and create child parameterized runners (
 * {@link ParameterizedRequirementsRunner}.
 * 
 * @author rhopp
 *
 */

public class ParameterizedRunner extends Parameterized {

	private static final Logger log = Logger.getLogger(ParameterizedRunner.class);

	private String configId;
	private Requirements requirements;
	private RunListener[] runListeners;
	private List<IBeforeTest> beforeTestExtensions;
	private List<IAfterTest> afterTestExtensions;

	private RequirementsRunner firstChildRunner;
	
	/**
	 * Instantiates a new parameterized runner.
	 *
	 * @param clazz the clazz
	 * @param requirements the requirements
	 * @param configId the config id
	 * @param runListeners the run listeners
	 * @param beforeTestExtensions the before test extensions
	 * @param afterTestExtensions the after test extensions
	 * @throws Throwable the throwable
	 */
	public ParameterizedRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners,
			List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions) throws Throwable {
		super(injectRequirements(clazz, requirements));
		
		this.requirements = requirements;
		this.configId = configId;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
	}
	
	private static Class<?> injectRequirements(Class<?> clazz, Requirements requirements) {
		log.debug("Injecting fulfilled requirements into static fields of test class");
		new RequirementsInjector().inject(clazz, requirements);
		return clazz;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#getName()
	 */
	@Override
	protected String getName() {
		return super.getName() + " " + configId;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.Parameterized#getChildren()
	 */
	@Override
	protected List<Runner> getChildren() {
		List<Runner> children = super.getChildren();
		for (Runner runner : children) {
			if (runner instanceof ParameterizedRequirementsRunner) {
				ParameterizedRequirementsRunner testRunner = (ParameterizedRequirementsRunner) runner;
				testRunner.setConfigId(configId);
				testRunner.setRequirements(requirements);
				testRunner.setRunListeners(runListeners);
				testRunner.setAfterTestExtensions(afterTestExtensions);
				testRunner.setBeforeTestExtensions(beforeTestExtensions);
				if (firstChildRunner == null){
					firstChildRunner = testRunner;
				}
			} else {
				return null;
			}
		}
		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.junit.runners.ParentRunner#withBeforeClasses(org.junit.runners.model.
	 * Statement)
	 */
	@Override
	protected Statement withBeforeClasses(Statement statement) {
		assertNotNull(firstChildRunner);
		return firstChildRunner.withBeforeClasses(statement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.junit.runners.ParentRunner#withAfterClasses(org.junit.runners.model.
	 * Statement)
	 */
	@Override
	protected Statement withAfterClasses(Statement statement) {
		assertNotNull(firstChildRunner);
		return firstChildRunner.withAfterClasses(statement);
	}
}
