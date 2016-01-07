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
package org.jboss.reddeer.workbench.test.ui.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class LabelView extends ViewPart {
	private Label label;

	public LabelView() {
		super();
	}

	public void setFocus() {
		label.setFocus();
	}

	public void createPartControl(Composite parent) {
		label = new Label(parent, 0);
		label.setText("Workbench Test View");
	}

}
