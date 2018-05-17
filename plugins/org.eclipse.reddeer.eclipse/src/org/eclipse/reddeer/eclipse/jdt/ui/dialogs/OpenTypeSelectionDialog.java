/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.dialogs;

import org.eclipse.reddeer.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuOpenable;

/**
 * Represents {@value #DIALOG_TITLE} dialog.
 * 
 * @see org.eclipse.jdt.internal.ui.dialogs.OpenTypeSelectionDialog
 * 
 * @author lvalach
 *
 */
public class OpenTypeSelectionDialog extends FilteredItemsSelectionDialog {

	public static final String DIALOG_TITLE = "Open Type";
	public static final String[] MENU_PATH = new String[] { "Navigate", "Open Type..." };

	/**
	 * Instantiates new OpenTypeSelectionDialog. Implementations are responsible for
	 * making sure given shell is OpenTypeSelectionDialog.
	 * 
	 * @param shell
	 *            instance of OpenTypeSelectionDialog
	 */
	public OpenTypeSelectionDialog(Shell shell) {
		super(shell);
	}

	/**
	 * Instantiates new OpenTypeSelectionDialog. An {@value #DIALOG_TITLE} shell
	 * will be connected with this instance. If there is no such shell, the shell
	 * may be assigned additionally (see {@link AbstractWindow#activate()}).
	 */
	public OpenTypeSelectionDialog() {
		super();
		isOpen();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Openable getDefaultOpenAction() {
		return new WorkbenchMenuOpenable(DIALOG_TITLE, MENU_PATH);
	}
}
