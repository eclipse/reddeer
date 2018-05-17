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
package org.eclipse.reddeer.junit.test.integration.runner;

import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.eclipse.reddeer.junit.test.integration.runner.order.TestSequence;
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
