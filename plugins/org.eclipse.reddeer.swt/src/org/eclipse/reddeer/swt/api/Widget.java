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
