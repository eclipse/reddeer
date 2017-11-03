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
package org.eclipse.reddeer.swt.test.ui.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class SWTControlsView extends ViewPart {

	private Label label;
	private StyledText styledText;
	private Text text;
	
	@Override
	public void createPartControl(Composite composite) {
		RowLayout rl = new RowLayout();
		composite.setLayout(rl);
		
		label = new Label(composite, SWT.NONE);
		label.setText("Name:");
		label.setData("org.eclipse.reddeer.widget.key", "label1");
		text = new Text(composite, SWT.NONE);
		text.setText("Original text");
		text.setData("org.eclipse.reddeer.widget.key", "text1");
		styledText = new StyledText(composite, SWT.NONE);
		styledText.setText("Styled text");
		styledText.setData("org.eclipse.reddeer.widget.key", "styledText1");
	}

	@Override
	public void setFocus() {
		text.setFocus();
	}
	

}
