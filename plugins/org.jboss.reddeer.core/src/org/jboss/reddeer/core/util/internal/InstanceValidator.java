package org.jboss.reddeer.core.util.internal;

/**
 * Simple instance validator
 * @author Vlado Pakan
 * @author Jiri Peterka
 * @deprecated since 0.8, use {@link #org.jboss.reddeer.core.util.InstanceValidator}
 */
@Deprecated
public class InstanceValidator {
	/**
	 * Simple validation method for non-nullable arguments.
	 * @param argument to check
	 * @param argumentName for exception message
	 * @throws IllegalArgumentException if {@code argument} is {@code null}
	 */
	public static void checkNotNull(Object argument, String argumentName) {
		if (argument == null) {
			throw new IllegalArgumentException("Argument '" + argumentName 
					+ "' should not be null.");
		}
	}
}
