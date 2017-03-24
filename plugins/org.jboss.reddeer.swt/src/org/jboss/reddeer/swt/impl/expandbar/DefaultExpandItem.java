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
package org.jboss.reddeer.swt.impl.expandbar;

import java.util.Iterator;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBar;
import org.jboss.reddeer.swt.api.ExpandItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default Expand Bar item implementation
 * 
 * @author Vlado Pakan
 * 
 */
public class DefaultExpandItem extends AbstractExpandItem {

	private static final Logger logger = Logger.getLogger(DefaultExpandItem.class);

	/**
	 * Default parameter-less constructor.
	 */
	public DefaultExpandItem() {
		this(0);
	}
	
	public DefaultExpandItem(org.eclipse.swt.widgets.ExpandItem widget){
		super(widget);
	}
	
	/**
	 * ExpandBarItem inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultExpandItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * Expand Bar item with specified text will be constructed.
	 *
	 * @param expandBarItemText the expand bar item text
	 */
	public DefaultExpandItem(String expandBarItemText) {
		this(0, expandBarItemText);
	}
	
	/**
	 * Expand Bar item with specified text inside given composite will be constructed.
	 *
	 * @param referencedComposite the referenced composite
	 * @param expandBarItemText the expand bar item text
	 */
	public DefaultExpandItem(ReferencedComposite referencedComposite, String expandBarItemText) {
		this(referencedComposite, 0, expandBarItemText);
	}

	/**
	 * Expand Bar item with specified Expand Bar index and text will be constructed.
	 *
	 * @param expandBarIndex the expand bar index
	 * @param expandBarItemText the expand bar item text
	 */
	public DefaultExpandItem(int expandBarIndex, String expandBarItemText) {
		super(DefaultExpandItem.findExpandBarItem(expandBarIndex, expandBarItemText));
	}
	
	/**
	 * Expand Bar item with specified Expand Bar index and text inside given composite will be constructed.
	 *
	 * @param referencedComposite the referenced composite
	 * @param expandBarIndex the expand bar index
	 * @param expandBarItemText the expand bar item text
	 */
	public DefaultExpandItem(ReferencedComposite referencedComposite, int expandBarIndex, String expandBarItemText) {
		super(DefaultExpandItem.findExpandBarItem(referencedComposite, expandBarIndex, expandBarItemText));
	}

	/**
	 * Expand Bar item with specified Expand Bar item index will be constructed.
	 *
	 * @param expandBarItemIndex the expand bar item index
	 */
	public DefaultExpandItem(int expandBarItemIndex) {
		this(0, expandBarItemIndex);
	}
	
	/**
	 * Expand Bar item with specified Expand Bar item index inside given composite will be constructed.
	 *
	 * @param referencedComposite the referenced composite
	 * @param expandBarItemIndex the expand bar item index
	 */
	public DefaultExpandItem(ReferencedComposite referencedComposite, int expandBarItemIndex) {
		this(referencedComposite, 0, expandBarItemIndex);
	}

	/**
	 * Expand Bar item with specified Expand Bar and Expand Bar item index will be constructed.
	 *
	 * @param expandBarIndex the expand bar index
	 * @param expandBarItemIndex the expand bar item index
	 */
	public DefaultExpandItem(int expandBarIndex, int expandBarItemIndex) {
		super(DefaultExpandItem.findExpandBarItem(expandBarIndex, expandBarItemIndex));
	}
	
	/**
	 * Expand Bar item with specified Expand Bar and Expand Bar item index inside given composite will be constructed.
	 *
	 * @param referencedComposite the referenced composite
	 * @param expandBarIndex the expand bar index
	 * @param expandBarItemIndex the expand bar item index
	 */
	public DefaultExpandItem(ReferencedComposite referencedComposite, int expandBarIndex, int expandBarItemIndex) {
		super(DefaultExpandItem.findExpandBarItem(referencedComposite, expandBarIndex, expandBarItemIndex));
	}
	
	/**
	 * Expand Bar item with specified Expand Bar index and Expand Bar item index will be
	 * constructed
	 * 
	 * @param expandBarIndex
	 * @param expandBarItemIndex
	 */
	private static org.eclipse.swt.widgets.ExpandItem findExpandBarItem(int expandBarIndex,
			int expandBarItemIndex) {
		logger.debug("Searching for Expand Bar item:"
			+ "\n  Expand Bar index: " + expandBarIndex
			+ "\n  Expand Bar Item index: " + expandBarItemIndex);
		ExpandBar expandBar = new DefaultExpandBar(expandBarIndex);
		List<ExpandItem> items = expandBar.getItems();
		if (items.size() < expandBarItemIndex + 1) {
			throw new SWTLayerException("No matching Expand Bar Item found");
		} else {
			return (org.eclipse.swt.widgets.ExpandItem)items.get(expandBarItemIndex).getSWTWidget();
		}
	}
	
	/**
	 * Expand Bar item with specified Expand Bar index and Expand Bar item index inside given composite will be
	 * constructed
	 * @param referencedComposite
	 * @param expandBarIndex
	 * @param expandBarItemIndex
	 */
	private static org.eclipse.swt.widgets.ExpandItem findExpandBarItem(ReferencedComposite referencedComposite, int expandBarIndex,
			int expandBarItemIndex) {
		logger.debug("Searching for Expand Bar item:"
			+ "\n  Expand Bar index: " + expandBarIndex
			+ "\n  Expand Bar Item index: " + expandBarItemIndex);
		ExpandBar expandBar = new DefaultExpandBar(referencedComposite, expandBarIndex);
		List<ExpandItem> items = expandBar.getItems();
		if (items.size() < expandBarItemIndex + 1) {
			throw new SWTLayerException("No matching Expand Bar Item found");
		} else {
			return (org.eclipse.swt.widgets.ExpandItem)items.get(expandBarItemIndex).getSWTWidget();
		}
	}

	/**
	 * Returns Expand Bar item with specified Expand Bar index and Expand Bar item index
	 * 
	 * @param expandBarIndex
	 * @param expandBarItemText
	 */
	private static org.eclipse.swt.widgets.ExpandItem findExpandBarItem(int expandBarIndex,
			String expandBarItemText) {
		logger.debug("Searching for Expand Bar item:"
				+ "\n  Expand Bar index: " + expandBarIndex
				+ "\n  Expand Bar Item text: " + expandBarItemText);
		org.eclipse.swt.widgets.ExpandItem result = null;
		ExpandBar expandBar = new DefaultExpandBar(expandBarIndex);
		List<ExpandItem> items = expandBar.getItems();
		boolean isFound = false;
		Iterator<ExpandItem> itExpandBarItem = items.iterator();
		ExpandItem expandBarItem = null;
		while (itExpandBarItem.hasNext() && (!isFound)) {
			expandBarItem = itExpandBarItem.next();
			if (expandBarItem.getText().equals(expandBarItemText)) {
				isFound = true;
			}
		}
		if (isFound) {
			result = (org.eclipse.swt.widgets.ExpandItem)expandBarItem.getSWTWidget();
		}
		else {
			throw new SWTLayerException("No matching Expand Bar item found");
		}
		return result;
	}
	
	/**
	 * Returns Expand Bar item with specified Expand Bar index inside given composite and Expand Bar item index
	 * @param referencedComposite
	 * @param expandBarIndex
	 * @param expandBarItemText
	 */
	private static org.eclipse.swt.widgets.ExpandItem findExpandBarItem(ReferencedComposite referencedComposite, int expandBarIndex,
			String expandBarItemText) {
		logger.debug("Searching for Expand Bar item:"
				+ "\n  Expand Bar index: " + expandBarIndex
				+ "\n  Expand Bar Item text: " + expandBarItemText);
		org.eclipse.swt.widgets.ExpandItem result = null;
		ExpandBar expandBar = new DefaultExpandBar(referencedComposite, expandBarIndex);
		List<ExpandItem> items = expandBar.getItems();
		boolean isFound = false;
		Iterator<ExpandItem> itExpandBarItem = items.iterator();
		ExpandItem expandBarItem = null;
		while (itExpandBarItem.hasNext() && (!isFound)) {
			expandBarItem = itExpandBarItem.next();
			if (expandBarItem.getText().equals(expandBarItemText)) {
				isFound = true;
			}
		}
		if (isFound) {
			result = (org.eclipse.swt.widgets.ExpandItem)expandBarItem.getSWTWidget();
		}
		else {
			throw new SWTLayerException("No matching Expand Bar item found");
		}
		return result;
	}

}
