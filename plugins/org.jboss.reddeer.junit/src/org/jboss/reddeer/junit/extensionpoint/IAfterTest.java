package org.jboss.reddeer.junit.extensionpoint;

public interface IAfterTest {
	/**
	 * Contains action proceeded after each method. Method is run only when
	 * method hasToRun() returns true
	 */
	public void runAfterTest();

	/**
	 * Returns true when method runAfterTest() has to run.
	 * 
	 * @return
	 */
	public boolean hasToRun();
}
