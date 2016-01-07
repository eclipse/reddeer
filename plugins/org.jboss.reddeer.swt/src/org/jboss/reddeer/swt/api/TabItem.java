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
 * API for tab item manipulation.
 * 
 * @author apodhrad
 * 
 */
public interface TabItem extends Widget {

	/**
	 * Activates the tab item.
	 */
	void activate();

	/**
	 * Returns text of the tab item.
	 * 
	 * @return text of the tab item
	 */
	String getText();

	org.eclipse.swt.widgets.TabItem getSWTWidget();
}
