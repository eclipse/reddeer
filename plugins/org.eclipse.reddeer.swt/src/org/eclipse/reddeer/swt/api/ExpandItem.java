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
package org.eclipse.reddeer.swt.api;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.common.wait.TimePeriod;

/**
 * API for expand bar item manipulation.
 * 
 * @author Vlado Pakan
 *
 */
public interface ExpandItem extends ReferencedComposite, Item<org.eclipse.swt.widgets.ExpandItem> {

	/**
	 * Expands the expand bar item.
	 */
	void expand();

	/**
	 * Expands expand bar item and wait for specific time period.
	 * 
	 * @param timePeriod time period to wait
	 */
	void expand(TimePeriod timePeriod);

	/**
	 * Collapses expand bar item.
	 */
	void collapse();

	/**
	 * Returns parent expand bar.
	 * 
	 * @return parent RedDeer expand bar
	 */
	ExpandBar getParent();

	/**
	 * Finds out whether expand bar item is collapsed or not.
	 * 
	 * @return true if expand bar item is collapsed, false otherwise
	 */
	boolean isExpanded();
}
