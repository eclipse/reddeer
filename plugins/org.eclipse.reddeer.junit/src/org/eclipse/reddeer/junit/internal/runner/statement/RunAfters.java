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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.After;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Statement which run after a test. Upon failure a screenshot is captured.
 * 
 * @author mlabuda@redhat.com
 * @author ljelinko
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class RunAfters extends AbstractStatementWithScreenshot {

	private final List<FrameworkMethod> fAfters;
	private Requirements requirements;

	/**
	 * Instantiates a new RunAfters. All requirements with runAfter() will be
	 * evaluated after methods annotated by @After.
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
	public RunAfters(String config, Statement next, TestClass testClass, FrameworkMethod method, Object target,
			Requirements requirements) {
		super(config, next, testClass, method, target);
		fAfters = testClass.getAnnotatedMethods(After.class);
		this.requirements = requirements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		List<Throwable> errors = new ArrayList<Throwable>();

		try {
			nextStatement.evaluate();
		} catch (Throwable e) {
			errors.add(e);
		}
		
		try {
			for (FrameworkMethod each : fAfters) {
					frameworkMethod = each;
					frameworkMethod.invokeExplosively(target);
			}
			
			// Should be run before testing the failures in tests
			requirements.runAfter();
			
		} catch (Throwable e) {
			if (ScreenshotCapturer.shouldCaptureScreenshotOnException(e)) {
				if (isClassLevel()) {
					createScreenshot("AfterClass");
				} else {
					createScreenshot("After");
				}
			}
			errors.add(e);
		}

		MultipleFailureException.assertEmpty(errors);
	
	}
}
