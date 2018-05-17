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
package org.eclipse.reddeer.gef.finder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;

/**
 * 
 * @author apodhrad
 *
 */
public class PaletteEntryFinder extends Finder<PaletteEntry> {

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.gef.finder.Finder#getChildren(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PaletteEntry> getChildren(PaletteEntry child) {
		List<PaletteEntry> list = new ArrayList<PaletteEntry>();
		if (child instanceof PaletteContainer) {
			return ((PaletteContainer) child).getChildren();
		}
		return list;
	}

}
