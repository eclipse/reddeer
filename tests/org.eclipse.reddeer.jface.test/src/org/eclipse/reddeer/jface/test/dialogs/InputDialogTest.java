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
package org.eclipse.reddeer.jface.test.dialogs;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.jface.dialogs.InputDialog;
import org.eclipse.reddeer.jface.test.dialogs.impl.TestingInputDialog;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Test;

/**
 *
 * @author jkopriva@redhat.com
 *
 */
public class InputDialogTest {

	private static TestingInputDialog inputDialog;
	private Shell s;

	@Test
	public void inputDialogMessagesTest() {
		openInputDialog();
		InputDialog dialog = new InputDialog(TestingInputDialog.TITLE);
		assertEquals(TestingInputDialog.TITLE, dialog.getTitleText());
		assertEquals(TestingInputDialog.INITIAL_TEXT, dialog.getInputText());
	}

	@Test
	public void inputDialogCancelTest() {
		openInputDialog();
		InputDialog dialog = new InputDialog(TestingInputDialog.TITLE);
		dialog.cancel();
	}

	@Test
	public void inputDialogOkTest() {
		openInputDialog();
		InputDialog dialog = new InputDialog(TestingInputDialog.TITLE);
		dialog.ok();
	}

	@Test
	public void inputDialogSetTextGetTextTest() {
		openInputDialog();
		InputDialog dialog = new InputDialog(TestingInputDialog.TITLE);
		dialog.setInputText("something");
		assertEquals("something", dialog.getInputText());
		dialog.cancel();
	}

	public void openInputDialog() {
		Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				inputDialog = new TestingInputDialog();
				inputDialog.create();
				inputDialog.open();

			}
		});

	}

	@After
	public void closeShell() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (s != null) {
					s.dispose();
				}
				if (inputDialog != null) {
					inputDialog.close();
				}
			}
		});
	}

}
