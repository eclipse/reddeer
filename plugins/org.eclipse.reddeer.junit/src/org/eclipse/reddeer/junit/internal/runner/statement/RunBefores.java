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
package org.eclipse.reddeer.junit.internal.runner.statement;

import java.util.List;

import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.Before;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Statement which runs before a test. Upon failure a screenshot is captured.
 * 
 * @author mlabuda@redhat.com
 * @author ljelinko
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class RunBefores extends AbstractStatementWithScreenshot {

	private final List<FrameworkMethod> befores;
	private Requirements requirements;

	/**
	 * Instantiates a new RunBefores. All requirements with runBefore() will be
	 * evaluated before methods annotated by @Before.
	 * 
	 * @param configId
	 *            configuration id
	 * @param next
	 *            next statement
	 * @param testClass
	 *            test class
	 * @param method
	 *            test method
	 * @param target
	 *            target object
	 * @param requirements
	 *            requirements
	 */
	public RunBefores(String configId, Statement next, TestClass testClass, FrameworkMethod method, Object target,
			Requirements requirements) {
		super(configId, next, testClass, method, target);
		this.befores = testClass.getAnnotatedMethods(Before.class);
		this.requirements = requirements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		FrameworkMethod before = null;
		try {
			requirements.runBefore();
			for (FrameworkMethod bfr : befores) {
				before = bfr;
				before.invokeExplosively(target);
			}
		} catch (Throwable throwable) {
			if (ScreenshotCapturer.shouldCaptureScreenshotOnException(throwable)) {
				if (isClassLevel()) {
					frameworkMethod = before;
					createScreenshot("Before");
				} else {
					createScreenshot("Before_" + before.getName());
				}
			}
			throw throwable;
		}
		nextStatement.evaluate();
	}
}