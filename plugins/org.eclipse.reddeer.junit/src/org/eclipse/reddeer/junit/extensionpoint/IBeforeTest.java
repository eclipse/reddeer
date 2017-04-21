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
package org.eclipse.reddeer.junit.extensionpoint;

import org.eclipse.reddeer.junit.execution.IExecutionPriority;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * Eclipse extension for running some action before @BeforeClass methods or before @Before methods
 *
 */
public interface IBeforeTest extends IExecutionPriority {
	
	/**
	 * Contains action proceeded prior test class @BeforeClass is run
	 * Method is run only when method hasToRun() returns true.
	 *
	 * @param config Config ID
	 * @param testClass Test class
	 */
	public void runBeforeTestClass(String config, TestClass testClass);
	
	/**
	 * Contains action proceeded prior test @Before is run
	 * Method is run only when method hasToRun() returns true.
	 *
	 * @param config Config ID
	 * @param target Target object
	 * @param method Test method
	 */
	public void runBeforeTest(String config, Object target, FrameworkMethod method);
	
	/**
	 * Returns true when method runBeforeTest() has to run
	 * Could be use to run method runBeforeTest() only once.
	 *
	 * @return true, if successful
	 */
	public boolean hasToRun();
}
