package org.jboss.reddeer.junit.extensionpoint;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Eclipse extension for running some action before @BeforeClass methods or before @Before methods
 *
 */
public interface IBeforeTest {
	
	/**
	 * Contains action proceeded prior test class @BeforeClass is run
	 * Method is run only when method hasToRun() returns true
	 * 
	 * @param config Config ID
	 * @param testClass Test class
	 */
	public void runBeforeTestClass(String config, TestClass testClass);
	
	/**
	 * Contains action proceeded prior test @Before is run
	 * Method is run only when method hasToRun() returns true
	 * 
	 * @param config Config ID
	 * @param target Target object
	 * @param method Test method
	 */
	public void runBeforeTest(String config, Object target, FrameworkMethod method);
	
	/**
	 * Returns true when method runBeforeTest() has to run
	 * Could be use to run method runBeforeTest() only once
	 * @return
	 */
	public boolean hasToRun();
}
