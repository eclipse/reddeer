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
package org.eclipse.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServerModule;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Radoslav Rabara, odockal@redhat.com
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
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.addAll();
		dialog.finish();
		List<String> expectedBefore = Arrays.asList(PROJECT_1, PROJECT_2, PROJECT_3);
		List<String> expectedAfter = Arrays.asList(PROJECT_1, PROJECT_3);

		List<ServerModule> modules = server.getModules();
		assertThat(modules.size(), is(3));
		modules.stream().forEach(mod -> {
			assertTrue(expectedBefore.contains(mod.getLabel().getName()));
		});

		ServerModule module = modules.stream().filter(item -> item.getLabel().getName().equals(PROJECT_2)).findFirst().orElse(null);
		assertNotNull(module);
		module.remove();

		modules = server.getModules();
		assertThat(modules.size(), is(2));
		modules.stream().forEach(mod -> {
			assertTrue(expectedAfter.contains(mod.getLabel().getName()));
		});
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
	
	@Test
	public void testGetServerModuleWithPublishState() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.addAll();
		page.togglePublishChanges(true);
		dialog.finish();

		ServerModule thirdModule = server.getModule(PROJECT_1);
		assertTrue(thirdModule.getLabel().getPublishState().equals(ServerPublishState.SYNCHRONIZED));
	}
	
	@Test
	public void testGetServerModuleWithServerState() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.add(PROJECT_2);
		page.togglePublishChanges(false);
		dialog.finish();

		server.getModules().get(0).start();
		ServerModule module = server.getModule(PROJECT_2);
		assertTrue(module.getLabel().getState().equals(ServerState.STARTED));
	}
	
	@Test
	public void testGetServerModuleWithNoState() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.addAll();
		page.togglePublishChanges(false);
		dialog.finish();

		ServerModule module = server.getModule(PROJECT_3);
		assertThat(module.getLabel().getPublishState(), is(ServerPublishState.NONE));
		assertThat(module.getLabel().getState(), is(ServerState.NONE));
	}
	
	@Test
	public void testGetServerModuleWithStates() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.add(PROJECT_3);
		page.togglePublishChanges(true);
		dialog.finish();

		server.getModules().get(0).start();
		ServerModule module = server.getModule(PROJECT_3);
		assertThat(module.getLabel().getPublishState(), is(ServerPublishState.SYNCHRONIZED));
		assertThat(module.getLabel().getState(), is(ServerState.STARTED));
	}
	
	private ServerModule addServerModule(){
		ModifyModulesDialog mmDialog = server.addAndRemoveModules();
		ModifyModulesPage mmPage = new ModifyModulesPage(mmDialog);
		mmPage.add(PROJECT_1);
		mmDialog.finish();
		return server.getModules().get(0);
	}
}
