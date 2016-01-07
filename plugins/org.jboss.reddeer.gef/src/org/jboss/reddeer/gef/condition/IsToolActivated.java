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
package org.jboss.reddeer.gef.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.gef.api.Palette;

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
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		return palette.getActiveTool().equals(tool);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "tool with label '" + tool + "' is activated";
	}

}
