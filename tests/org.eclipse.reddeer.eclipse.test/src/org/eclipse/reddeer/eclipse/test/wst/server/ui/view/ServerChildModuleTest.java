/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
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

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServerModule;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersView2;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author zcervink@redhat.com
 *
 */
public class ServerChildModuleTest extends ServersViewTestCase {

	private static Server server;
	private static final String SERVER_NAME = "Server ABC";

	@BeforeClass
	public static void createProjects() {
		importProjects(true);
		createServer(SERVER_NAME);
		server = getServersView().getServer(SERVER_NAME);
		server.start();
		deployMultimodularProjectToServer();
	}

	@AfterClass
	public static void removeProjects() {
		server.stop();
		new CleanWorkspaceRequirement().fulfill();
	}

	@Test
	public void testBasicServerSubmoduleMethods() {
		ServersView2 serversView = new ServersView2();
		Server server = serversView.getServer(SERVER_NAME);
		final List<ServerModule> modules = server.getModules();
		assertTrue("There should be 1 module deployed (the number of deployed modules is " + modules.size() + ").",
				modules.size() == 1);
		assertTrue(
				"The name of deployed module should be 'server-multimodular-project-ear(server-multimodular-project)', but is '"
						+ modules.get(0).getLabel().getName() + "'.",
				modules.get(0).getLabel().getName().equals("server-multimodular-project-ear(server-multimodular-project)"));

		ServerModule module = modules.get(0);
		List<ServerModule> childModules = module.getModules();
		assertTrue("There should be 2 submodules deployed (the number of deployed submodules is " + childModules.size()
				+ ").", childModules.size() == 2);
		assertTrue(
				"The name of first deployed submodule should be 'server-multimodular-project-ejb', but is '"
						+ childModules.get(0).getLabel().getName() + "'.",
				childModules.get(0).getLabel().getName().equals("server-multimodular-project-ejb"));
		assertTrue(
				"The name of second deployed submodule should be 'server-multimodular-project-jar', but is '"
						+ childModules.get(1).getLabel().getName() + "'.",
				childModules.get(1).getLabel().getName().equals("server-multimodular-project-jar"));

		assertTrue(
				"The name of first deployed submodule should be 'server-multimodular-project-ejb  [Synchronized]', but is '"
						+ childModules.get(0).getTreeItem().getText() + "'.",
				childModules.get(0).getTreeItem().getText().equals("server-multimodular-project-ejb  [Synchronized]"));
		assertTrue(
				"The name of second deployed submodule should be 'server-multimodular-project-jar  [Synchronized]', but is '"
						+ childModules.get(1).getTreeItem().getText() + "'.",
				childModules.get(1).getTreeItem().getText().equals("server-multimodular-project-jar  [Synchronized]"));
	}

	private static void deployMultimodularProjectToServer() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.addAll();
		dialog.finish();
		new WaitWhile(new JobIsRunning());
	}
}
