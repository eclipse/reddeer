package org.jboss.reddeer.junit.integration.runner.injection;

import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.internal.configuration.SuiteConfiguration;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class InjectRequirementsRedDeerSuite extends RedDeerSuite {

	protected static final String LOCATIONS_ROOT_DIR = "resources/org/jboss/reddeer/junit/integration/runner/order";
	
	public InjectRequirementsRedDeerSuite(Class<?> clazz, RunnerBuilder builder)
			throws InitializationError {
		super(heck(clazz), builder);
	}

	protected InjectRequirementsRedDeerSuite(Class<?> clazz, RunnerBuilder builder,
			SuiteConfiguration config) throws InitializationError {
		super(clazz, builder, config);
	}
	
	/**
	 * Hecky hook for setting the system property.
	 * @param clazz
	 * @return
	 */
	private static Class<?> heck(Class<?> clazz){
		System.setProperty(RedDeerProperties.CONFIG_FILE.getName(), LOCATIONS_ROOT_DIR);
		return clazz;
	}
}
