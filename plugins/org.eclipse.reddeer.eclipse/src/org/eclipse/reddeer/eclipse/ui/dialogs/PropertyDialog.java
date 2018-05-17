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
