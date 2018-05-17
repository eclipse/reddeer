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
package org.eclipse.reddeer.swt.test.handler;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionDelegate;

public class ViewActionWithId extends ActionDelegate implements IViewActionDelegate {

	

	private static boolean toggle = false;
	
	public void init(IViewPart view) {
	}

	@Override
	public void run(IAction action) {
		toggle=true;
	}
	
	public static boolean isToggled() {
		return toggle;
	}

}
