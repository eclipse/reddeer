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
package org.jboss.reddeer.junit.test.internal.runner;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jboss.reddeer.junit.internal.configuration.RequirementsConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.jboss.reddeer.junit.internal.runner.ParameterizedRequirementsRunner;
import org.jboss.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.jboss.reddeer.junit.internal.runner.ParameterizedRunner;
import org.jboss.reddeer.junit.internal.runner.RequirementsRunner;
import org.jboss.reddeer.junit.internal.runner.RequirementsRunnerBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runners.ParentRunner;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

public class RequirementsRunnerBuilderTest {

	private RequirementsRunnerBuilder runnerBuilder;
	
	private Requirements requirements;
	
	@Before
	public void setup(){
		TestRunConfiguration testRunConfig = mock(TestRunConfiguration.class);

		requirements = mock(Requirements.class);
		RequirementsBuilder requirementsBuilder = mock(RequirementsBuilder.class);
		when(requirementsBuilder.build(any(Class.class), any(RequirementsConfiguration.class), any(String.class))).thenReturn(requirements);
				
		runnerBuilder = new RequirementsRunnerBuilder(testRunConfig);
		runnerBuilder.setRequirementsBuilder(requirementsBuilder);
	}
	
	@Test
	public void canFulfill() throws Throwable {
		when(requirements.canFulfill()).thenReturn(true);
		
		Runner runner = runnerBuilder.runnerForClass(TestCase.class);
		
		assertNotNull(runner);
		assertTrue(isA(RequirementsRunner.class).matches(runner));
	}

	@Test
	public void cannotFulfill() throws Throwable {
		when(requirements.canFulfill()).thenReturn(false);
		
		Runner runner = runnerBuilder.runnerForClass(TestCase.class);
		
		assertNull(runner);
	}
	
	@Test
	public void parameterizedRunnerTest() throws Throwable{
		when(requirements.canFulfill()).thenReturn(true);
		Runner runner = runnerBuilder.runnerForClass(ParameterizedSuite.class);
		assertNotNull(runner);
		assertTrue(isA(ParameterizedRunner.class).matches(runner));
		ParameterizedRunner pRunner = (ParameterizedRunner) runner;
		List<Runner> children = getChildren(pRunner);
		assertEquals(2, children.size());
		for (Runner childRunner : children) {
			assertTrue(isA(ParameterizedRequirementsRunner.class).matches(childRunner));
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Runner> getChildren(ParameterizedRunner pRunner) throws Throwable{
		Method m = ParentRunner.class.getDeclaredMethod("getChildren");
		m.setAccessible(true);
		return (List<Runner>) m.invoke(pRunner);
	}

	public static class TestCase {
		@Test
		public void fakeTest(){
			
		}
	}
	
	@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
	public static class ParameterizedSuite{
		@Parameters
		public static Collection<Object> data(){
			Collection<Object> c = new ArrayList<Object>();
			c.add(new Object());
			c.add(new Object());
			return c;
		}
		
		@Test
		public void testMethod(){
			
		}
	}
}
