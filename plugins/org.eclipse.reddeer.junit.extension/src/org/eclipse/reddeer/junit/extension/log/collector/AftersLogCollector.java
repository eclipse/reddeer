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
package org.eclipse.reddeer.junit.extension.log.collector;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.direct.platform.Platform;
import org.eclipse.reddeer.junit.extension.ExtensionPriority;
import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Afters log collector provides collecting workbench log at two points. First one is 
 * after execution of AfterTest extensions. Second point is after execution of AfterClass
 * extensions.
 * 
 * @author mlabuda@redhat.com
 * @since 1.2.0
 *
 */
public class AftersLogCollector extends LogCollector implements IAfterTest {

	public static String AFTER_TEST_METHOD_DESCRIPTION = 
			"Log entries collected between before extensions (included) and\n"
			+ "after extensions (included) for test method:"; 
	
	public static final String AFTER_TEST_CLASS_DESCRIPTION = 
			"Log entries collected between after extensions (excluded) \n"
			+ "and after class extensions (included):"; 
	
	@Override
	public long getPriority() {
		return ExtensionPriority.AFTERSLOG_COLLECTOR_PRIORITY;
	}

	@Override
	public void runAfterTestClass(String config, TestClass testClass) {
		processWorkbenchLog(config, testClass.getJavaClass().getSimpleName(), AFTER_TEST_CLASS_DESCRIPTION);
		Platform.getWorkbenchLog().delete();
	}

	@Override
	public void runAfterTest(String config, Object target, FrameworkMethod method) {
		constructAfterTestMethodDescription(method.getMethod().getName());
		processWorkbenchLog(config, method.getDeclaringClass().getSimpleName(), AFTER_TEST_METHOD_DESCRIPTION);
		Platform.getWorkbenchLog().delete();
	}

	@Override
	public boolean hasToRun() {
		return RedDeerProperties.LOG_COLLECTOR_ENABLED.getBooleanValue() && eclipseLogFileExists();
	}

	private void constructAfterTestMethodDescription(String testMethodName) {
		AFTER_TEST_METHOD_DESCRIPTION =  "Log entries collected between before extensions (included) and\n"
				+ "after extensions (included) for test method " + testMethodName + ":";  
	}
}
