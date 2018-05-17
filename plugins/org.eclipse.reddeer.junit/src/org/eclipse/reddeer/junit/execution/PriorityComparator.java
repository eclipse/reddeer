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

import java.util.Comparator;

/**
 * Priority comparator for comparing prioritized objects.
 * Compared object with highest priority is first: [100,
 * 10, 0, -10, -100]
 * 
 * @author mlabuda@redhat.com
 * @since 1.2.0
 */
public class PriorityComparator implements Comparator<IExecutionPriority> {

	@Override
	public int compare(IExecutionPriority o1, IExecutionPriority o2) {
		return Long.compare(o2.getPriority(), o1.getPriority());
	}
}
