/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.rules;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.runner.statement.AbstractStatementWithScreenshot;
import org.eclipse.reddeer.junit.screenshot.CaptureScreenshotException;
import org.eclipse.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * The ErrorCollector rule allows execution of a test to continue after the
 * first problem is found. See {@link org.junit.rules.ErrorCollector} for detail
 * description. This subclass adds automatic screenshot capturing.
 * 
 * @see org.junit.rules.ErrorCollector
 * 
 * @author lvalach
 *
 */
public class ErrorCollectorWithScreenshot extends org.junit.rules.ErrorCollector {

	private static final Logger log = Logger.getLogger(ErrorCollectorWithScreenshot.class);

	private Description description;

	@Override
	public Statement apply(Statement base, Description description) {
		if (base instanceof AbstractStatementWithScreenshot) {
			this.description = description;
		}
		return super.apply(base, description);
	}

	/**
	 * {@inheritDoc} Captures screenshot.
	 */
	@Override
	public void addError(Throwable error) {
		try {
			ScreenshotCapturer.getInstance().captureScreenshotOnFailure(getConfig(), getScreenshotFilename());
		} catch (CaptureScreenshotException e) {
			e.printInfo(log);
		}
		super.addError(error);
	}

	private String getConfig() {
		String[] splited = description.getMethodName().split(" ");
		return splited.length < 1 ? RequirementConfigurationSet.EMPTY_SET_ID : splited[1];
	}

	private String getTestMethodName() {
		String[] splited = description.getMethodName().split(" ");
		return splited[0];
	}

	private Class<?> getTestClass() {
		return description.getTestClass();
	}

	/**
	 * Gets whole path to a directory where screenshots should be stored. Includes
	 * config directory.
	 * 
	 * @return path to a directory supposed to contain screenshots
	 * 
	 * @see ScreenshotCapturer.getPath
	 */
	public String getScreenshotDirPath() {
		return ScreenshotCapturer.getInstance().getScreenshotDirPath(getConfig());
	}

	/**
	 * Constructs the name of the screenshot.
	 * 
	 * @return
	 */
	private String getScreenshotFilename() {
		return ScreenshotCapturer.getScreenshotFileName(getTestClass(), getTestMethodName(), null);
	}
}
