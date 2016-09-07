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
package org.jboss.reddeer.junit.extension.log.collector;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.direct.platform.Platform;
import org.jboss.reddeer.junit.extension.ExtensionPriority;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Befores log collector provides collecting workbench log at two points. First one is 
 * before execution of any BeforeClass extension. Second point is before execution of any
 * BeforeTest extension.
 *  
 * @author mlabuda@redhat.com
 * @since 1.2.0
 */
public class BeforesLogCollector extends LogCollector implements IBeforeTest{
	
	public static final String BEFORE_TEST_METHOD_DESCRIPTION = 
			"Log entries collected between before class extensions (included)\n"
			+ "and before extensions (excluded) for a test method:";
	
	public static boolean hasToRun = true;
	
	@Override
	public long getPriority() {
		return ExtensionPriority.BEFORES_LOG_COLLECTOR_PRIORITY;
	}

	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {	
		Platform.getWorkbenchLog().delete();
		hasToRun = true;
	}

	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		processWorkbenchLog(config, method.getDeclaringClass().getSimpleName(), BEFORE_TEST_METHOD_DESCRIPTION);
		Platform.getWorkbenchLog().delete();
		hasToRun = false;
	}

	@Override
	public boolean hasToRun() {
		return RedDeerProperties.LOG_COLLECTOR_ENABLED.getBooleanValue() && hasToRun && eclipseLogFileExists();
	}
}
