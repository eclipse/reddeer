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
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.eclipse.core.resources.DefaultProject;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.DefaultServer;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServerModule;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServersViewException;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.editor.ServerEditor;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Lucia Jelinkova
 */
public class ServerTest extends ServersViewTestCase {

	private static final String SERVER_1 = "Server 1";

	private static final String SERVER_2 = "Server 2";

	private static Server server1;
	
	private static Server server2;

	@BeforeClass
	public static void createProjects(){
		importProjects();
	}

	@BeforeClass
	public static void createServers(){
		createServer(SERVER_1);
		server1 = getServersView().getServer(SERVER_1);
	}

	@AfterClass
	public static void removeProjects(){
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		for (DefaultProject project : explorer.getProjects()){
			DeleteUtils.forceProjectDeletion(project,true);
		}
	}

	@Override
	public void tearDown() {
		if (!ServerState.STOPPED.equals(server1.getLabel().getState())){
			server1.stop();			
		}
		
		if (!server1.getModules().isEmpty()){
			ModifyModulesDialog dialog = server1.addAndRemoveModules();
			ModifyModulesPage page = new ModifyModulesPage();
			page.removeAll();
			dialog.finish();
		}
		
		try {
			getServersView().getServer(SERVER_2).delete();
		} catch (EclipseLayerException e) {
			// ok, already deleted
		}
		try {
			new WaitUntil(new ShellIsAvailable(ModifyModulesDialog.DIALOG_TITLE), TimePeriod.NONE);
			new DefaultShell(ModifyModulesDialog.DIALOG_TITLE).close();
		} catch (WaitTimeoutExpiredException e){
			// ok, dialog is already closed
		}
		super.tearDown();
	}

	@Test(expected=ServersViewException.class)
	public void start(){
		// start stopped server
		server1.start();

		assertThat(server1.getLabel().getState(), is(ServerState.STARTED));

		// start started server
		server1.start();
	}

	@Test(expected=ServersViewException.class)
	public void debug(){
		// debug stopped server
		server1.debug();

		assertThat(server1.getLabel().getState(), is(ServerState.DEBUGGING));

		// debug started server
		server1.debug();
	}

	@Test(expected=ServersViewException.class)
	public void profile(){
		// profile stopped server
		server1.profile();

		assertThat(server1.getLabel().getState(), is(ServerState.PROFILING));

		// profile started server
		server1.profile();
	}

	@Test(expected=ServersViewException.class)
	public void restart(){
		// restart running server
		server1.debug();
		server1.restart();

		assertThat(server1.getLabel().getState(), is(ServerState.STARTED));

		// restart stopped server
		server1.stop();
		server1.restart();
	}

	@Test(expected=ServersViewException.class)
	public void restartInDebug(){
		// restart in debug running server
		server1.debug();
		server1.restartInDebug();

		assertThat(server1.getLabel().getState(), is(ServerState.DEBUGGING));

		// restart in debug stopped server
		server1.stop();
		server1.restartInDebug();
	}

	@Test(expected=ServersViewException.class)
	public void restartInProfile(){
		// restart in profile running server
		server1.profile();
		server1.restartInProfile();

		assertThat(server1.getLabel().getState(), is(ServerState.PROFILING));

		// restart in profile stopped server
		server1.stop();
		server1.restartInProfile();
	}

	@Test(expected=ServersViewException.class)
	public void stop(){
		// stop running server
		server1.start();
		server1.stop();

		assertThat(server1.getLabel().getState(), is(ServerState.STOPPED));

		// stop stopped server
		server1.stop();
		server1.stop();
	}

	@Test
	public void publish(){
		server1.publish();

		assertThat(server1.getLabel().getPublishState(), is(ServerPublishState.SYNCHRONIZED));
	}

	@Test
	public void clean(){
		server1.clean();

		assertThat(server1.getLabel().getPublishState(), is(ServerPublishState.SYNCHRONIZED));
	}

	@Test
	public void delete_runningServer(){
		createServer(SERVER_2);
		server2 = getServersView().getServer(SERVER_2);
		
		server2.start();
		server2.delete(false);

		List<DefaultServer> servers = getServersView().getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_1));
	}

	@Test
	public void delete_runningServerAndStop(){
		createServer(SERVER_2);
		server2 = getServersView().getServer(SERVER_2);
		
		server2.start();
		server2.delete(true);

		List<DefaultServer> servers = getServersView().getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_1));
	}

	@Test
	public void openServer(){
		ServerEditor editor = server1.open();

		assertEquals(server1.getLabel().getName(), editor.getTitle());;
	}

	@Test
	public void addAndRemoveModule(){
		ModifyModulesDialog mmd = server1.addAndRemoveModules();

		assertThat(ModifyModulesDialog.DIALOG_TITLE, is(new DefaultShell().getText()));
		mmd.cancel();
		
	}

	@Test
	public void getModules(){
		List<ServerModule> modules = server1.getModules();

		assertThat(modules.size(), is(0));
	}

	@Test
	public void getModule(){
		ModifyModulesDialog dialog = server1.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage();
		page.addAll();
		dialog.finish();

		ServerModule module = server1.getModule(PROJECT_2);
		ServerModule module2 = server1.getModule(new RegexMatcher(".*project-2"));

		assertThat(module.getLabel().getName(), is(PROJECT_2));
		assertThat(module2.getLabel().getName(), is(PROJECT_2));
	}

	@Test(expected=EclipseLayerException.class)
	public void getModule_nonExisting(){
		server1.getModule("ABC");
	}
}
