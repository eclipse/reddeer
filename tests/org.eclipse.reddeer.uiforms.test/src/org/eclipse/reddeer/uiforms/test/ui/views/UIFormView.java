/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
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
