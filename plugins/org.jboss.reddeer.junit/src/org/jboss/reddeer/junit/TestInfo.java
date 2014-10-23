package org.jboss.reddeer.junit;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
/**
 * Stores information about test.
 * Used to send informations about running tests to {@link IAfterTest} JUnit extensions 
 * @author vlado pakan
 *
 */
public class TestInfo {

	private String methodName;
	private String config;
	private Class<?> testObjectClass;
	
	/**
	 * Creates TestInfo.
	 * @param methodName
	 * @param config
	 * @param testObjectClass
	 */
	public TestInfo(String methodName, String config,
			Class<?> testObjectClass) {
		super();
		this.methodName = methodName;
		this.config = config;
		this.testObjectClass = testObjectClass;
	}
	/**
	 * Returns test method name. 
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * Returns configuration name.
	 * @return
	 */
	public String getConfig() {
		return config;
	}
	/**
	 * Returns test object aka instance of running test
	 * @return
	 */
	public Class<?> getTestObjectClass() {
		return testObjectClass;
	}
}
