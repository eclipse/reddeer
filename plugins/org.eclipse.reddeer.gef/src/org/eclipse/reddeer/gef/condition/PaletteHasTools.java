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
package org.eclipse.reddeer.gef.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.gef.api.Palette;

/**
 * Returns true if a given palette has the specified number of tools or more.
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class PaletteHasTools extends AbstractWaitCondition {

	private Palette palette;
	private int numberOfTools;

	/**
	 * Contructs a condition which is fulfilled if a given palette has at least 1 tool.
	 * 
	 * @param palette
	 *            Palette
	 */
	public PaletteHasTools(Palette palette) {
		this(palette, 1);
	}

	/**
	 * Contructs a condition which is fulfilled if a given palette has at least the specified number of tools.
	 * 
	 * @param palette
	 *            Palette
	 * @param numberOfTools
	 *            Number of tools
	 */
	public PaletteHasTools(Palette palette, int numberOfTools) {
		this.palette = palette;
		this.numberOfTools = numberOfTools;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		return palette.getTools().size() >= numberOfTools;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "palette has " + numberOfTools + " tools";
	}

}
