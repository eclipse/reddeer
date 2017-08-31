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
package org.eclipse.reddeer.requirements.test.server;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersView2;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.server.ServerRequirementState;
import org.eclipse.reddeer.requirements.server.apache.tomcat.ApacheTomcatServerRequirement;
import org.eclipse.reddeer.requirements.server.apache.tomcat.ApacheTomcatServerRequirement.ApacheTomcatServer;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(RedDeerSuite.class)
@ApacheTomcatServer(state=ServerRequirementState.RUNNING)
public class ServerRunningTest {
	
	@InjectRequirement
	protected ApacheTomcatServerRequirement requirement;
	
	
	@Test
	public void isServerRunningTest(){
		
		ServersView2 sw = new ServersView2();
		sw.open();
		Server s = sw.getServer(requirement.getServerName());
		assertTrue(s.getLabel().getState().equals(ServerState.STARTED));
	}

}
