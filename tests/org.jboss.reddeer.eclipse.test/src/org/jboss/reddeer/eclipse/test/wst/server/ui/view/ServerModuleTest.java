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
package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.jboss.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServerModule;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Radoslav Rabara
 *
 */
public class ServerModuleTest extends ServersViewTestCase {

	private static final String SERVER = "Server ABC";

	private static Server server;

	@BeforeClass
	public static void createProjects(){
		importProjects();
		
		createServer(SERVER);
		server = getServersView().getServer(SERVER);
		server.start();
	}

	@AfterClass
	public static void removeProjects(){
		server.stop();
		new CleanWorkspaceRequirement().fulfill();
	}
	
	@After
	public void removeModules(){
		List<ServerModule> modules = server.getModules();
		for(ServerModule module: modules){
			module.remove();
		}
	}
	
	@Test
	public void stopServerModule(){
		ServerModule module = addServerModule();
		module.stop();
		assertTrue(module.getLabel().getState().equals(ServerState.STOPPED));
	}

	@Test
	public void removeServerModule() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage();
		page.addAll();
		dialog.finish();

		List<ServerModule> modules = server.getModules();
		assertThat(modules.size(), is(3));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_1));
		assertThat(modules.get(1).getLabel().getName(), is(PROJECT_2));
		assertThat(modules.get(2).getLabel().getName(), is(PROJECT_3));

		modules.get(0).remove();

		modules = server.getModules();
		assertThat(modules.size(), is(2));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_2));
		assertThat(modules.get(1).getLabel().getName(), is(PROJECT_3));
	}
	
	@Test
	public void startServerModule(){
		ServerModule module = addServerModule();
		module.start();
		assertTrue(module.getLabel().getState().equals(ServerState.STARTED));
	}
	
	@Test
	public void restartServerModule(){
		ServerModule module = addServerModule();
		if(!module.canRestart()){
			module.start();
		}
		assertTrue(module.canRestart());
		module.restart();
		assertTrue(module.getLabel().getState().equals(ServerState.STARTED));
	}
	
	private ServerModule addServerModule(){
		ModifyModulesDialog mmDialog = server.addAndRemoveModules();
		ModifyModulesPage mmPage = new ModifyModulesPage();
		mmPage.add(PROJECT_1);
		mmDialog.finish();
		return server.getModules().get(0);
	}
}
