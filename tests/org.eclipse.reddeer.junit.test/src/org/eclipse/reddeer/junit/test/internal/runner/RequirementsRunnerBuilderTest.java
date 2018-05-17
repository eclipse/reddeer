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
package org.eclipse.reddeer.junit.test.internal.runner;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.reddeer.junit.internal.configuration.RequirementConfigurationSet;
import org.eclipse.reddeer.junit.internal.runner.ParameterizedRequirementsRunner;
import org.eclipse.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.eclipse.reddeer.junit.internal.runner.ParameterizedRunner;
import org.eclipse.reddeer.junit.internal.runner.RequirementsRunnerBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;
import org.junit.runners.ParentRunner;

public class RequirementsRunnerBuilderTest {

	private RequirementsRunnerBuilder runnerBuilder;
	
	@Before
	public void setup(){
		runnerBuilder = new RequirementsRunnerBuilder(new RequirementConfigurationSet());
	}
	
	@Test
	public void parameterizedRunnerTest() throws Throwable{
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
