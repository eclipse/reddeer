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

/**
 * Interface for all RedDeer widgets.
 *
 */
public interface Widget<T extends org.eclipse.swt.widgets.Widget> {

	/**
	 * Gets an encapsulated SWT widget.
	 * 
	 * @return SWT widget encapsulated by this widget
	 */
	T getSWTWidget();
	
	/**
	 * Finds out whether a widget is disposed.
	 * 
	 * @return true if widget is disposed, false otherwise
	 */
	boolean isDisposed();

}
