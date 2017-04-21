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
