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
package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for CLabel manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface CLabel extends Widget<org.eclipse.swt.custom.CLabel> {
	
	/**
	 * Returns text of the CLabel.
	 * 
	 * @return text of the CLabel
	 */
	String getText();

	/**
	 * Returns ToolTip text on the CLabel.
	 * 
	 * @return ToolTip text
	 */
	String getTooltipText();

	/**
	 * Returns the horizontal alignment. The alignment style (SWT.LEFT,
	 * SWT.CENTER or SWT.RIGHT) is returned. Alignment styles are located in
	 * org.eclipse.swt.SWT.
	 * 
	 * @return alignment of CLabel
	 */
	int getAlignment();

	/**
	 * Returns whether CLabel contains image or not.
	 * 
	 * @return true if CLabel contains image, false otherwise
	 */
	boolean hasImage();
}
