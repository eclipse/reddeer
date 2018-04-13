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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.junit.test.internal.requirement.parameterized.inject.RequirementImpl.RequirementAnnot;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

@RunWith(RedDeerSuite.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
@RequirementAnnot(value = "abc")
public class ParameterizedStaticReqTestClazz {

	@InjectRequirement
	private static RequirementImpl requirementImpl;
	
	static RequirementImpl reqInParameters;
	static RequirementImpl reqInBeforeClass;
	static RequirementImpl reqInBefore;

	@Parameters
	public static Collection<Object> data() {
		reqInParameters = requirementImpl;
		return Arrays.asList(new Object[] { 1, 2 });
	}

	private int number;
	
	public ParameterizedStaticReqTestClazz(int number) {
		this.number = number;
	}
	
	@BeforeClass
	public static void beforeClassSetup() {
		reqInBeforeClass = requirementImpl;
	}
	
	@Before
	public void beforeSetup() {
		reqInBefore = requirementImpl;
	}
	
	public static RequirementImpl getReq() {
		return requirementImpl;
	}

	@Test
	public void baseTest() {
		assertTrue("Number passed from parametrized test should be either 1 or 2", number == 1 || number == 2);
	}

}
