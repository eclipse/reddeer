package org.jboss.reddeer.junit.extensionpoint;

public interface IAfterTest {

	/**
	 * Contains action proceeded after each method. Method is run only when
	 * method hasToRun() returns true
	 * 
	 * @param target Target test
	 */
	public void runAfterTest(Object target);

	/**
	 * Returns true when method runAfterTest() has to run.
	 * 
	 * @return
	 */
	public boolean hasToRun();
}
