package org.jboss.reddeer.junit.internal.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class represents test class and set of requirement configuration classes used to run with it.
 * Can hold more test classes for the same set of configurations.
 * @author odockal
 *
 */
public class TestClassRequirementMap {

	private Set<Class<?>> requirementsConfiguration = new HashSet<>();
	
	private List<Class<?>> testClasses = new ArrayList<>();
	
	/**
	 * Class constructor.
	 * @param configs set of configuration classes
	 * @param clazz test classes
	 */
	public TestClassRequirementMap(Set<Class<?>> configs, Class<?> clazz) {
		this.requirementsConfiguration = configs;
		addClass(clazz);
	}
	
	/**
	 * Add another test class.
	 * @param clazz the test class
	 */
	public void addClass(Class<?> clazz) {
		if (clazz != null) {
			this.testClasses.add(clazz);
		}
	}
	
	/**
	 * Checks whether set of req. configurations is equal to the given set.
	 * @param set given set of configurations
	 * @return true if the sets are equals, more formally: Two sets are equal if and only if they have the same elements. 
	 */
	public boolean equalAnnotationSet(Set<Class<?>> set) {
		return this.requirementsConfiguration.containsAll(set) && set.containsAll(this.requirementsConfiguration);
	}
	
	public List<Class<?>> getClasses() {
		return this.testClasses;
	}
	
	/**
	 * Gets all test classes as an array.
	 * @return all test classes as an array
	 */
	public Class<?>[] getClassesAsArray() {
		return this.testClasses.toArray(new Class<?>[this.testClasses.size()]);
	}
	
	/**
	 * Returns requirement configurations set.
	 * @return set of requirement configurations
	 */
	public Set<Class<?>> getRequirementConfiguration() {
		return this.requirementsConfiguration;
	}
}
