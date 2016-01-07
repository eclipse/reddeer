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
package org.jboss.reddeer.junit.internal.runner.statement;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * 
 * Statement which runs {@link IBeforeTest#runBeforeTest()} methods of
 * defined extensions. Upon failure a screenshot is captured.
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

	/* (non-Javadoc)
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
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
			log.error("Run method runBeforeTest() of class " + before.getClass().getCanonicalName() + " failed", e);
			createScreenshot("BeforeTestExt", before.getClass());
			throw e;
		}

		nextStatement.evaluate();
	}
}