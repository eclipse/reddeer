package org.jboss.reddeer.junit.internal.runner;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Suite with custom name. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class NamedSuite extends Suite {
	
	private final String suiteName;

	/**
	 * Constructor used for suites. 
	 * 
	 * @param clazz
	 * @param builder
	 * @param name
	 * @throws InitializationError
	 */
	public NamedSuite(Class<?> clazz, RunnerBuilder builder, String name) throws InitializationError {
		super(clazz, builder);
		this.suiteName = name;
	}
	
	/**
	 * Constructor used for separate undependent classes. 
	 * 
	 * @param classes
	 * @param builder
	 * @param name
	 * @throws InitializationError
	 */
	public NamedSuite(Class<?>[] classes, RunnerBuilder builder, String name) throws InitializationError {
		super(builder, EmptySuite.class, classes);
		this.suiteName = name;
	}
	
	@Override
	public String getName() {
		return suiteName;
	}
	
	@Override
	public String toString() {
		return "Suite '" + suiteName + "'";
	}
}
