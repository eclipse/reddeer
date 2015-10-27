package org.jboss.reddeer.junit.test.integration.runner.order.param;

import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createAfter;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createAfterClass;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createBefore;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createBeforeClass;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createCleanup;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createFulfill;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createIAfter;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createIAfterClass;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createIBefore;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createIBeforeClass;
import static org.jboss.reddeer.junit.test.integration.runner.order.TestSequence.createTest;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.test.integration.runner.IAfterTestImpl;
import org.jboss.reddeer.junit.test.integration.runner.IBeforeTestImpl;
import org.jboss.reddeer.junit.test.integration.runner.RunnerIntegrationPropertyRequirement;
import org.jboss.reddeer.junit.test.integration.runner.order.TestSequenceRedDeerSuite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class ParametrizedTestRunner extends TestSequenceRedDeerSuite {

	private static final List<Object> expectedSequence;

	static {
		expectedSequence = new ArrayList<Object>();
		// suite 1
		expectedSequence.add(createIBeforeClass(IBeforeTestImpl.class));
		expectedSequence.add(createFulfill(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createBeforeClass(ParametrizedTest.class));
		// param1
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createBefore(ParametrizedTest.class));
		expectedSequence.add(createTest(ParametrizedTest.class));
		expectedSequence.add(createAfter(ParametrizedTest.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		// param2
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createBefore(ParametrizedTest.class));
		expectedSequence.add(createTest(ParametrizedTest.class));
		expectedSequence.add(createAfter(ParametrizedTest.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		expectedSequence.add(createAfterClass(ParametrizedTest.class));
		expectedSequence.add(createCleanup(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createIAfterClass(IAfterTestImpl.class));
		// suite 2
		expectedSequence.add(createIBeforeClass(IBeforeTestImpl.class));
		expectedSequence.add(createFulfill(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createBeforeClass(ParametrizedTest.class));
		// param 1
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createBefore(ParametrizedTest.class));
		expectedSequence.add(createTest(ParametrizedTest.class));
		expectedSequence.add(createAfter(ParametrizedTest.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		// param 2
		expectedSequence.add(createIBefore(IBeforeTestImpl.class));
		expectedSequence.add(createBefore(ParametrizedTest.class));
		expectedSequence.add(createTest(ParametrizedTest.class));
		expectedSequence.add(createAfter(ParametrizedTest.class));
		expectedSequence.add(createIAfter(IAfterTestImpl.class));
		expectedSequence.add(createAfterClass(ParametrizedTest.class));
		expectedSequence.add(createCleanup(RunnerIntegrationPropertyRequirement.class));
		expectedSequence.add(createIAfterClass(IAfterTestImpl.class));
	}
	
	public ParametrizedTestRunner(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}

	public ParametrizedTestRunner(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(clazz, builder);
	}
	
	@Override
	protected List<?> getExpectedSequence() {
		return expectedSequence;
	}
}
