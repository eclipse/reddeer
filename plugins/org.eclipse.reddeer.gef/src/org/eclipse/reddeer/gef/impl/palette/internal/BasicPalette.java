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
package org.eclipse.reddeer.gef.impl.palette.internal;

import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.reddeer.gef.impl.palette.AbstractPalette;

/**
 * Basic implementation for Palette
 * 
 * @author Andrej Podhradsky (andrej.podhradsky@gmail.com)
 *
 */
public class BasicPalette extends AbstractPalette {

	/**
	 * Instantiates a new basic palette.
	 *
	 * @param paletteViewer the palette viewer
	 */
	public BasicPalette(PaletteViewer paletteViewer) {
		super(paletteViewer);
	}

}
