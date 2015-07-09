package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.requirement.Requirements;
import org.jboss.reddeer.junit.internal.requirement.inject.RequirementsInjector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Parameterized;
import org.junit.runners.model.FrameworkMethod;
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

	private RequirementsInjector requirementsInjector = new RequirementsInjector();
	private static final Logger log = Logger.getLogger(ParameterizedRunner.class);

	private String configId;
	private Requirements requirements;
	private RunListener[] runListeners;
	private List<IBeforeTest> beforeTestExtensions;
	private List<IAfterTest> afterTestExtensions;

	public ParameterizedRunner(Class<?> clazz, Requirements requirements, String configId, RunListener[] runListeners,
			List<IBeforeTest> beforeTestExtensions, List<IAfterTest> afterTestExtensions) throws Throwable {
		super(clazz);
		this.requirements = requirements;
		this.configId = configId;
		this.runListeners = runListeners;
		this.beforeTestExtensions = beforeTestExtensions;
		this.afterTestExtensions = afterTestExtensions;
	}

	@Override
	protected String getName() {
		return super.getName() + " " + configId;
	}

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

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		log.debug("Injecting fulfilled requirements into static fields of test class");
		requirementsInjector.inject(getTestClass().getJavaClass(), requirements);

		List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(BeforeClass.class);
		Statement s = befores.isEmpty() ? statement
				: new RunBefores(configId, null, statement, befores, null, getTestClass());
		runBeforeTest();
		return new FulfillRequirementsStatement(requirements, s);
	}

	@Override
	protected Statement withAfterClasses(Statement statement) {
		List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(AfterClass.class);
		Statement s = afters.isEmpty() ? statement
				: new RunAfters(configId, null, statement, afters, null, getTestClass());
		runAfterTest();
		return new CleanUpRequirementStatement(requirements, s);
	}

	/**
	 * Method is called before test is run. Manages
	 * org.jboss.reddeer.junit.extensionpoint.IBeforeTest extensions
	 */
	private void runBeforeTest() {
		for (IBeforeTest beforeTestExtension : beforeTestExtensions) {
			if (beforeTestExtension.hasToRun()) {
				log.debug("Run method runBeforeTest() of class " + beforeTestExtension.getClass().getCanonicalName());
				beforeTestExtension.runBeforeTest();
			}
		}
	}

	/**
	 * Method is called after test is run. Manages
	 * org.jboss.reddeer.junit.extensionpoint.IAfterTest extensions
	 */
	private void runAfterTest() {
		for (IAfterTest afterTestExtension : afterTestExtensions) {
			if (afterTestExtension.hasToRun()) {
				log.debug("Run method runAfterTest() of class " + afterTestExtension.getClass().getCanonicalName());
				afterTestExtension.runAfterTest(getTestClass());
			}
		}
	}
}
