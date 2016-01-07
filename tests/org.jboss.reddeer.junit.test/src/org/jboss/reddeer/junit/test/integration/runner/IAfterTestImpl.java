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

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequence;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class IAfterTestImpl implements IAfterTest {

	@Override
	public void runAfterTestClass(String config, TestClass testClass) {
		TestSequence.addIAftersClass(IAfterTestImpl.class);
	}
	
	@Override
	public void runAfterTest(String config, Object target, FrameworkMethod method) {
		TestSequence.addIAfters(IAfterTestImpl.class);
	}

	@Override
	public boolean hasToRun() {
		return true;
	}
}
