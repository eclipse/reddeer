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
package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.swt.api.TreeItem;

/**
 * Represents an item in the explorer
 * 
 * @author Jan Richter
 *
 */
public class ExplorerItem extends AbstractExplorerItem{

	/**
	 * Creates {@link ExplorerItem}.
	 *
	 * @param treeItem the tree item
	 */
	public ExplorerItem(TreeItem treeItem) {
		super(treeItem);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.core.resources.AbstractExplorerItem#select()
	 */
	@Override
	public void select() {
		activateWrappingView();
		treeItem.select();
	}
}
