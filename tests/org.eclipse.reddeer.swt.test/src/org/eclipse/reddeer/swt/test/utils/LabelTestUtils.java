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
package org.eclipse.reddeer.swt.test.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LabelTestUtils {

	public static Label createLabel(Composite parent, String text){
		Label swtLabel= new Label(parent, SWT.LEFT);
		swtLabel.setText(text);
		swtLabel.setSize(100,30);
		return swtLabel;
	}

	public static Label createLabel(Composite parent, String text, Image image){
		Label swtLabel= createLabel(parent, text);
		swtLabel.setImage(image);
		return swtLabel;
	}
	
	public static CLabel createCLabel(Composite parent, String text){
		CLabel swtLabel= new CLabel(parent, SWT.LEFT);
		swtLabel.setText(text);
		swtLabel.setSize(100,30);
		return swtLabel;
	}
}
