/*******************************************************************************
 * Copyright (c) 2017-2020 Red Hat, Inc and others.
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

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.reddeer.eclipse.test.Activator;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IRuntimeType;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.IServerType;
import org.eclipse.wst.server.core.model.ModuleDelegate;
import org.eclipse.wst.server.core.model.ServerDelegate;

public class TestServer extends ServerDelegate {

	public static final String TYPE = "Basic";
	
	public static final String NAME = "Test server";
	
	@Override
	public IStatus canModifyModules(IModule[] arg0, IModule[] arg1) {
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Test server message");
	}

	@Override
	public IModule[] getChildModules(IModule[] module) {
		return ServerModelUtilities.getChildModules(module);
	}

	@Override
	public IModule[] getRootModules(IModule module) throws CoreException {
		IStatus status = canModifyModules(new IModule[] { module }, null);
		if (status != null && !status.isOK())
			throw new CoreException(status);
		IModule[] parents = ServerModelUtilities.getParentModules(getServer(), module);
		if (parents.length > 0)
			return parents;
		return new IModule[] { module };
	}

	@Override
	public void modifyModules(IModule[] arg0, IModule[] arg1,
			IProgressMonitor arg2) throws CoreException {
	}
}

/**
 * Class contains methods needed for working with the multimodular project
 * 
 * Content of the class has been copied and minimized from jbosstools-server repository
 * from org.jboss.ide.eclipse.as.wtp.core.util.ServerModelUtilities class
 */
class ServerModelUtilities {

	public static IModule[] getChildModules(IModule[] module) {
		int last = module.length - 1;
		if (module[last] != null && module[last].getModuleType() != null)
			return getChildModules(module[last]);
		return new IModule[0];
	}

	public static IModule[] getChildModules(IModule module) {
		try {
			ModuleDelegate md = ((ModuleDelegate) module.loadAdapter(ModuleDelegate.class, new NullProgressMonitor()));
			IModule[] children = md == null ? null : md.getChildModules();
			return children == null ? new IModule[0] : children;
		} catch (Exception e) {
			// Need to protect against broken module types, like libra. No need to log.
			return null;
		}
	}
	
	public static IModule[] getParentModules(IServer server, IModule module) {
		// get all supported modules
		IRuntimeType rtt = getRuntimeType(server);
		IModule[] supported = rtt == null ? new IModule[0]
				: org.eclipse.wst.server.core.ServerUtil.getModules(rtt.getModuleTypes());
		ArrayList<IModule> list = new ArrayList<IModule>();

		for (int i = 0; i < supported.length; i++) {
			IModule[] childs = ServerModelUtilities.getChildModules(supported[i]);
			if (childs != null) {
				for (int j = 0; j < childs.length; j++) {
					if (childs[j].equals(module))
						list.add(supported[i]);
				}
			}
		}
		return list.toArray(new IModule[list.size()]);
	}

	public static IRuntimeType getRuntimeType(IServer server) {
		return server == null ? null : getRuntimeType(server.getServerType());
	}

	public static IRuntimeType getRuntimeType(IServerType server) {
		return server == null ? null : server.getRuntimeType();
	}
}
