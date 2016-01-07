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
package org.jboss.reddeer.junit.test.integration.runner.injection;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement;
import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement2;
import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement.RequirementAAnnotation;
import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement2.RequirementAAnnotation2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(InjectRequirementsRedDeerSuite.class)
@RequirementAAnnotation
@RequirementAAnnotation2
public class InjectRequirementsTest {

	@InjectRequirement
	private static RunnerIntegrationPropertyRequirement staticRequirement;
	
	private static RunnerIntegrationPropertyRequirement previousStaticRequirement;

	@InjectRequirement
	private RunnerIntegrationPropertyRequirement2 requirement;
	
	private RunnerIntegrationPropertyRequirement2 previousRequirement;
	
	private static int run;
	
	@BeforeClass
	public static void beforeClass(){
		assertNotNull(staticRequirement);
		if (run == 0){
			previousStaticRequirement = staticRequirement;
			run++;
		} else {
			assertNotEquals("Static requirement should be injected for every test before @BeboreClass method", 
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
