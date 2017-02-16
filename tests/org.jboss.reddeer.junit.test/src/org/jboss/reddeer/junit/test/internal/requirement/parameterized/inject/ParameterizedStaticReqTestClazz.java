/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.test.internal.requirement.parameterized.inject;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.junit.test.internal.requirement.parameterized.inject.RequirementImpl.RequirementAnnot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

@RunWith(RedDeerSuite.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
@RequirementAnnot(value = "abc")
public class ParameterizedStaticReqTestClazz {

	@InjectRequirement
	public static RequirementImpl requirementImpl;

	@Parameters
	public static Collection<Object> data() {
		return Arrays.asList(new Object[] { 1, 2 });
	}

	public static RequirementImpl getReq() {
		return requirementImpl;
	}

	@Test
	public void baseTest() {

	}

}
