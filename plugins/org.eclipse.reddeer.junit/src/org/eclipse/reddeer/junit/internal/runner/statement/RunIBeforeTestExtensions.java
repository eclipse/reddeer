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

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * 
 * Statement which runs methods of defined extensions. Upon failure a screenshot is captured.
 * 
 * @author Lucia Jelinkova
 *
 */
public class RunIBeforeTestExtensions extends AbstractStatementWithScreenshot {

	private static final Logger log = Logger.getLogger(RunIBeforeTestExtensions.class);

	private final List<IBeforeTest> befores;

	/**
	 * Instantiates a new run i before test extensions.
	 *
	 * @param config the config
	 * @param next the next
	 * @param testClass the test class
	 * @param method the method
	 * @param target the target
	 * @param befores the befores
	 */
	public RunIBeforeTestExtensions(String config, Statement next, TestClass testClass, 
			FrameworkMethod method, Object target, List<IBeforeTest> befores) {
		super(config, next, testClass, method, target);
		this.befores = befores;
	}

	@Override
	public void evaluate() throws Throwable {
		IBeforeTest before = null;

		log.debug("Run before test extensions for test class " + testClass.getJavaClass().getName());
		try {
			for (IBeforeTest bfr : befores) {
				before = bfr;
				if (before.hasToRun()){
					log.debug("Run method runBeforeTest() of class " + before.getClass().getCanonicalName());
					before.runBeforeTest(config, target, frameworkMethod);
				}
			}
		} catch (Throwable e) {
			if(ScreenshotCapturer.shouldCaptureScreenshotOnException(e)){
				log.error("Run method runBeforeTest() of class " + before.getClass().getCanonicalName() + " failed", e);
				createScreenshot("BeforeTestExt", before.getClass());
			}
			throw e;
		}

		nextStatement.evaluate();
	}
}