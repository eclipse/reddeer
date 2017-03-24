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
package org.jboss.reddeer.junit.extension.after.test.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.core.handler.IBeforeShellIsClosed;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.junit.TestInfo;
import org.jboss.reddeer.junit.extension.ExtensionPriority;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.screenshot.ScreenshotCapturer;
import org.junit.Assert;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;
import org.jboss.reddeer.workbench.handler.WorkbenchShellHandler;

/**
 * Extension for Extension point org.jboss.reddeer.junit.after.test. It closes
 * all shells after each test. If there is an open shell the test will fail.<br>
 * Use this system property to enable/disable it:
 * 
 * - rd.closeShells=[true|false] (default=true)
 * 
 * @author Andrej Podhradsky
 *
 */
public class CloseAllShellsExt implements IAfterTest {

	public static final boolean CLOSE_ALL_SHELLS = RedDeerProperties.CLOSE_ALL_SHELLS.getBooleanValue();
	private static final Logger log = Logger.getLogger(CloseAllShellsExt.class);

	private String config;

	private TestClass testClass;

	private Object target;

	private FrameworkMethod method;

	@Override
	public void runAfterTestClass(String config, TestClass testClass) {
		this.config = config;
		this.testClass = testClass;
		this.target = null;
		this.method = null;

		run();
	}

	/**
	 * See {@link IAfterTest}
	 */
	@Override
	public void runAfterTest(String config, Object target, FrameworkMethod method) {
		this.config = config;
		this.testClass = null;
		this.target = target;
		this.method = method;

		run();
	}

	private void run() {
		BeforeShellIsClosedAdapter beforeShellIsClosedAdapter = new BeforeShellIsClosedAdapter();
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells(beforeShellIsClosedAdapter);
		if (beforeShellIsClosedAdapter.getClosedShellsTitles().size() > 0) {
			Assert.fail("The following shells remained open " + beforeShellIsClosedAdapter.getClosedShellsTitles());
		}
	}

	/**
	 * See {@link IAfterTest}
	 */
	@Override
	public boolean hasToRun() {
		return CLOSE_ALL_SHELLS;
	}

	/**
	 * See {@link IBeforeShellIsClosed}
	 */
	private class BeforeShellIsClosedAdapter implements IBeforeShellIsClosed {

		private List<String> closedShellsTitles;

		public BeforeShellIsClosedAdapter() {
			this.closedShellsTitles = new ArrayList<>();
		}

		public void runBeforeShellIsClosed(Shell shell) {
			String shellTitle = ShellHandler.getInstance().getText(shell);

			closedShellsTitles.add(shellTitle);
			try {
				String fileName;
				if (testClass != null) {
					fileName = ScreenshotCapturer.getScreenshotFileName(testClass.getJavaClass(), null,
							"CloseAllShellsExt_closing_" + shellTitle);
				} else if (target instanceof TestInfo) {
					TestInfo testInfo = (TestInfo) target;
					config = testInfo.getConfig();
					fileName = ScreenshotCapturer.getScreenshotFileName(testInfo.getTestObjectClass(),
							testInfo.getMethodName(), "CloseAllShellsExt_closing_" + shellTitle);
				} else {
					fileName = ScreenshotCapturer.getScreenshotFileName(target.getClass(), method.getName(),
							"CloseAllShellsExt_closing_" + shellTitle);
				}
				ScreenshotCapturer.getInstance().captureScreenshotOnFailure(config, fileName);
			} catch (CaptureScreenshotException e) {
				e.printStackTrace();
			}
		}

		public List<String> getClosedShellsTitles() {
			return this.closedShellsTitles;
		}
	}

	@Override
	public long getPriority() {
		return ExtensionPriority.CLOSE_ALL_SHELLS_PRIORITY;
	}

}
