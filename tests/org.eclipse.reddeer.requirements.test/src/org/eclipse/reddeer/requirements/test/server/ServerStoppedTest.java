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
@ApacheTomcatServer(state=ServerRequirementState.STOPPED)
public class ServerStoppedTest {
	
	@InjectRequirement
	protected ApacheTomcatServerRequirement requirement;
	
	
	@Test
	public void isServerStoppedTest(){
		
		ServersView2 sw = new ServersView2();
		sw.open();
		Server s = sw.getServer(requirement.getServerName());
		assertTrue(s.getLabel().getState().equals(ServerState.STOPPED));
	}

}
