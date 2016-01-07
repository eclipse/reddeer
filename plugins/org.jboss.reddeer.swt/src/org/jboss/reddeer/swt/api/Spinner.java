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
 * API for spinner manipulation.
 * 
 * @author Andrej Podhradsky
 * 
 */
public interface Spinner extends Widget {

	/**
	 * Gets value of the spinner.
	 * 
	 * @return value of the spinner
	 */
	int getValue();

	/**
	 * Sets given value to the spinner.
	 * 
	 * @param value value of the spinner to set
	 */
	void setValue(int value);
	
	org.eclipse.swt.widgets.Spinner getSWTWidget();
}
