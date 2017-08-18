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
package org.eclipse.reddeer.junit.internal.runner.statement;

import java.util.List;

import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.BeforeClass;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Statement which runs before a test class. Upon failure a screenshot is
 * captured.
 * 
 * @author mlabuda@redhat.com
 * @author ljelinko
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class RunBeforeClasses extends AbstractStatementWithScreenshot {

	private final List<FrameworkMethod> beforeClasses;
	private Requirements requirements;

	/**
	 * Instantiates a new RunBeforeClasses. All requirements with runBeforeClass()
	 * will be evaluated before methods annotated by @BeforeClass.
	 * 
	 * @param configId
	 *            configuration id
	 * @param next
	 *            next statement
	 * @param testClass
	 *            test class
	 * @param requirements
	 *            requirements
	 */
	public RunBeforeClasses(String configId, Statement next, TestClass testClass, Requirements requirements) {
		super(configId, next, testClass, null, null);
		this.beforeClasses = testClass.getAnnotatedMethods(BeforeClass.class);
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
			requirements.runBeforeClass();
			for (FrameworkMethod bfr : beforeClasses) {
				before = bfr;
				before.invokeExplosively(target);
			}
		} catch (Throwable throwable) {
			if (ScreenshotCapturer.shouldCaptureScreenshotOnException(throwable)) {
				if (isClassLevel()) {
					frameworkMethod = before;
					createScreenshot("BeforeClass");
				} else {
					createScreenshot("BeforeClass_" + before.getName());
				}
			}
			throw throwable;
		}
		nextStatement.evaluate();
	}
}