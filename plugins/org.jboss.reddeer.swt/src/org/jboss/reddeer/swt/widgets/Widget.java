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
package org.jboss.reddeer.swt.widgets;

public interface Widget {

	/**
	 * Returns SWT Widget enclosed by this widget
	 * 
	 * @return
	 */
	org.eclipse.swt.widgets.Widget getSWTWidget();

	/**
	 * Returns true when widget is enabled, false otherwise
	 * 
	 * @return
	 */
	boolean isEnabled();
	
	/**
	 * Returns true when widget is disposed, false otherwise
	 * 
	 * @return
	 */
	boolean isDisposed();

}
