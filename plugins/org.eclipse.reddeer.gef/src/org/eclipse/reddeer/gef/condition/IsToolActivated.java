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
package org.eclipse.reddeer.gef.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.gef.api.Palette;

/**
 * Returns true if a given tool is active in the specified palette.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class IsToolActivated extends AbstractWaitCondition {

	private Palette palette;
	private String tool;

	/**
	 * Constructs a condition which is fulfilled if a given tool is active in the specified palette.
	 * 
	 * @param palette
	 *            Palette
	 * @param label
	 *            Tool label
	 */
	public IsToolActivated(Palette palette, String label) {
		this.palette = palette;
		this.tool = label;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		return palette.getActiveTool().equals(tool);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "tool with label '" + tool + "' is activated";
	}

}
