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
package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;

public class ServerHasPublishState extends AbstractWaitCondition {

	private ServerPublishState expectedState;
	private Server server;

	public ServerHasPublishState(Server server, ServerPublishState expectedState) {
		this.expectedState = expectedState;
		this.server = server;
	}

	@Override
	public boolean test() {
		return expectedState.equals(server.getLabel().getPublishState());
	}

	@Override
	public String description() {
		return "server's publish state is " + expectedState.getText();
	}
	
	@Override
	public String errorMessage() {
		return "Server expected state was " + expectedState.getText() + " but current state is "
				+ server.getLabel().getPublishState().getText(); 
	}

}
