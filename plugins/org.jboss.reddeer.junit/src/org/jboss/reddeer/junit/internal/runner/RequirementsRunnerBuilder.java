package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.RequirementsBuilder;
import org.jboss.reddeer.common.logging.Logger;
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

	public TestsExecutionManager testsManager;

	private Logger log = Logger.getLogger(RequirementsRunnerBuilder.class);
	
	private RequirementsBuilder requirementsBuilder = new RequirementsBuilder();
	
	private List<IBeforeTest> beforeTestExtensions;
	private List<IAfterTest> afterTestExtensions;
	
	private TestRunConfiguration config;
	
	private RunListener[] runListeners;
	
	public RequirementsRunnerBuilder(TestRunConfiguration config) {
		this(config, null, null, null, null);
	}
	
	public RequirementsRunnerBuilder(TestRunConfiguration config , RunListener[] runListeners , List<IBeforeTest> beforeTestExtensions) {
		this(config, runListeners, beforeTestExtensions, null, null);
	}
	
	public RequirementsRunnerBuilder(TestRunConfiguration config , RunListener[] runListeners , List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions, TestsExecutionManager testsManager) {
		this.config = config;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
		this.testsManager = testsManager;
	}

	@Override
	public Runner runnerForClass(Class<?> clazz) throws Throwable {
		log.info("Found test " + clazz);
		if(testsManager != null) {
			testsManager.addTest(clazz);
		}
		Requirements requirements = requirementsBuilder.build(clazz, config.getRequirementConfiguration());
		if (requirements.canFulfill()){
			log.info("All requirements can be fulfilled, the test will run");
			if(testsManager != null) {
				testsManager.addExecutedTest(clazz);
			}
			return new RequirementsRunner(clazz, requirements, config.getId(),runListeners, beforeTestExtensions, afterTestExtensions);
		} else {
			log.info("All requirements cannot be fulfilled, the test will NOT run");
			return null;
		}
	}
	
	public void setRequirementsBuilder(RequirementsBuilder requirementsBuilder) {
		this.requirementsBuilder = requirementsBuilder;
	}
}
