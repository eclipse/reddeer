package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
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
	
	private List<IBeforeTest> beforeTestExtensions;
	
	private TestRunConfiguration config;
	
	private RunListener[] runListeners;
	
	public RequirementsRunnerBuilder(TestRunConfiguration config) {
		this(config,null,null);
	}
	
	public RequirementsRunnerBuilder(TestRunConfiguration config , RunListener[] runListeners , List<IBeforeTest> beforeTestExtensions) {
		this.config = config;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
	}

	@Override
	public Runner runnerForClass(Class<?> clazz) throws Throwable {
		log.info("Found test " + clazz);
		Requirements requirements = requirementsBuilder.build(clazz, config.getRequirementConfiguration());
		if (requirements.canFulfill()){
			log.info("All requirements can be fulfilled, the test will run");
			return new RequirementsRunner(clazz, requirements, config.getId(),runListeners, beforeTestExtensions);
		} else {
			log.info("All requirements cannot be fulfilled, the test will NOT run");
			return null;
		}
	}
	
	public void setRequirementsBuilder(RequirementsBuilder requirementsBuilder) {
		this.requirementsBuilder = requirementsBuilder;
	}
}
