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
package org.jboss.reddeer.spy.view;

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
