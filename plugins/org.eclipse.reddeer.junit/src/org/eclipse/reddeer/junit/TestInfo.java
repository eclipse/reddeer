/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit;

import org.eclipse.reddeer.junit.extensionpoint.IAfterTest;
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
	 *
	 * @param methodName the method name
	 * @param config the config
	 * @param testObjectClass the test object class
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
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}
	
	/**
	 * Returns configuration name.
	 *
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}
	
	/**
	 * Returns test object aka instance of running test.
	 *
	 * @return the test object class
	 */
	public Class<?> getTestObjectClass() {
		return testObjectClass;
	}
}
