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
package org.jboss.reddeer.core.util;

/**
 * Result runnable should be implemented by any class whose instances are intended to be executed by a thread and
 * their run should return a result of execution.
 */
public interface ResultRunnable<T> {

	/**
	 * Run.
	 *
	 * @return the t
	 */
	T run();
}
