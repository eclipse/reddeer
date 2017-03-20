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

import org.jboss.reddeer.workbench.topmenu.TopMenuPreferencesDialog;

/**
 * Property dialog implementation
 * 
 * @author Lucia Jelinkova
 *
 */
public class PropertyDialog extends TopMenuPreferencesDialog {
	
	public PropertyDialog(String resourceName) {
		super("Properties for "+resourceName,"File","Properties");
	}
	
	@Override
	public Class<?> getEclipseClass() {
		return org.eclipse.ui.internal.dialogs.PropertyDialog.class;
	}
}
