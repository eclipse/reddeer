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

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.screenshot.CaptureScreenshotException;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Common ancestor for statements that need to save screenshot. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class AbstractStatementWithScreenshot extends Statement {

	private static final Logger log = Logger.getLogger(AbstractStatementWithScreenshot.class);

	protected String config;

	protected Statement nextStatement;

	protected TestClass testClass;

	protected FrameworkMethod frameworkMethod;

	protected Object target;

	/**
	 * Instantiates a new abstract statement with screenshot.
	 *
	 * @param config the config
	 * @param next the next
	 * @param testClass the test class
	 * @param method the method
	 * @param target the target
	 */
	public AbstractStatementWithScreenshot(String config, Statement next, TestClass testClass, FrameworkMethod method, Object target) {
		this.config = config;
		this.nextStatement = next;
		this.testClass = testClass;
		this.frameworkMethod = method;
		this.target = target;
	}

	/**
	 * Checks if is class level.
	 *
	 * @return true, if is class level
	 */
	protected boolean isClassLevel(){
		return target == null;
	}
	
	/**
	 * Creates the screenshot.
	 */
	protected void createScreenshot() {
		try {
			ScreenshotCapturer capturer = ScreenshotCapturer.getInstance();
			capturer.captureScreenshotOnFailure(config, getScreenshotFilename());
		} catch (CaptureScreenshotException ex) {
			ex.printInfo(log);
		}
	}
	
	/**
	 * Creates the screenshot.
	 *
	 * @param description the description
	 */
	protected void createScreenshot(String description) {
		try {
			ScreenshotCapturer capturer = ScreenshotCapturer.getInstance();
			capturer.captureScreenshotOnFailure(config, getScreenshotFilename(description));
		} catch (CaptureScreenshotException ex) {
			ex.printInfo(log);
		}
	}
	
	/**
	 * Creates the screenshot.
	 *
	 * @param description the description
	 * @param extensionClass the extension class
	 */
	protected void createScreenshot(String description, Class<?> extensionClass) {
		try {
			ScreenshotCapturer capturer = ScreenshotCapturer.getInstance();
			capturer.captureScreenshotOnFailure(config, getScreenshotFilename(description, extensionClass));
		} catch (CaptureScreenshotException ex) {
			ex.printInfo(log);
		}
	}
	
	private String getScreenshotFilename(String description, Class<?> extraClass){
		String methodName = frameworkMethod != null ? frameworkMethod.getName() : null;
		return ScreenshotCapturer.getScreenshotFileName(
				testClass.getJavaClass(),
				methodName,
				description + "_" + extraClass.getSimpleName());
	}
	
	private String getScreenshotFilename(String description){
		String methodName = frameworkMethod != null ? frameworkMethod.getName() : null;
		return ScreenshotCapturer.getScreenshotFileName(
				testClass.getJavaClass(),
				methodName,
				description);
	}
	
	private String getScreenshotFilename(){
		String methodName = frameworkMethod != null ? frameworkMethod.getName() : null;
		return ScreenshotCapturer.getScreenshotFileName(
				testClass.getJavaClass(),
				methodName,
				null);
	}
}
