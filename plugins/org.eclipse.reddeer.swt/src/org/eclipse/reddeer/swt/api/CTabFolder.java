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

import org.eclipse.reddeer.core.reference.ReferencedComposite;

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
