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
package org.eclipse.reddeer.uiforms.test.ui.views;

import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;


public class UIFormView extends WorkbenchView {

	private static final String TEST_CATEGORY = "RedDeer Test UI Forms";
	
	private static final String VIEW_TEXT = "UI Form Test";
	
	public UIFormView() {
		super(TEST_CATEGORY, VIEW_TEXT);
	}
}
