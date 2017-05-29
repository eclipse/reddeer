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
package org.eclipse.reddeer.jface.test.window;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EclipseWindow extends Window{

	protected EclipseWindow(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createContents(Composite parent) {
		parent.getShell().setText("Eclipse Window");
		return super.createContents(parent);
	}

}
