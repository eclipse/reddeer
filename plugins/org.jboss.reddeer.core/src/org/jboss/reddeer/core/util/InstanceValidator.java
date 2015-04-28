package org.jboss.reddeer.core.util;

/**
 * Simple instance validator.
 * 
 * @author Vlado Pakan
 * @author Jiri Peterka
 */
public class InstanceValidator {
	
	/**
	 * Simple validation method for non-nullable arguments.
	 * @param argument argument to check
	 * @param argumentName name for exception message
	 * @throws IllegalArgumentException if {@code argument} is {@code null}
	 */
	public static void checkNotNull(Object argument, String argumentName) {
		if (argument == null) {
			throw new IllegalArgumentException("Argument '" + argumentName 
					+ "' should not be null.");
		}
	}
}
