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
package org.eclipse.reddeer.junit.test.internal.requirement.inject;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.eclipse.reddeer.requirements.property.PropertyConfiguration;
import org.junit.Before;
import org.junit.Test;

public class RequirementsInjectorTest {

	private RequirementsInjector injector = new RequirementsInjector();

	private RequirementA requirementA;

	private Requirements requirements;

	private static final String PROPERTY_NAME = "key";
	private static final String PROPERTY_VALUE = "value";
	private static final String NO_CONFIG = "no-configuration";

	@Before
	public void setupRequirement() {
		PropertyConfiguration propertyConfig = new PropertyConfiguration();
		propertyConfig.addProperty(PROPERTY_NAME, PROPERTY_VALUE);

		requirementA = new RequirementA();
		requirementA.setConfiguration(propertyConfig);
	}

	@Test
	public void testInjectingToGrandParent() {
		GrandParentRequirementTestMock grandParent = new GrandParentRequirementTestMock();
		requirements = new Requirements(Arrays.asList(requirementA), GrandParentRequirementTestMock.class, NO_CONFIG);
		injector.inject(grandParent, requirements);

		assertTrue("Grand parent's requirementA1 should have injected property, but it does not have it.",
				PROPERTY_VALUE
						.equals(grandParent.getRequirementA1().getConfiguration().getPropertyValue(PROPERTY_NAME)));
		assertTrue("Grand parent's requirementA2 should have injected property, but it does not have it.",
				PROPERTY_VALUE
						.equals(grandParent.getRequirementA2().getConfiguration().getPropertyValue(PROPERTY_NAME)));
	}

	@Test
	public void testInjectingToParent() {
		ParentRequirementTestMock parent = new ParentRequirementTestMock();
		requirements = new Requirements(Arrays.asList(requirementA), ParentRequirementTestMock.class, NO_CONFIG);
		injector.inject(parent, requirements);

		assertTrue("Grand parent's requirementA1 should have injected property, but it does not have it.",
				PROPERTY_VALUE.equals(parent.getRequirementA1().getConfiguration().getPropertyValue(PROPERTY_NAME)));
		assertTrue("Grand parent's requirementA2 should have injected property, but it does not have it.",
				PROPERTY_VALUE.equals(parent.getRequirementA2().getConfiguration().getPropertyValue(PROPERTY_NAME)));
		assertTrue("Parent's requirementA3 should have injected property, but it does not have it.",
				PROPERTY_VALUE.equals(parent.getRequirementA3().getConfiguration().getPropertyValue(PROPERTY_NAME)));
	}

	@Test
	public void testInjectingToChild() {
		ChildRequirementTestMock child = new ChildRequirementTestMock();
		requirements = new Requirements(Arrays.asList(requirementA), ChildRequirementTestMock.class, NO_CONFIG);
		injector.inject(child, requirements);

		assertTrue("Grand parent's requirementA1 should have injected property, but it does not have it.",
				PROPERTY_VALUE.equals(child.getRequirementA1().getConfiguration().getPropertyValue(PROPERTY_NAME)));
		assertTrue("Grand parent's requirementA2 should have injected property, but it does not have it.",
				PROPERTY_VALUE.equals(child.getRequirementA2().getConfiguration().getPropertyValue(PROPERTY_NAME)));
		assertTrue("Parent's requirementA3 should have injected property, but it does not have it.",
				PROPERTY_VALUE.equals(child.getRequirementA3().getConfiguration().getPropertyValue(PROPERTY_NAME)));
		assertTrue("Child's requirementA4 should have injected property, but it does not have it.",
				PROPERTY_VALUE.equals(child.getRequirementA4().getConfiguration().getPropertyValue(PROPERTY_NAME)));
	}

	@Test
	public void testInjectToStatic() {
		requirements = new Requirements(Arrays.asList(requirementA), StaticRequirementTestMock.class, NO_CONFIG);
		injector.inject(StaticRequirementTestMock.class, requirements);

		assertTrue("Requirement is not injected to static field, although it should be.", PROPERTY_VALUE.equals(
				StaticRequirementTestMock.getRequirementA().getConfiguration().getPropertyValue(PROPERTY_NAME)));
	}

	@Test(expected = org.eclipse.reddeer.junit.requirement.inject.RequirementInjectionException.class)
	public void testNoRequirementFieldInjection() {
		NonExistedRequirementTestMock testInstance = new NonExistedRequirementTestMock();
		requirements = new Requirements(Arrays.asList(requirementA), NonExistedRequirementTestMock.class, NO_CONFIG);

		injector.inject(testInstance, requirements);
	}

}
