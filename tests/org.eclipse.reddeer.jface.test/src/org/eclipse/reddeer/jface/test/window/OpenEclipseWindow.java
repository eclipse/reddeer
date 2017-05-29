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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

public class OpenEclipseWindow extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		EclipseWindow ew = new EclipseWindow(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		ew.setBlockOnOpen(true);
		ew.open();
		return ew;
	}

}
