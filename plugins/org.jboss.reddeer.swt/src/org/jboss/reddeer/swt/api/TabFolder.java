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

import java.util.List;

/**
 * API for tab folder manipulation.
 * 
 * @author Jiri Peterka
 * @author Andrej Podhradsky
 *
 */
public interface TabFolder extends Control<org.eclipse.swt.widgets.TabFolder> {

	/**
	 * Gets tab item labels.
	 * 
	 * @return labels of the tab item
	 */
	String[] getTabItemLabels();
	
	/**
	 * Gets selected tab items
	 * @return selected tab items
	 */
	List<TabItem> getSelection();
	
	/**
	 * Gets tab items of tab folder
	 * @return tab items of tab folder
	 */
	List<TabItem> getItems();
}
