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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.execution.PriorityComparator;
import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * 
 * Statement which runs methods of defined extensions. Upon failure a screenshot is captured.
 * 
 * @author Lucia Jelinkova
 *
 */
public class RunIAfterClassExtensions extends AbstractStatementWithScreenshot {

	private static final Logger log = Logger.getLogger(RunIAfterClassExtensions.class);

	private final List<IAfterTest> afters;

	/**
	 * Instantiates a new run i after class extensions.
	 *
	 * @param config the config
	 * @param next the next
	 * @param testClass the test class
	 * @param afters the afters
	 */
	public RunIAfterClassExtensions(String config, Statement next, TestClass testClass, 
			List<IAfterTest> afters) {
		super(config, next, testClass, null, null);
		this.afters = afters;
	}

	@Override
	public void evaluate() throws Throwable {
		List<Throwable> errors = new ArrayList<Throwable>();

		try {
			nextStatement.evaluate();
		} catch (Throwable e) {
			errors.add(e);
		} 

		IAfterTest after = null;

		log.debug("Run after class extensions for test class " + testClass.getJavaClass().getName());
		afters.sort(new PriorityComparator());
		for (IAfterTest each : afters) {
			after = each;
			try {
				if (after.hasToRun()){
					log.debug("Run method runAfterTestClass() of class " + after.getClass().getCanonicalName());
					after.runAfterTestClass(config, testClass);
				}
			} catch (Throwable e) {
				if(ScreenshotCapturer.shouldCaptureScreenshotOnException(e)){
					log.error("Run method runAfterTestClass() of class " + after.getClass().getCanonicalName() + " failed", e);
					createScreenshot("AfterClassExt", after.getClass());
				}
				errors.add(e);
			}
		}
		MultipleFailureException.assertEmpty(errors);
	}
}
