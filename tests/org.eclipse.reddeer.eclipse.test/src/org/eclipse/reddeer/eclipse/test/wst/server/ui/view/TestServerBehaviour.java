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
import org.eclipse.debug.core.ILaunch;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.model.ServerBehaviourDelegate;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;

public class TestServerBehaviour extends ServerBehaviourDelegate {

	public TestServerBehaviour() {
		// TODO Auto-generated constructor stub
	}

	protected void setupLaunch(ILaunch launch, String launchMode, IProgressMonitor monitor) throws CoreException {

		setServerState(IServer.STATE_STARTING);
		AbstractWait.sleep(TimePeriod.SHORT);
		setServerRestartState(false);
		setServerState(IServer.STATE_STARTED);
		setMode(launchMode);
	}

	@Override
	public void stop(boolean arg0) {
		setServerState(IServer.STATE_STOPPING);
		AbstractWait.sleep(TimePeriod.SHORT);
		setServerState(IServer.STATE_STOPPED);
	}

	protected void publishServer(int kind, org.eclipse.core.runtime.IProgressMonitor monitor) throws org.eclipse.core.runtime.CoreException {
		setServerPublishState(kind);
		AbstractWait.sleep(TimePeriod.SHORT);
		setServerPublishState(IServer.PUBLISH_STATE_NONE);
	}
	
	@Override
	public boolean canRestartModule(IModule[] module){
		return true;
	}
	
	@Override
	public boolean canPublishModule(IModule[] module){
		return true;
	}
	
	@Override
	public void startModule(IModule[] module, IProgressMonitor monitor) throws CoreException {
		setModuleState(module, IServer.STATE_STARTING);
		AbstractWait.sleep(TimePeriod.SHORT);
		setModuleState(module, IServer.STATE_STARTED);
	}
	
	@Override
	public void restartModule(IModule[] module, IProgressMonitor monitor) throws CoreException {
		setModuleState(module, IServer.STATE_STARTING);
		AbstractWait.sleep(TimePeriod.SHORT);
		setModuleState(module, IServer.STATE_STARTED);
	}
	
	@Override
	public void stopModule(IModule[] module, IProgressMonitor monitor) throws CoreException {
		setModuleState(module, IServer.STATE_STOPPING);
		AbstractWait.sleep(TimePeriod.SHORT);
		setModuleState(module, IServer.STATE_STOPPED);
	}
	
	
}
