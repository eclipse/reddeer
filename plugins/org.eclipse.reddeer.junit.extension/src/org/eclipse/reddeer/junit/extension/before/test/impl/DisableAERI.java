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
package org.eclipse.reddeer.junit.extension.before.test.impl;

import org.eclipse.reddeer.direct.preferences.PreferencesUtil;
import org.eclipse.reddeer.junit.extensionpoint.IBeforeTest;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Disable AERI reporting by setting send mode to never
 * 
 * @author rawagner
 *
 */
public class DisableAERI implements IBeforeTest {

	@Override
	public long getPriority() {
		return Long.MAX_VALUE;
	}

	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		PreferencesUtil.setAERISendMode("NEVER");
	}

	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {

	}

	@Override
	public boolean hasToRun() {
		return true;
	}

}
