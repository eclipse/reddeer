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

import org.eclipse.swt.graphics.Image;

/**
 * API for CLabel manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface CLabel extends Control<org.eclipse.swt.custom.CLabel> {
	
	/**
	 * Returns text of the CLabel.
	 * 
	 * @return text of the CLabel
	 */
	String getText();

	/**
	 * Returns the horizontal alignment. The alignment style (SWT.LEFT,
	 * SWT.CENTER or SWT.RIGHT) is returned. Alignment styles are located in
	 * org.eclipse.swt.SWT.
	 * 
	 * @return alignment of CLabel
	 */
	int getAlignment();

	/**
	 * Returns CLabel image.
	 * 
	 * @return Clabel image
	 */
	Image getImage();
	
	/**
	 * Returns CLabel tooltip text
	 * @return CLabel tooltip text
	 */
	String getToolTipText();
}
