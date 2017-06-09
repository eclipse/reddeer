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
package org.eclipse.reddeer.junit.test.internal.runner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.eclipse.reddeer.junit.internal.runner.RequirementsRunner;
import org.junit.Test;

public class RequirementsRunnerTest {

	private RequirementsRunner requirementsRunner;
	
	@Test
	public void testInjectMethodInvocation() throws Exception {
		
		Requirements requirements = mock(Requirements.class);
		requirementsRunner = new RequirementsRunner(SimpleTest.class, requirements, "no-configuration");
		
		RequirementsInjector reqInjector = mock(RequirementsInjector.class);
		requirementsRunner.setRequirementsInjector(reqInjector);
		
		Object testInstance = requirementsRunner.createTest();
		verify(reqInjector, times(1)).inject(testInstance, requirements);
		
	}
	
	public static class SimpleTest {
		@Test
		public void testNothing() {
			
		}
	}
	
}
