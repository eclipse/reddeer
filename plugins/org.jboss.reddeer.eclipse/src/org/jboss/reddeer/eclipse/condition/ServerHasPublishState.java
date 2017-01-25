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

/**
 * 
 * @author odockal
 * @contributor jkopriva@redhat.com
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

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "server's publish state is '" + expectedPublishState.getText() + "'";
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#errorMessageUntil()
	 */
	@Override
	public String errorMessageUntil() {
		return "Server still has publish state '" + expectedPublishState.getText() + "'";
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#errorMessageWhile()
	 */
	@Override
	public String errorMessageWhile() {
		return "server expected state was '" + expectedPublishState.getText() + "' but current state is '"
				+ currentPublishState.getText() + "'"; 
	}

}
