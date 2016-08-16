/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.junit.execution;

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