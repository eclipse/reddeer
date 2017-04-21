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
package org.eclipse.reddeer.requirements.test.openperspective;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.properties.RedDeerProperties;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.eclipse.reddeer.junit.internal.configuration.NullTestRunConfiguration;
import org.eclipse.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.eclipse.reddeer.junit.internal.requirement.Requirements;
import org.eclipse.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.exception.RequirementsLayerException;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class OpenPerspectiveRequirementTest{

	private String oldCaptureScreenshotPropertyValue = null;
	private boolean propertyHasChanged = false;
	
	@Test
	public void canFulfillJavaPerspectiveTest() {
		Requirements requirements = getRequirements(JavaPerspectiveTestClass.class);
		assertTrue(requirements.canFulfill());
	}

	@Test
	public void canFulfillNonExistingPerspectiveTest() {
		Requirements requirements = getRequirements(NonExistingPerspectiveTestClass.class);
		assertFalse(requirements.canFulfill());
	}

	@Test
	public void fulfillJavaPerspectiveTest() {
		Requirements requirements = getRequirements(JavaPerspectiveTestClass.class);
		requirements.fulfill();
	}

	@Test(expected = RequirementsLayerException.class)
	public void fulfillNonExistingPerspectiveTest() {
		if (!"false".equalsIgnoreCase(RedDeerProperties.CAPTURE_SCREENSHOT.getValue())) {
			oldCaptureScreenshotPropertyValue = RedDeerProperties.CAPTURE_SCREENSHOT.getValue();
			System.setProperty(RedDeerProperties.CAPTURE_SCREENSHOT.getName(), "false");
			propertyHasChanged = true;
		}
		
		Requirements requirements = getRequirements(NonExistingPerspectiveTestClass.class);
		requirements.fulfill();
	}

	@After
	public void setOldValueOfCaptureScreenshotProperty() {
		if (propertyHasChanged) {
			if (oldCaptureScreenshotPropertyValue == null) {
				System.clearProperty(RedDeerProperties.CAPTURE_SCREENSHOT.getName());
			} else {
				System.setProperty(RedDeerProperties.CAPTURE_SCREENSHOT.getName(), oldCaptureScreenshotPropertyValue);
			}
			propertyHasChanged = false;
		}
	}
	
	private Requirements getRequirements(Class<?> klass) {
		RequirementsBuilder reqBuilder = new RequirementsBuilder();
		TestRunConfiguration config = new NullTestRunConfiguration();
		return reqBuilder.build(klass, config.getRequirementConfiguration(), config.getId());
	}

	@OpenPerspective(JavaPerspective.class)
	public static class JavaPerspectiveTestClass {

		@Test
		public void voidTest() {

		}
	}

	@OpenPerspective(NonExistingPerspective.class)
	public static class NonExistingPerspectiveTestClass {
		@Test
		public void voidTest() {

		}
	}
}
