package org.jboss.reddeer.junit.internal.runner;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.junit.BeforeClass;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Fulfills the requirements before {@link BeforeClass} is called and
 * injects requirements to proper injection points 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementsRunner extends BlockJUnit4ClassRunner {
	
	private static final Logger log = Logger.getLogger(RequirementsRunner.class);
	
	private Requirements requirements;

	private RequirementsInjector requirementsInjector = new RequirementsInjector();
	
	public RequirementsRunner(Class<?> clazz, Requirements requirements) throws InitializationError {
		super(clazz);
		this.requirements = requirements;
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		Statement s = super.withBeforeClasses(statement);
		return new FulfillRequirementsStatement(requirements, s);
	}
	
	@Override
	protected Object createTest() throws Exception {
		Object testInstance = super.createTest();
		log.debug("Injecting fulfilled requirements into test instance");
		requirementsInjector.inject(testInstance, requirements);
		return testInstance;
	}

	@Override
	public void run(RunNotifier arg0) {
		LoggingRunListener loggingRunListener = new LoggingRunListener();
		arg0.addListener(loggingRunListener);
		super.run(arg0);
		arg0.removeListener(loggingRunListener);
	}
	public void setRequirementsInjector(RequirementsInjector requirementsInjector) {
		this.requirementsInjector = requirementsInjector;
	}
	
	private class LoggingRunListener extends RunListener {
		@Override
		public void testFailure(Failure failure) throws Exception {
			Throwable throwable = failure.getException();
			// it's test failure
			if (throwable instanceof AssertionError){
				log.error("Failed test: " + failure.getDescription(),throwable);
			}
			// it's Exception
			else {
				log.error("Exception in test: " + failure.getDescription(),throwable);
			}
			super.testFailure(failure);
		}
		@Override
		public void testFinished(Description description) throws Exception {
			log.info("Finished test: " + description);
			super.testFinished(description);
		}
		@Override
		public void testIgnored(Description description) throws Exception {
			log.info("Ignored test: " + description);
			super.testIgnored(description);
		}
		@Override
		public void testStarted(Description description) throws Exception {
			log.info("Started test: " + description);
			super.testStarted(description);
		}
	}
}
