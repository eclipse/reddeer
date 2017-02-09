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
import org.jboss.reddeer.eclipse.core.resources.Resource;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;

/**
 * Represents property dialog that is open after right-click on a resource tree item and
 * select Properties context menu. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ResourcePropertyDialog extends PropertyDialog {

	private final Logger log = Logger.getLogger(ResourcePropertyDialog.class);
	
	private Resource resource;
	
	/**
	 * Instantiates a new explorer item property dialog.
	 *
	 * @param resource resource item to get its property dialog
	 */
	public ResourcePropertyDialog(Resource resource){
		this.resource = resource;
	}
	
	/**
	 * Opens "Properties" for the explorer item and selects the right property page from the Properties dialog. 
	 * 
	 */
	protected void openImpl(){
		log.info("Open Properties for explorer item '" + resource.getName() + "' by context menu");
		resource.select();
		new ContextMenu("Properties").select();;
	}
	
	protected String getResourceName() {
		return resource.getName();
	}
}
