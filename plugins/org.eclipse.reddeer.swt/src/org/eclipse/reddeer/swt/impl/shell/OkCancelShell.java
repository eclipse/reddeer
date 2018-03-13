/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.shell;

import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.Matcher;

/**
 * Represents a simple shell with "OK" and "Cancel" buttons.
 * 
 * @author lvalach
 *
 */
public class OkCancelShell extends DefaultShell {

	/**
	 * Instantiates a new OkCancelShell.
	 *
	 * @param text
	 *            shell text
	 */
	public OkCancelShell(String text) {
		super(text);
	}

	/**
	 * Instantiates a new OkCancelShell.
	 * 
	 * @param shell
	 *            shell with both "OK" and "Cancel" buttons
	 */
	public OkCancelShell(Shell shell) {
		super(shell);
	}

	/**
	 * 
	 * Creates a new OkCancelShell matching specified matcher. First found shell
	 * with specified matcher is created. Beware, there is no strict (deterministic)
	 * order of shells.
	 * 
	 * @param matchers
	 *            matchers to match title of a shell
	 */
	public OkCancelShell(Matcher<?>... matchers) {
		super(matchers);
	}

	/**
	 * Instantiates a new OkCancelShell.
	 */
	public OkCancelShell() {
		super();
	}

	/**
	 * Click "OK" button.
	 */
	public void ok() {
		new OkButton(this).click();
	}

	/**
	 * Click "Cancel" button.
	 */
	public void cancel() {
		new CancelButton(this).click();
	}

	/**
	 * Finds out whether a "OK" button is enabled.
	 * 
	 * @return true if button is enabled, false otherwise
	 */
	public boolean isOkEnabled() {
		return new OkButton(this).isEnabled();
	}

	/**
	 * Finds out whether a "Cancel" button is enabled.
	 * 
	 * @return true if button is enabled, false otherwise
	 */
	public boolean isCancelEnabled() {
		return new CancelButton(this).isEnabled();
	}
}
