package org.jboss.reddeer.junit.extensionpoint;

public interface IAfterTest {
	/**
	 * Contains action proceeded prior test is run
	 * Method is run only when method hasToRun() returns true
	 */
	public void runAfterTest();
	/**
	 * Returns true when method runAfterTest() has to run
	 * Could be use to run method runAfterTest() only once
	 * @return
	 */
	public boolean hasToRun();
}
