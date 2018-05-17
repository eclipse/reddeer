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
package org.eclipse.reddeer.eclipse.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServerModule;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;

/**
 * 
 * @author rawagner
 *
 */
public class ServerModuleHasPublishState extends AbstractWaitCondition{
	
	private ServerModule module;
	private ServerPublishState state;
	private ServerPublishState resultPublishState;
	
	public ServerModuleHasPublishState(ServerModule module, ServerPublishState state) {
		this.module = module;
		this.state = state;
	}

	@Override
	public boolean test() {
		if (state.equals(module.getLabel().getPublishState())) {
			this.resultPublishState = state;
			return true;
		}
		return false;
	}
	
	@Override
	public String description() {
		return "expected server module publish state: '" + state.getText();
	}
	
	@Override
	public String errorMessageWhile() {
		return "Server module still has publish state " + state.getText(); 
	}
	
	@Override
	public String errorMessageUntil() {
		return "Server module expected publis state was " + state.getText() + " but current publis state is "
				+ module.getLabel().getState().getText(); 
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public ServerPublishState getResult() {
		return this.resultPublishState;
	}

}
