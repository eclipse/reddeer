/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.junit.execution;

/**
 * Interface for prioritizing execution of methods.
 * 
 * @author mlabuda@redhat.com
 * @since 1.2.0
 */
public interface IExecutionPriority {

	/**
	 * Gets priority of an object. Higher value means that object
	 * e.g. method will be executed sooner than another with lower value. 
	 * 
	 * @return priority value of priority, higher means sooner execution
	 */
	long getPriority();	
}