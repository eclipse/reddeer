package org.jboss.reddeer.requirements.test.openperspective;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.junit.internal.configuration.NullTestRunConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.exception.RequirementsLayerException;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
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
		if (!"false".equalsIgnoreCase(System.getProperty("captureScreenshot"))) {
			oldCaptureScreenshotPropertyValue = System.getProperty("captureScreenshot");
			System.setProperty("captureScreenshot", "false");
			propertyHasChanged = true;
		}
		
		Requirements requirements = getRequirements(NonExistingPerspectiveTestClass.class);
		requirements.fulfill();
	}

	@After
	public void setOldValueOfCaptureScreenshotProperty() {
		if (propertyHasChanged) {
			if (oldCaptureScreenshotPropertyValue == null) {
				System.clearProperty("captureScreenshot");
			} else {
				System.setProperty("captureScreenshot", oldCaptureScreenshotPropertyValue);
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
