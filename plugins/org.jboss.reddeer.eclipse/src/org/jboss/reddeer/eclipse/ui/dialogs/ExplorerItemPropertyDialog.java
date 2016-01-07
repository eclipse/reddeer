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
package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.core.resources.AbstractExplorerItem;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;

/**
 * Represents property dialog that is open after right-click on explorer item and
 * select Properties context menu. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ExplorerItemPropertyDialog extends PropertyDialog {

	private final Logger log = Logger.getLogger(ExplorerItemPropertyDialog.class);
	
	private AbstractExplorerItem item;
	
	/**
	 * Instantiates a new explorer item property dialog.
	 *
	 * @param item Explorer item to whom this dialog belongs
	 */
	public ExplorerItemPropertyDialog(AbstractExplorerItem item){
		this.item = item;
	}
	
	/**
	 * Opens "Properties" for the explorer item and selects the right property page from the Properties dialog. 
	 * <br />
	 */
	protected void openImpl(){
		log.info("Open Properties for explorer item '" + item.getName() + "' by context menu");
		item.select();
		new ContextMenu("Properties").select();;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.ui.dialogs.PropertyDialog#getResourceName()
	 */
	protected String getResourceName() {
		return item.getName();
	}
}
