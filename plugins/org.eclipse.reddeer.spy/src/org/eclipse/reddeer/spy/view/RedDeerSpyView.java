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
