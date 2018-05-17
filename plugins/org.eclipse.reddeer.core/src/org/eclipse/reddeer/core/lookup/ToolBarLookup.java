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
package org.eclipse.reddeer.core.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Tool bar lookup provides methods for looking up various tool bars. 
 * Methods should be executed in UI Thread.
 * 
 * @author Jiri Peterka
 * 
 */
public class ToolBarLookup {

	private static ToolBarLookup instance;

	private ToolBarLookup() { }
	
	/**
	 * Gets instance of ToolBarLookup.
	 * 
	 * @return ToolBarLookup instance
	 */
	public static ToolBarLookup getInstance() {
		if (instance == null) {
			instance = new ToolBarLookup();
		}
		return instance;
	}

	/**
	 * Gets first tool bar of currently active referenced composite (shell, view, etc.).
	 * 
	 * @return first tool bar of currently active referenced composite
	 */
	public ToolBar getToolBar() {
		return getToolBar(null, 0);
	}

	/**
	 * Gets tool bar with specified index within specified referenced composite.
	 * 
	 * @param rc referenced composite where to look for tool bar
	 * @param index index of tool bar
	 * @return tool bar within specified referenced composite with with specified index
	 */
	public ToolBar getToolBar(ReferencedComposite rc, int index) {
		return WidgetLookup.getInstance()
				.activeWidget(rc, ToolBar.class, index);
	}

	/**
	 * Gets all tool bars located within specified referenced composite.
	 * 
	 * @param rc referenced composite to search for tool bar
	 * @return list of tool bars contained in specified referenced composite, or empty list
	 * if there is no tool bar in specified referenced composite at all
	 */
	public List<ToolBar> getToolbars(ReferencedComposite rc) {
		List<ToolBar> list = new ArrayList<ToolBar>();
		boolean found = true;
		int index = 0;
		do {
			try {
				list.add(WidgetLookup.getInstance().activeWidget(rc,
						ToolBar.class, index));
				index++;
			} catch (CoreLayerException ex) {
				found = false;
			}
		} while (found);
		return list;
	}
}
