/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.test.integration.runner.injection;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;
import org.eclipse.reddeer.junit.test.integration.runner.injection.RunnerIntegrationRequirement.RequirementAAnnotation;
import org.eclipse.reddeer.junit.test.integration.runner.injection.RunnerIntegrationRequirement2.RequirementAAnnotation2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectRequirementsRedDeerSuite.class)
@RequirementAAnnotation
@RequirementAAnnotation2
public class InjectRequirementsTest {

	@InjectRequirement
	private static RunnerIntegrationRequirement staticRequirement;
	
	private static RunnerIntegrationRequirement previousStaticRequirement;

	@InjectRequirement
	private RunnerIntegrationRequirement2 requirement;
	
	private RunnerIntegrationRequirement2 previousRequirement;
	
	private static int run;
	
	@BeforeClass
	public static void beforeClass(){
		assertNotNull(staticRequirement);
		if (run == 0){
			previousStaticRequirement = staticRequirement;
			run++;
		} else {
			assertNotEquals("Static requirement should be injected for every test before @BeforeClass method", 
					previousStaticRequirement, staticRequirement);
		}
	}
	
	@Test
	public void test(){
		assertNotNull(requirement);
		if (run == 0){
			previousRequirement = requirement;
		} else {
			assertNotEquals("Requirement should be injected for every test calling test method", 
					previousRequirement, requirement);
		}
	}
}
