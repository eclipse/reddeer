package org.jboss.reddeer.junit.internal.runner;

import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.parameterized.ParametersRunnerFactory;
import org.junit.runners.parameterized.TestWithParameters;

/**
 * ParametersRunnerFactory for creating ParameterizedRequirementsRunners.
 * 
 * @author rhopp
 *
 */
public class ParameterizedRequirementsRunnerFactory implements ParametersRunnerFactory {

	/* (non-Javadoc)
	 * @see org.junit.runners.parameterized.ParametersRunnerFactory#createRunnerForTestWithParameters(org.junit.runners.parameterized.TestWithParameters)
	 */
	@Override
	public Runner createRunnerForTestWithParameters(TestWithParameters test) throws InitializationError {
		return new ParameterizedRequirementsRunner(test);
	}

}
