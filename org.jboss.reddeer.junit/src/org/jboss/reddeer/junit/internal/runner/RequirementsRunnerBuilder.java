package org.jboss.reddeer.junit.internal.runner;

import org.apache.log4j.Logger;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.junit.runner.Runner;
import org.junit.runners.model.RunnerBuilder;

/**
 * Checks if the requirements on the test class can be fulfilled and creates a runner for the test class or
 * ignores the test. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RequirementsRunnerBuilder extends RunnerBuilder {

	private Logger log = Logger.getLogger(RequirementsRunnerBuilder.class);
	
	private RequirementsBuilder requirementsBuilder = new RequirementsBuilder();
	
	private TestRunConfiguration config;
	
	public RequirementsRunnerBuilder(TestRunConfiguration config) {
		this.config = config;
	}

	/**
	 * Checks if the requirements on the test class can be fulfilled and creates a runner for the test class
	 * @return runner for the test class
	 * @throws Throwable
	 */
	@Override
	public Runner runnerForClass(Class<?> clazz) throws Throwable {
		log.info("Found test " + clazz);
		Requirements requirements = requirementsBuilder.build(clazz, config.getRequirementConfiguration());
		if (requirements.canFulfill()){
			log.info("All requirements can be fulfilled, the test will run");
			return new RequirementsRunner(clazz, requirements);
		} else {
			log.info("All requirements cannot be fulfilled, the test will NOT run");
			return null;
		}
	}
	
	public void setRequirementsBuilder(RequirementsBuilder requirementsBuilder) {
		this.requirementsBuilder = requirementsBuilder;
	}
}
