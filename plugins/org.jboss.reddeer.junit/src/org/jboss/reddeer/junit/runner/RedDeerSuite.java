package org.jboss.reddeer.junit.runner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.internal.configuration.TestRunConfiguration;
import org.jboss.reddeer.junit.internal.extensionpoint.AfterTestInitialization;
import org.jboss.reddeer.junit.internal.extensionpoint.BeforeTestInitialization;
import org.jboss.reddeer.junit.internal.runner.EmptySuite;
import org.jboss.reddeer.junit.internal.runner.NamedSuite;
import org.jboss.reddeer.junit.internal.runner.RequirementsRunnerBuilder;
import org.jboss.reddeer.junit.internal.runner.TestsExecutionManager;
import org.jboss.reddeer.junit.internal.runner.TestsWithoutExecutionSuite;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * 
 * Allows to run the tests (single or a suite) for each configuration file provided.
 * 
 * @author Lucia Jelinkova
 * 
 */
public class RedDeerSuite extends Suite {

	private static final Logger log = Logger.getLogger(RedDeerSuite.class);
	// this variable has to set within static initialization block in child
	// class
	// in order to add custom listeners
	protected static RunListener[] runListeners;

	private static List<IBeforeTest> beforeTestExtensions = RedDeerSuite.initializeBeforeTestExtensions();

	private static List<IAfterTest> afterTestExtensions = RedDeerSuite.initializeAfterTestExtensions();

	/**
	 * Called by the JUnit framework.
	 * 
	 * @param clazz
	 * @param builder
	 * @throws InitializationError
	 */
	public RedDeerSuite(Class<?> clazz, RunnerBuilder builder) throws InitializationError {
		this(clazz, builder, new SuiteConfiguration());
	}

	/**
	 * The {@link EmptySuite} makes sure that the @BeforeClass and @AfterClass are not called on the suite class too
	 * often.
	 * 
	 * @param clazz
	 * @param builder
	 * @param config
	 * @throws InitializationError
	 */
	protected RedDeerSuite(Class<?> clazz, RunnerBuilder builder, SuiteConfiguration config) throws InitializationError {
		super(EmptySuite.class, createSuite(clazz, config));
	}

	/**
	 * Creates a new suite for each configuration file found.
	 * 
	 * @param clazz
	 * @param config
	 * @return
	 * @throws InitializationError
	 */
	protected static List<Runner> createSuite(Class<?> clazz, SuiteConfiguration config) throws InitializationError {
		log.info("Creating RedDeer suite...");
		TestsExecutionManager testsManager = new TestsExecutionManager();
		List<Runner> configuredSuites = new ArrayList<Runner>();
		boolean isSuite = isSuite(clazz);

		for (TestRunConfiguration testRunConfig : config.getTestRunConfigurations()) {
			log.info("Adding suite with name " + testRunConfig.getId() + " to RedDeer suite");
			if (isSuite) {
				configuredSuites.add(new NamedSuite(clazz, new RequirementsRunnerBuilder(testRunConfig, runListeners,
						beforeTestExtensions, afterTestExtensions, testsManager), testRunConfig.getId()));
			} else {
				configuredSuites.add(new NamedSuite(new Class[] { clazz }, new RequirementsRunnerBuilder(testRunConfig,
						runListeners, beforeTestExtensions, afterTestExtensions, testsManager), testRunConfig.getId()));
			}
		}

		if (!testsManager.allTestsAreExecuted()) {
			if (isSuite) {
				configuredSuites.add(new TestsWithoutExecutionSuite(clazz, testsManager));
			} else {
				configuredSuites.add(new TestsWithoutExecutionSuite(new Class[] { clazz }, testsManager));
			}
		}
		log.info("RedDeer suite created");
		return configuredSuites;
	}

	private static boolean isSuite(Class<?> clazz) {
		SuiteClasses annotation = clazz.getAnnotation(SuiteClasses.class);
		return annotation != null;
	}

	@Override
	protected String getName() {
		return "Red Deer Suite";
	}

	/**
	 * Initializes all Before Test extensions
	 */
	private static List<IBeforeTest> initializeBeforeTestExtensions() {
		List<IBeforeTest> beforeTestExts;
		// check if eclipse is running
		try {
			Class.forName("org.eclipse.core.runtime.Platform");
			log.debug("Eclipse is running");
			beforeTestExts = BeforeTestInitialization.initialize();
		} catch (ClassNotFoundException e) {
			// do nothing extension is implemented only for eclipse right now
			log.debug("Eclipse is not running");
			beforeTestExts = new LinkedList<IBeforeTest>();
		}
		return beforeTestExts;
	}

	/**
	 * Initializes all After Test extensions
	 */
	private static List<IAfterTest> initializeAfterTestExtensions() {
		List<IAfterTest> afterTestExts;
		// check if eclipse is running
		try {
			Class.forName("org.eclipse.core.runtime.Platform");
			log.debug("Eclipse is running");
			afterTestExts = AfterTestInitialization.initialize();
		} catch (ClassNotFoundException e) {
			// do nothing extension is implemented only for eclipse right now
			log.debug("Eclipse is not running");
			afterTestExts = new LinkedList<IAfterTest>();
		}
		return afterTestExts;
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		super.filter(new FilterDecorator(filter));
	}

	/**
	 * Running single test in case of parameterized test causes issue as explained in
	 * http://youtrack.jetbrains.com/issue/IDEA-65966
	 * 
	 * As a workaround we wrap the original filter and then pass it a wrapped description which removes the parameter
	 * part (See deparametrizedName).
	 */
	private static final class FilterDecorator extends Filter {
		private final Filter delegate;

		/**
		 * Constructs the decorator with a given filter.
		 * 
		 * @param delegate
		 *            Filter which will be decorated
		 */
		private FilterDecorator(Filter delegate) {
			this.delegate = delegate;
		}

		@Override
		public boolean shouldRun(Description description) {
			return delegate.shouldRun(wrap(description));
		}

		@Override
		public String describe() {
			return delegate.describe();
		}
	}

	/**
	 * Wraps a given description with a new display name (see deparametrizedName).
	 * 
	 * @param description
	 *            Description
	 * @return Description with correct display name
	 */
	private static Description wrap(Description description) {
		String name = description.getDisplayName();
		String fixedName = deparametrizedName(name);
		Description clonedDescription = Description.createSuiteDescription(fixedName, description.getAnnotations()
				.toArray(new Annotation[0]));
		for (Description child : description.getChildren()) {
			clonedDescription.addChild(wrap(child));
		}
		return clonedDescription;
	}

	/**
	 * Removes ' default' from a given description name.
	 * 
	 * @param name
	 *            Description name
	 * @return Description name without ' default'
	 */
	private static String deparametrizedName(String name) {
		return name.replaceAll(" default", "");
	}
}
