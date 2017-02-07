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
import org.jboss.reddeer.junit.execution.PriorityComparator;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * 
 * Statement which runs methods of defined extensions. Upon failure a screenshot is captured.
 * 
 * @author Lucia Jelinkova
 *
 */
public class RunIBeforeClassExtensions extends AbstractStatementWithScreenshot {

	private static final Logger log = Logger.getLogger(RunIBeforeClassExtensions.class);

	private final List<IBeforeTest> befores;

	/**
	 * Instantiates a new run i before class extensions.
	 *
	 * @param config the config
	 * @param next the next
	 * @param testClass the test class
	 * @param befores the befores
	 */
	public RunIBeforeClassExtensions(String config, Statement next, TestClass testClass, 
			List<IBeforeTest> befores) {
		super(config, next, testClass, null, null);
		this.befores = befores;
	}

	@Override
	public void evaluate() throws Throwable {
		IBeforeTest before = null;

		log.debug("Run before class extensions for test class " + testClass.getJavaClass().getName());
		try {
			befores.sort(new PriorityComparator());
			for (IBeforeTest bfr : befores) {
				before = bfr;
				if (before.hasToRun()){
					log.debug("Run method runBeforeTestClass() of class " + before.getClass().getCanonicalName());
					before.runBeforeTestClass(config, testClass);
				}
			}
		} catch (Throwable e) {
			if(ScreenshotCapturer.shouldCaptureScreenshotOnException(e)){
				log.error("Run method runBeforeTestClass() of class " + before.getClass().getCanonicalName() + " failed", e);
				createScreenshot("BeforeClassExt", before.getClass());
			}
			throw e;
		}

		nextStatement.evaluate();
	}
}