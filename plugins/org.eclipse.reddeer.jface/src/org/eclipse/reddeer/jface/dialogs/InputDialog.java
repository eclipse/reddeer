/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.reddeer.jface.dialogs;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * {@link org.eclipse.jface.dialogs.InputDialog}
 *
 * @author Radoslav Rabara, jkopriva@redhat.com
 *
 */
public class InputDialog extends DefaultShell {

	/**
	 * Represents the InputDialog.
	 */
	public InputDialog() {
		super();
	}

	/**
	 * Represents InputDialog with the specified <var>title</var>.
	 *
	 * @param title InputDialog title
	 */
	public InputDialog(String title) {
		super(title);
	}

	/**
	 * Click on the OK button.
	 */
	public void ok() {
		new OkButton().click();
	}

	/**
	 * Click on the cancel button.
	 */
	public void cancel() {
		new CancelButton().click();
	}

	/**
	 * Returns text from input text field.
	 *
	 * @return input text
	 */
	public String getInputText() {
		return new DefaultText().getText();
	}

	/**
	 * Returns text from input text field.
	 *
	 * @return input text
	 */
	public String getTitleText() {
		return Display.syncExec(() -> this.getText());
	}

	/**
	 * Sets the specified <var>text</var> into input text field.
	 *
	 * @param text text to be set
	 */
	public void setInputText(String text) {
		new DefaultText().setText(text);
	}

}
