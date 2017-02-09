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
package org.jboss.reddeer.uiforms.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * Represents Hyperlink object
 * 
 * @author Lucia Jelinkova
 *
 */
public interface Hyperlink extends Widget<org.eclipse.ui.forms.widgets.Hyperlink> {

	/**
	 * Returns hyperlink's text .
	 *
	 * @return the text
	 */
	String getText();

	/**
	 * Cliks the hyperlink.
	 */
	void activate();
}
