/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServerModule;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;

/**
 * 
 * @author rawagner
 *
 */
public class ServerModuleHasPublishState extends AbstractWaitCondition{
	
	private ServerModule module;
	private ServerPublishState state;
	
	public ServerModuleHasPublishState(ServerModule module, ServerPublishState state) {
		this.module = module;
		this.state = state;
	}

	@Override
	public boolean test() {
		return state.equals(module.getLabel().getPublishState());
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

}
