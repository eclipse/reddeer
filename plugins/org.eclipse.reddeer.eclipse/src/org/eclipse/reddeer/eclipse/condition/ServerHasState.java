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
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;

/**
 * 
 * @author odockal, jkopriva@redhat.com
 *
 */

public class ServerHasState extends AbstractWaitCondition {

	private ServerState expectedState;
	private ServerState currentState;
	private ServerState resultState;
	private Server server;

	public ServerHasState(Server server, ServerState expectedState) {
		this.expectedState = expectedState;
		this.server = server;
	}

	@Override
	public boolean test() {
		this.currentState = server.getLabel().getState();
		if ( expectedState.equals(this.currentState)) {
			this.resultState = currentState;
			return true;
		}
		return false;
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

	@SuppressWarnings("unchecked")
	@Override 
	public ServerState getResult() {
		return this.resultState;
	}
	
}
