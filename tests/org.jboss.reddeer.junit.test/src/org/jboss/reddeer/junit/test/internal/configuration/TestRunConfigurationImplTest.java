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
package org.jboss.reddeer.junit.test.internal.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.RequirementsConfigurationImpl;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfigurationImpl;
import org.jboss.reddeer.junit.test.internal.requirement.TestCustomJavaRequirement;
import org.jboss.reddeer.junit.test.internal.requirement.TestPropertyRequirementA;
import org.junit.Test;

public class TestRunConfigurationImplTest {

	@Test
	public void getId(){
		List<Object> configs = new ArrayList<>();
		
		configs.add(new TestPropertyRequirementA());
		configs.add(new TestCustomJavaRequirement());
		
		String id = new TestRunConfigurationImpl(configs).getId();
		
		assertThat(id, is(TestPropertyRequirementA.class.getSimpleName() + " " +
				TestCustomJavaRequirement.class.getSimpleName()));
	}
	
	@Test
	public void getId_empty(){
		
		String id = new TestRunConfigurationImpl(new ArrayList<>()).getId();
		
		assertThat(id, is("Empty Configuration"));
	}
	
	@Test
	public void getRequirementConfiguration_caching(){
		List<Object> configs = new ArrayList<>();
		
		configs.add(mock(TestPropertyRequirementA.class));
		configs.add(mock(TestCustomJavaRequirement.class));
		
		TestRunConfiguration config = new TestRunConfigurationImpl(configs);
		
		RequirementsConfiguration configuration1 = config.getRequirementConfiguration();
		RequirementsConfiguration configuration2 = config.getRequirementConfiguration();
		
		assertSame(configuration1, configuration2);
		assertThat(configuration1, instanceOf(RequirementsConfigurationImpl.class));
	}
	
}
