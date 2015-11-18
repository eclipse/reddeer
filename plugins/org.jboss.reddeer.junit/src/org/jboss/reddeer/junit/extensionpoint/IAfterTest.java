package org.jboss.reddeer.junit.extensionpoint;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Eclipse extension for running some action after @AfterClass methods or after @After methods
 *
 */
public interface IAfterTest {

	/**
	 * Contains action proceeded after @AfterClass methods. Method is run only when
	 * method hasToRun() returns true
	 * 
	 * @param config Config ID
	 * @param testClass Test class
	 */
	public void runAfterTestClass(String config, TestClass testClass);
	
	/**
	 * Contains action proceeded after each method. Method is run only when
	 * method hasToRun() returns true
	 * 
	 * @param config Config ID
	 * @param target Target object
	 * @param method Test method
	 */
	public void runAfterTest(String config, Object target, FrameworkMethod method);

	/**
	 * Returns true when method runAfterTest() has to run.
	 *
	 * @return true, if successful
	 */
	public boolean hasToRun();
}
