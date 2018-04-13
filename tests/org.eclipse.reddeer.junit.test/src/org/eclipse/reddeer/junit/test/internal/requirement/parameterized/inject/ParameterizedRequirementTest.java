/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.internal.requirement.parameterized.inject;

import static org.junit.Assert.assertNotNull;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

@SuppressWarnings("restriction")
public class ParameterizedRequirementTest {

	@Test
	public void testStaticInject() throws InitializationError {
		executeTestClass(ParameterizedStaticReqTestClazz.class);
		assertNotNull(ParameterizedStaticReqTestClazz.getReq());
	}

	@Test
	public void testRequirementInjectionInBefore() throws InitializationError {
		ParameterizedStaticReqTestClazz.reqInBefore = null;
		executeTestClass(ParameterizedStaticReqTestClazz.class);
		assertNotNull(ParameterizedStaticReqTestClazz.reqInBefore);
	}

	@Test
	public void testRequirementInjectionInBeforeClass() throws InitializationError {
		ParameterizedStaticReqTestClazz.reqInBeforeClass = null;
		executeTestClass(ParameterizedStaticReqTestClazz.class);
		assertNotNull(ParameterizedStaticReqTestClazz.reqInBeforeClass);
	}

	@Test
	public void testRequirementInjectionInParameters() throws InitializationError {
		ParameterizedStaticReqTestClazz.reqInParameters = null;
		executeTestClass(ParameterizedStaticReqTestClazz.class);
		assertNotNull(ParameterizedStaticReqTestClazz.reqInParameters);
	}

	private void executeTestClass(Class<?> testClass) throws InitializationError {
		System.clearProperty(RedDeerProperties.CONFIG_FILE.getName());
		RunNotifier notifier = new RunNotifier();
		AllDefaultPossibilitiesBuilder builder = new AllDefaultPossibilitiesBuilder(true);
		RedDeerSuite rs = new RedDeerSuite(testClass, builder);
		rs.run(notifier);
	}
}
