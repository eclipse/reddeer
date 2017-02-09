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
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;

/**
 * 
 * @author odockal, jkopriva@redhat.com
 *
 */

public class ServerHasState extends AbstractWaitCondition {

	private ServerState expectedState;
	private ServerState currentState;
	private Server server;

	public ServerHasState(Server server, ServerState expectedState) {
		this.expectedState = expectedState;
		this.server = server;
	}

	@Override
	public boolean test() {
		this.currentState = server.getLabel().getState();
		return expectedState.equals(this.currentState);
	}

	@Override
	public String description() {
		return "server's state is: " + expectedState.getText();
	}
	
	@Override
	public String errorMessageWhile() {
		return "Server still has state '" + currentState.getText() + "'"; 
	}
	
	@Override
	public String errorMessageUntil() {
		return "server state was '" + currentState.getText() + "'" + " but expected state is '"
				+ expectedState.getText() + "'"; 
	}

}
