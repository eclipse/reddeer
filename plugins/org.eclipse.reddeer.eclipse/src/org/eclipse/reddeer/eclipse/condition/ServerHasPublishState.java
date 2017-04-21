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
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;

/**
 * 
 * @author odockal, jkopriva@redhat.com
 *
 */

public class ServerHasPublishState extends AbstractWaitCondition {

	private ServerPublishState expectedPublishState;
	private ServerPublishState currentPublishState;
	private Server server;

	public ServerHasPublishState(Server server, ServerPublishState expectedState) {
		this.expectedPublishState = expectedState;
		this.server = server;
	}

	@Override
	public boolean test() {
		this.currentPublishState = server.getLabel().getPublishState();
		return expectedPublishState.equals(this.currentPublishState);
	}

	@Override
	public String description() {
		return "server's publish state is '" + expectedPublishState.getText() + "'";
	}
	
	@Override
	public String errorMessageUntil() {
		return "Server still has publish state '" + expectedPublishState.getText() + "'";
	}
	
	@Override
	public String errorMessageWhile() {
		return "server expected state was '" + expectedPublishState.getText() + "' but current state is '"
				+ currentPublishState.getText() + "'"; 
	}

}
