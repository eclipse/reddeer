package org.jboss.reddeer.junit.internal.runner;

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

	private RequirementsBuilder requirementsBuilder = new RequirementsBuilder();
	
	private TestRunConfiguration config;
	
	public RequirementsRunnerBuilder(TestRunConfiguration config) {
		this.config = config;
	}

	@Override
	public Runner runnerForClass(Class<?> clazz) throws Throwable {
		Requirements requirements = requirementsBuilder.build(clazz, config.getRequirementConfiguration());
		if (requirements.canFulfill()){
			return new RequirementsRunner(clazz, requirements);
		} else {
			return null;
		}
	}
	
	public void setRequirementsBuilder(RequirementsBuilder requirementsBuilder) {
		this.requirementsBuilder = requirementsBuilder;
	}
}
