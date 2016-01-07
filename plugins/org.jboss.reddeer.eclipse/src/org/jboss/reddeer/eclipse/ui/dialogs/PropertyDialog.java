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

import org.jboss.reddeer.jface.preference.PreferenceDialog;

/**
 * Property dialog implementation
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class PropertyDialog extends PreferenceDialog {

	/**
	 * Returns the name of the resource as will be shown on the dialog's title.
	 *
	 * @return the resource name
	 */
	protected abstract String getResourceName();
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.preference.PreferenceDialog#getTitle()
	 */
	public String getTitle(){
		return "Properties for " + getResourceName();
	}
}
