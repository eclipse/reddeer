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
package org.eclipse.reddeer.spy.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class RedDeerSpyView extends ViewPart{

	private RedDeerSpy redDeerSpy;
	
	@Override
	public void createPartControl(Composite parent) {
		redDeerSpy = new RedDeerSpy(parent);
	}

	@Override
	public void setFocus() {
	}
}
