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
package org.eclipse.reddeer.eclipse.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServerModule;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;

/**
 * 
 * @author rawagner
 *
 */
public class ServerModuleHasState extends AbstractWaitCondition{
	
	private ServerModule module;
	private ServerState state;
	
	public ServerModuleHasState(ServerModule module, ServerState state) {
		this.module = module;
		this.state = state;
	}

	@Override
	public boolean test() {
		return state.equals(module.getLabel().getState());
	}
	
	@Override
	public String description() {
		return "expected server module state: '" + state.getText();
	}
	
	@Override
	public String errorMessageWhile() {
		return "Server module still has state " + state.getText(); 
	}
	
	@Override
	public String errorMessageUntil() {
		return "Server module expected state was " + state.getText() + " but current state is "
				+ module.getLabel().getState().getText(); 
	}

}
