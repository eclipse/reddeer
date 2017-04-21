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
package org.eclipse.reddeer.junit.execution;

import org.junit.runners.model.FrameworkMethod;


/**
 * Used to decide whether a specific test method should run or not. 
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public interface TestMethodShouldRun {
	
	/**
	 * Method of conditional running of a test method(s). If condition is met, test method runs.
	 * 
	 * @param method framework method which contains conditional running annotation
	 * @return true if a specified test should run, false otherwise.
	 */
	public boolean shouldRun(FrameworkMethod method);
}
