/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.uiforms.test.ui.views;

import org.jboss.reddeer.workbench.impl.view.WorkbenchView;


public class UIFormView extends WorkbenchView {

	private static final String TEST_CATEGORY = "Red Deer Test UI Forms";
	
	private static final String VIEW_TEXT = "UI Form Test";
	
	public UIFormView() {
		super(TEST_CATEGORY, VIEW_TEXT);
	}
}
