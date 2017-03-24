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

import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * API for CTab folder manipulation.
 * 
 * @author Jiri Peterka
 *
 */
public interface CTabFolder extends Control<org.eclipse.swt.custom.CTabFolder>, ReferencedComposite{

	/**
	 * Returns selected {@link CTabItem} within the folder
	 * @return selected tab within the folder
	 */
	CTabItem getSelection();
	
	/**
	 * Gets tab item labels.
	 * 
	 * @return labels of the tab item
	 */
	String[] getTabItemLabels();
}
