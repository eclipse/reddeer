package org.jboss.reddeer.junit.internal.runner;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Divides test classes into two categories:
 * <ul>
 * <li>Test classes <b>with</b> a run</li>
 * <li>Test classes <b>without</b> a run</li>
 * </ul>
 * 
 * Usage:<br/>
 * Add test class to {@link TestsExecutionManager} using method {@link #addTest(Class)}.
 * It will be added as test without a run.<br/><br/>
 * 
 * If the test class has a run, change the state of the test class
 * using method {@link #addExecutedTest(Class)}.
 * 
 * @author Radoslav Rabara
 *
 */
public class TestsExecutionManager {
		
	private Set<Class<?>> allTestClasses = new TreeSet<Class<?>>(classNameComparator);
	private Set<Class<?>> executedTestClasses = new TreeSet<Class<?>>(classNameComparator);
	
	private static Comparator<Class<?>> classNameComparator = new Comparator<Class<?>>(){
		@Override
		public int compare(Class<?> clazz0, Class<?> clazz1) {
			return clazz0.getName().compareTo(clazz1.getName());
		}
	};
	
	/**
	 * Adds the specified <var>testClass</var> to the manager
	 * as test class WITHOUT A RUN
	 * 
	 * @param testClass test {@link Class} to be added
	 * 				as test class without a run
	 */
	public void addTest(Class<?> testClass) {
		allTestClasses.add(testClass);
	}
	
	/**
	 * Adds the specified <var>testClass</var> to the manager
	 * as test class WITH A RUN
	 * 
	 * @param testClass test {@link Class} to be added
	 * 				as test class with a run
	 */
	public void addExecutedTest(Class<?> testClass) {
		addTest(testClass);
		executedTestClasses.add(testClass);
	}
	
	/**
	 * Calculates the number of tests without a run
	 * 
	 * @return the number of tests without a run
	 */
	public boolean allTestsAreExecuted() {
		int notExecutedTestsCount = allTestClasses.size() - executedTestClasses.size();
		return notExecutedTestsCount == 0;
	}
	
	/**
	 * Returns <code>true</code> if the specified test {@link Class}
	 * has a run
	 * 
	 * @param testClass test {@link Class} whose status is to be tested 
	 * @return <code>true</code> if the specified test class has a run
	 */
	public boolean isExecuted(Class<?> testClass) {
		return executedTestClasses.contains(testClass); 
	}
}
