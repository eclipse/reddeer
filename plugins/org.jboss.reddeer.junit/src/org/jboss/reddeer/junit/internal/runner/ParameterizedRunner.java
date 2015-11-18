package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Parameterized;
import org.junit.runners.model.Statement;

/**
 * This is parent runner for one parameterized test class. It's purpose is to
 * fulfill requirements and create child parameterized runners (
 * {@link ParameterizedRequirementsRunner}.
 * 
 * @author rhopp
 *
 */

public class ParameterizedRunner extends Parameterized {

	private String configId;
	private Requirements requirements;
	private RunListener[] runListeners;
	private List<IBeforeTest> beforeTestExtensions;
	private List<IAfterTest> afterTestExtensions;

	private RequirementsRunner requirementsRunner;
	
	/**
	 * Instantiates a new parameterized runner.
	 *
	 * @param clazz the clazz
	 * @param requirements the requirements
	 * @param configId the config id
	 * @param runListeners the run listeners
	 * @param beforeTestExtensions the before test extensions
	 * @param afterTestExtensions the after test extensions
	 * @throws Throwable the throwable
	 */
	public ParameterizedRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners,
			List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions) throws Throwable {
		super(clazz);
		this.requirementsRunner = new RequirementsRunner(clazz, requirements, configId, runListeners, beforeTestExtensions, afterTestExtensions);
		
		this.requirements = requirements;
		this.configId = configId;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#getName()
	 */
	@Override
	protected String getName() {
		return super.getName() + " " + configId;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.Parameterized#getChildren()
	 */
	@Override
	protected List<Runner> getChildren() {
		List<Runner> children = super.getChildren();
		for (Runner runner : children) {
			if (runner instanceof ParameterizedRequirementsRunner) {
				ParameterizedRequirementsRunner testRunner = (ParameterizedRequirementsRunner) runner;
				testRunner.setConfigId(configId);
				testRunner.setRequirements(requirements);
				testRunner.setRunListeners(runListeners);
				testRunner.setAfterTestExtensions(afterTestExtensions);
				testRunner.setBeforeTestExtensions(beforeTestExtensions);
			} else {
				return null;
			}
		}
		return children;
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#withBeforeClasses(org.junit.runners.model.Statement)
	 */
	@Override
	protected Statement withBeforeClasses(Statement statement) {
		return requirementsRunner.withBeforeClasses(statement);
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#withAfterClasses(org.junit.runners.model.Statement)
	 */
	@Override
	 protected Statement withAfterClasses(Statement statement) {
      return requirementsRunner.withAfterClasses(statement);
   }
}
