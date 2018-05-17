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
package org.eclipse.reddeer.common.util;

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
