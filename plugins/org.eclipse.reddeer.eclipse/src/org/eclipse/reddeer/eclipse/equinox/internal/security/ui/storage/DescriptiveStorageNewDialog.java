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
package org.eclipse.reddeer.eclipse.equinox.internal.security.ui.storage;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Secure storage set new password dialog.
 *
 * @author jnovak@redhat.com
 */
public class DescriptiveStorageNewDialog extends AbstractWindow {

	public static final String SHELL_TITLE = "Secure Storage Password";

	public DescriptiveStorageNewDialog() {
		super(SHELL_TITLE);
	}

	public void setNewPassword(String newPassword) {
		new LabeledText("Password:").setText(newPassword);
		new LabeledText("Confirm password:").setText(newPassword);
	}

	public void ok(){
		new OkButton().click();
		new WaitWhile(new ShellIsAvailable(SHELL_TITLE));
	}

	public void cancel(){
		new CancelButton().click();
		new WaitWhile(new ShellIsAvailable(SHELL_TITLE));
	}

	@Override
	public Openable getDefaultOpenAction() {
		return null;
	}

}
