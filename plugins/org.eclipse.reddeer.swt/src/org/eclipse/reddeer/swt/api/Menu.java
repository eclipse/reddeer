/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.api;

import org.hamcrest.Matcher;

/**
 * API for menu manipulation
 * @author rawagner
 *
 */
public interface Menu extends Widget<org.eclipse.swt.widgets.Menu>{
	
	/**
	 * Gets menu items of the menu
	 * @return menu items of the menu
	 */
	java.util.List<MenuItem> getItems();
	
	/**
	 * Finds menu item with given path
	 * @param path of menu item to find
	 * @return menu item
	 */
	MenuItem getItem(String... path);
	
	/**
	 * Finds menu item matching given matchers
	 * @param matchers 
	 * @return menu item
	 */
	@SuppressWarnings("unchecked")
	MenuItem getItem(Matcher<String>... matchers);

}
