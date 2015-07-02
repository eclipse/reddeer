package org.jboss.reddeer.junit.test.integration.runner.order.testcase;

import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createAfter;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createAfterClass;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createBefore;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createBeforeClass;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createFulfill;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createTest;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequenceRedDeerSuite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class RequirementsRunner extends TestSequenceRedDeerSuite {

	private static final List<Object> expectedSequence;

	static {
		expectedSequence = new ArrayList<Object>();
		// suite 1
		expectedSequence.add(createFulfill(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createBeforeClass(RequirementsTestCase.class));
		expectedSequence.add(createBefore(RequirementsTestCase.class));
		expectedSequence.add(createTest(RequirementsTestCase.class));
		expectedSequence.add(createAfter(RequirementsTestCase.class));
		expectedSequence.add(createAfterClass(RequirementsTestCase.class));
		// suite 2
		expectedSequence.add(createFulfill(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createBeforeClass(RequirementsTestCase.class));
		expectedSequence.add(createBefore(RequirementsTestCase.class));
		expectedSequence.add(createTest(RequirementsTestCase.class));
		expectedSequence.add(createAfter(RequirementsTestCase.class));
		expectedSequence.add(createAfterClass(RequirementsTestCase.class));
	}
	
	public RequirementsRunner(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}

	public RequirementsRunner(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}
	
	@Override
	protected List<?> getExpectedSequence() {
		return expectedSequence;
	}
}
