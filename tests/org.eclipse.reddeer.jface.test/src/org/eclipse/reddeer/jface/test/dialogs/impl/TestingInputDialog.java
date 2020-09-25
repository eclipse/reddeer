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
package org.eclipse.reddeer.jface.test.dialogs.impl;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 *
 * @author jkopriva@redhat.com
 *
 */
public class TestingInputDialog extends InputDialog {

	public static final String TEXT = "InputDialog info message";
	public static final String INITIAL_TEXT = "InputDialog initial text";
	public static final String TITLE = "InputDialog title";

	public TestingInputDialog() {
		super(null, TITLE, TEXT, INITIAL_TEXT, null);
	}

	@Override
	public void create() {
		super.create();
		getShell().setText(TITLE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		return area;
	}

}
