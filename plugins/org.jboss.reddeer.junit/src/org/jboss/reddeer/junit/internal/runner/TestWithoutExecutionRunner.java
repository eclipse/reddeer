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
package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * Special runner invoking {@link InitializationError}
 * for each test class.<br/>
 * 
 * It doesn't run tests, it just provide information about tests
 * in error's message.
 * 
 * @author Radoslav Rabara
 *
 */
public class TestWithoutExecutionRunner extends BlockJUnit4ClassRunner {

	/**
	 * Constructs {@link Runner}, which invokes {@link InitializationError}
	 * for each test class. Error has message that the specified {@link Class}
	 * was not executed because the requirements was not fulfilled
	 * for a single configuration file 
	 *
	 * @param klass the klass
	 * @throws InitializationError the initialization error
	 */
	public TestWithoutExecutionRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#testName(org.junit.runners.model.FrameworkMethod)
	 */
	@Override
	protected String testName(FrameworkMethod method) {
		return method.getName()
				+ " - without a single run (requirements was not fulfilled)";
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#collectInitializationErrors(java.util.List)
	 */
	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		errors.add(new TestHasNoRunError());//tests will fails with initialization error
	}
	
	private static class TestHasNoRunError extends InitializationError {
		public TestHasNoRunError() {
			super("Test has no run"
					+ "(requirements was not fulfilled for a single test configuration)");
		}
	}
}
