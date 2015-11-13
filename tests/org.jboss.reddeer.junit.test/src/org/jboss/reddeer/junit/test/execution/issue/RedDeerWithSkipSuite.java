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
package org.jboss.reddeer.junit.test.execution.issue;

import java.util.HashSet;
import java.util.Set;

import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Assert;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * 
 * @author Andrej Podhradsky
 *
 */
public class RedDeerWithSkipSuite extends RedDeerSuite {

	private static Set<String> executedMethods;

	public RedDeerWithSkipSuite(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
		super(clazz, builder);
	}

	public RedDeerWithSkipSuite(Class<?> clazz, RunnerBuilder builder, SuiteConfiguration config)
			throws InitializationError {
		super(clazz, builder, config);
	}

	@Override
	public void run(RunNotifier notifier) {
		String originalValue = System.getProperty("rd.skipUnresolvedIssues", "false");
		System.setProperty("rd.skipUnresolvedIssues", "true");
		super.run(notifier);
		System.setProperty("rd.skipUnresolvedIssues", originalValue);
		Assert.assertEquals("Exactly one method should be executed!", 1, executedMethods.size());
		Assert.assertTrue("Wrong method was executed!", executedMethods.contains("resolvedIssueTest"));
	}

	static void executeMethod(String methodName) {
		if (executedMethods == null) {
			executedMethods = new HashSet<String>();
		}
		executedMethods.add(methodName);
	}

}
