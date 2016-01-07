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
package org.jboss.reddeer.gef.impl.palette.internal;

import org.eclipse.gef.ui.palette.PaletteViewer;
import org.jboss.reddeer.gef.impl.palette.AbstractPalette;

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
