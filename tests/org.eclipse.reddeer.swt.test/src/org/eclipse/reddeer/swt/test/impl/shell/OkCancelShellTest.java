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
package org.eclipse.reddeer.swt.test.impl.shell;

import static org.junit.Assert.fail;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.OkCancelShell;
import org.eclipse.reddeer.swt.test.utils.ShellTestUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class OkCancelShellTest {

	protected static final String SHELL_TITLE = "Testing shell";

	private Button okButton;
	private Button cancelButton;
	private ButtonClickListener okListener;
	private ButtonClickListener cancelListener;

	@Before
	public void setUp() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = ShellTestUtils.createShell(SHELL_TITLE);

				okButton = new org.eclipse.swt.widgets.Button(shell, SWT.PUSH);
				okButton.setText("OK");
				okListener = new ButtonClickListener();
				okButton.addListener(SWT.Selection, okListener);

				cancelButton = new org.eclipse.swt.widgets.Button(shell, SWT.PUSH);
				cancelButton.setText("Cancel");
				cancelListener = new ButtonClickListener();
				cancelButton.addListener(SWT.Selection, cancelListener);
			}
		});
	}

	@Test
	public void okAndCancelButtonsTest() {
		OkCancelShell shell = new OkCancelShell(SHELL_TITLE);

		shell.ok();
		try {
			new WaitUntil(new ButtonHeardClickNotification(okListener));
		} catch (WaitTimeoutExpiredException e) {
			fail("OK button didn't registered click event.");
		}

		shell.cancel();
		try {
			new WaitUntil(new ButtonHeardClickNotification(cancelListener));
		} catch (WaitTimeoutExpiredException e) {
			fail("Cancel button didn't registered click event.");
		}
	}

	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				ShellTestUtils.closeShell(SHELL_TITLE);
			}
		});
	}

	public class ButtonClickListener implements Listener {

		private boolean heard = false;

		public void handleEvent(Event e) {
			switch (e.type) {
			case SWT.Selection:
				heard = true;
				break;
			}
		}

		public boolean isHeard() {
			return heard;
		}
	}

	private class ButtonHeardClickNotification extends AbstractWaitCondition {

		private ButtonClickListener listener;

		public ButtonHeardClickNotification(ButtonClickListener listener) {
			this.listener = listener;
		}

		@Override
		public boolean test() {
			return listener.isHeard();
		}

		@Override
		public String description() {
			return "button heard click notification";
		}
	}
}
