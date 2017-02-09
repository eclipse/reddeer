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
 * API for scale manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface Scale extends Widget<org.eclipse.swt.widgets.Scale> {

	/**
	 * Returns minimum value of the scale.
	 * 
	 * @return minimum value of the scale.
	 */
	int getMinimum();

	/**
	 * Returns maximum value of the scale.
	 * 
	 * @return maximum value of the scale
	 */
	int getMaximum();

	/**
	 * Returns current value of the scale.
	 * 
	 * @return current value of the scale
	 */
	int getSelection();

	/**
	 * Sets current value of the scale.
	 * 
	 * @param value value of the scale to select
	 */
	void setSelection(int value);

	/**
	 * Sets focus on the scale.
	 */
	void setFocus();
}
