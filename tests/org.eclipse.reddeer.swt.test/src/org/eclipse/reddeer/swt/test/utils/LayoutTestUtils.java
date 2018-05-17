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
package org.eclipse.reddeer.swt.test.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class LayoutTestUtils {

	public static Composite createFlatFormComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		FormLayout layout = new FormLayout();
		composite.setLayout(layout);
		return composite;
	}

	public static FormData entryLayoutData() {
		return entryLayoutData(null);
	}

	public static FormData entryLayoutData(Control referenceControl) {
		FormData data = new FormData();
		data = new FormData();
		data.left = new FormAttachment(0, 100);
		if (referenceControl != null) {
			data.top = new FormAttachment(referenceControl, 10);
		}
		return data;
	}

	public static FormData labelLayoutData(Control referenceControl) {
		FormData data = new FormData();
		data.right = new FormAttachment(referenceControl, -10);
		data.top = new FormAttachment(referenceControl, 0, SWT.CENTER);
		return data;
	}

}
