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
import org.junit.AfterClass;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Statement which runs after a test class. Upon failure a screenshot is
 * captured.
 *
 * @author mlabuda@redhat.com
 * @author ljelinko
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 *
 */
public class RunAfterClasses extends AbstractStatementWithScreenshot {

	private final List<FrameworkMethod> afterClasses;
	private Requirements requirements;

	/**
	 * Instantiates a new RunAfterClassess. All requirements with runAfterClass()
	 * will be evaluated after methods annotated by @AfterClass.
	 *
	 * @param config
	 *            the config
	 * @param next
	 *            the next
	 * @param testClass
	 *            the test class
	 * @param afters
	 *            the afters
	 */
	public RunAfterClasses(String config, Statement next, TestClass testClass, Requirements requirements) {
		super(config, next, testClass, null, null);
		afterClasses = testClass.getAnnotatedMethods(AfterClass.class);
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
			for (FrameworkMethod each : afterClasses) {
				
					frameworkMethod = each;
					frameworkMethod.invokeExplosively(target);
			}
			
			// Should be run before testing the failures in this context
			requirements.runAfterClass();
			
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
