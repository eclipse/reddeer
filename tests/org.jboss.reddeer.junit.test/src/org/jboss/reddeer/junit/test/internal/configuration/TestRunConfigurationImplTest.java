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
package org.jboss.reddeer.junit.test.internal.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.RequirementsConfigurationImpl;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfigurationImpl;
import org.junit.Test;

public class TestRunConfigurationImplTest {

	@Test
	public void getId(){
		File file = new File("aaa/bbb.ccc");
		
		String id = new TestRunConfigurationImpl(file).getId();
		
		assertThat(id, is("bbb.ccc"));
	}
	
	@Test
	public void getRequirementConfiguration_caching(){
		File file = mock(File.class);
		TestRunConfiguration config = new TestRunConfigurationImpl(file);
		
		RequirementsConfiguration configuration1 = config.getRequirementConfiguration();
		RequirementsConfiguration configuration2 = config.getRequirementConfiguration();
		
		assertSame(configuration1, configuration2);
		assertThat(configuration1, instanceOf(RequirementsConfigurationImpl.class));
	}
	
}
