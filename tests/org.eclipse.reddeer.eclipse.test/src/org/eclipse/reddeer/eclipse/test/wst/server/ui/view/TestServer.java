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
package org.eclipse.reddeer.eclipse.test.wst.server.ui.view;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.model.ServerDelegate;
import org.eclipse.reddeer.eclipse.test.Activator;

public class TestServer extends ServerDelegate {

	public static final String TYPE = "Basic";
	
	public static final String NAME = "Test server";
	
	@Override
	public IStatus canModifyModules(IModule[] arg0, IModule[] arg1) {
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Test server message");
	}

	@Override
	public IModule[] getChildModules(IModule[] arg0) {
		return new IModule[0];
	}

	@Override
	public IModule[] getRootModules(IModule module) throws CoreException {
		return new IModule[] { module };
	}

	@Override
	public void modifyModules(IModule[] arg0, IModule[] arg1,
			IProgressMonitor arg2) throws CoreException {
	}
}
