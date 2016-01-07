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
package org.jboss.reddeer.suite;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
/**
 * Custom test suite.
 * Allows to modify test suite initialization and perform pre and/or post actions.
 * 
 * @author lucia jelinkova
 *
 */
public class MyTestSuite extends RedDeerSuite {
    /**
     * Test suite constructor.
     * Could be use for some pre and post construct actions
     * @param clazz
     * @param builder
     * @throws InitializationError
     */
	public MyTestSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}

	/**
	 * Run the tests for this runner
	 * @param notifier
	 */
	@Override
	public void run(RunNotifier notifier) {
		// put here some actions called before tests of test suite are run
		// e.g. close welcome screen		
		super.run(notifier);
		// put here some actions called after tests of test suite are run
		// e.g. cleanup workspace
	}
}
