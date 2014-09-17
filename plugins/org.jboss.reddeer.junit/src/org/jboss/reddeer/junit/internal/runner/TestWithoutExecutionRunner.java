package org.jboss.reddeer.junit.internal.runner;

import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * Special runner invoking {@link InitializationError}
 * for each test class.<br/>
 * 
 * It doesn't run tests, it just provide information about tests
 * in error's message.
 * 
 * @author Radoslav Rabara
 *
 */
public class TestWithoutExecutionRunner extends BlockJUnit4ClassRunner {

	/**
	 * Constructs {@link Runner}, which invokes {@link InitializationError}
	 * for each test class. Error has message that the specified {@link Class}
	 * was not executed because the requirements was not fulfilled
	 * for a single configuration file 
	 * 
	 * @param klass
	 * @throws InitializationError
	 */
	public TestWithoutExecutionRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}
	
	@Override
	protected String testName(FrameworkMethod method) {
		return method.getName()
				+ " - without a single run (requirements was not fulfilled)";
	}
	
	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		errors.add(new TestHasNoRunError());//tests will fails with initialization error
	}
	
	private static class TestHasNoRunError extends InitializationError {
		public TestHasNoRunError() {
			super("Test has no run"
					+ "(requirements was not fulfilled for a single test configuration)");
		}
	}
}
