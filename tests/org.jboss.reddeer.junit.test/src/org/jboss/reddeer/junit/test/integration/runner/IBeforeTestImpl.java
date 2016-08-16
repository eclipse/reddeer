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
package org.jboss.reddeer.junit.test.integration.runner;

import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class IBeforeTestImpl implements IBeforeTest {

	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		TestSequence.addIBeforesClass(IBeforeTestImpl.class);
	}
	
	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		TestSequence.addIBefores(IBeforeTestImpl.class);
	}

	@Override
	public boolean hasToRun() {
		return true;
	}

	@Override
	public long getPriority() {
		return 0;
	}
}
