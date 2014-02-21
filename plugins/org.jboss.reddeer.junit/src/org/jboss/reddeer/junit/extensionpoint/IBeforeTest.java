package org.jboss.reddeer.junit.extensionpoint;

public interface IBeforeTest {
	/**
	 * Contains action proceeded prior test is run
	 * Method is run only when method hasToRun() returns true
	 */
	public void runBeforeTest();
	/**
	 * Returns true when method runBeforeTest() has to run
	 * Could be use to run method runBeforeTest() only once
	 * @return
	 */
	public boolean hasToRun();
}
