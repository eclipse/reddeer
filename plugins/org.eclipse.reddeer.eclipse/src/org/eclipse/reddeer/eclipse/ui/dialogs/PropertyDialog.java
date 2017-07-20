/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.dialogs;

import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuPreferencesDialog;

/**
 * Property dialog implementation
 * 
 * @author Lucia Jelinkova
 *
 */
public class PropertyDialog extends WorkbenchMenuPreferencesDialog {
	
	public PropertyDialog(String resourceName) {
		super("Properties for "+resourceName,"File","Properties");
	}
	
	@Override
	public Class<? extends org.eclipse.jface.window.Window> getEclipseClass() {
		return org.eclipse.ui.internal.dialogs.PropertyDialog.class;
	}
}
