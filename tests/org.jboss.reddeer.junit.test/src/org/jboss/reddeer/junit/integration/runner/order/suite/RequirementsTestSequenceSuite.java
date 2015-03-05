package org.jboss.reddeer.junit.integration.runner.order.suite;

import static org.jboss.reddeer.junit.integration.runner.order.TestSequence.createAfter;
import static org.jboss.reddeer.junit.integration.runner.order.TestSequence.createAfterClass;
import static org.jboss.reddeer.junit.integration.runner.order.TestSequence.createBefore;
import static org.jboss.reddeer.junit.integration.runner.order.TestSequence.createBeforeClass;
import static org.jboss.reddeer.junit.integration.runner.order.TestSequence.createFulfill;
import static org.jboss.reddeer.junit.integration.runner.order.TestSequence.createTest;
import static org.jboss.reddeer.junit.integration.runner.order.TestSequence.createCleanup;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.integration.runner.RunnerIntegrationPropertyRequirement;
import org.jboss.reddeer.junit.integration.runner.order.TestSequenceRedDeerSuite;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Checks if the right sequence is called if there are mixed test cases with and without requirements. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementsTestSequenceSuite extends TestSequenceRedDeerSuite {

	private static final List<Object> expectedSequence;

	static {
		expectedSequence = new ArrayList<Object>();

		// suite 1
		// test case 1
		expectedSequence.add(createFulfill(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createBeforeClass(RequirementsTestCase.class));
		expectedSequence.add(createBefore(RequirementsTestCase.class));
		expectedSequence.add(createTest(RequirementsTestCase.class));
		expectedSequence.add(createAfter(RequirementsTestCase.class));
		expectedSequence.add(createBefore(RequirementsTestCase.class));
		expectedSequence.add(createTest(RequirementsTestCase.class));
		expectedSequence.add(createAfter(RequirementsTestCase.class));
		expectedSequence.add(createAfterClass(RequirementsTestCase.class));
		expectedSequence.add(createCleanup(RunnerIntegrationPropertyRequirement.class));
		// test case 2
		expectedSequence.add(createBeforeClass(NoRequirementsTestCase.class));
		expectedSequence.add(createBefore(NoRequirementsTestCase.class));
		expectedSequence.add(createTest(NoRequirementsTestCase.class));
		expectedSequence.add(createAfter(NoRequirementsTestCase.class));
		expectedSequence.add(createAfterClass(NoRequirementsTestCase.class));
		// suite 2
		// test case 1
		expectedSequence.add(createFulfill(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createBeforeClass(RequirementsTestCase.class));
		expectedSequence.add(createBefore(RequirementsTestCase.class));
		expectedSequence.add(createTest(RequirementsTestCase.class));
		expectedSequence.add(createAfter(RequirementsTestCase.class));
		expectedSequence.add(createBefore(RequirementsTestCase.class));
		expectedSequence.add(createTest(RequirementsTestCase.class));
		expectedSequence.add(createAfter(RequirementsTestCase.class));
		expectedSequence.add(createAfterClass(RequirementsTestCase.class));
		expectedSequence.add(createCleanup(RunnerIntegrationPropertyRequirement.class));
		// test case 2
		expectedSequence.add(createBeforeClass(NoRequirementsTestCase.class));
		expectedSequence.add(createBefore(NoRequirementsTestCase.class));
		expectedSequence.add(createTest(NoRequirementsTestCase.class));
		expectedSequence.add(createAfter(NoRequirementsTestCase.class));
		expectedSequence.add(createAfterClass(NoRequirementsTestCase.class));
	}

	public RequirementsTestSequenceSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}

	public RequirementsTestSequenceSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}

	@Override
	protected List<?> getExpectedSequence() {
		return expectedSequence;
	}
}
