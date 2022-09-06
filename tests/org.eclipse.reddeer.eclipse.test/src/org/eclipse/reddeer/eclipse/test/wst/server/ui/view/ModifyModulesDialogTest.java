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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.Server;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServerModule;
import org.eclipse.reddeer.eclipse.wst.server.ui.cnf.ServersViewEnums.ServerPublishState;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * 
 * @author Lucia Jelinkova
 *
 */
public class ModifyModulesDialogTest extends ServersViewTestCase{

	private static final String SERVER = "Server ABC";
	
	private static Server server;

	@BeforeClass
	public static void createProjects(){
		importProjects();
		
		createServer(SERVER);
		server = getServersView().getServer(SERVER);
	}
	
	@After
	public void removeModules() {
		List<ServerModule> modules = server.getModules();
		for(ServerModule module: modules){
			module.remove();
		}
	}
	
	
	@AfterClass
	public static void removeProjects(){
		PackageExplorerPart explorer = new PackageExplorerPart();
		explorer.open();
		for (DefaultProject project : explorer.getProjects()){
			DeleteUtils.forceProjectDeletion(project, true);
		}
	}
	
	@Test
	public void addAll_removeTwo(){
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.addAll();
		dialog.finish();
		List<String> expectedBefore = Arrays.asList(PROJECT_1, PROJECT_2, PROJECT_3);

		List<ServerModule> modules = server.getModules();
		assertThat(modules.size(), is(3));
		
		modules.stream().forEach(mod -> {
			assertTrue(expectedBefore.contains(mod.getLabel().getName()));
		});
		
		dialog = server.addAndRemoveModules();
		page = new ModifyModulesPage(dialog);
		page.remove(PROJECT_1, PROJECT_3);
		dialog.finish();
		
		modules = server.getModules();
		assertThat(modules.size(), is(1));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_2));
	}
	
	@Test
	public void addTwo_removeAll(){
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.add(PROJECT_1, PROJECT_3);
		dialog.finish();

		List<ServerModule> modules = server.getModules();
		assertThat(modules.size(), is(2));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_1));
		assertThat(modules.get(1).getLabel().getName(), is(PROJECT_3));
		
		dialog = server.addAndRemoveModules();
		page = new ModifyModulesPage(dialog);
		page.removeAll();
		dialog.finish();
		
		modules = server.getModules();
		assertThat(modules.size(), is(0));
	}
	
	@Test
	public void testRemoveConfiguredModules() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.add(PROJECT_1, PROJECT_3);
		if (!page.getConfiguredModules().isEmpty()) {
		   page.removeAll();
		}
		dialog.finish();
	}
	
	@Test
	public void getAvailableModules() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);

		List<String> availableModules = page.getAvailableModules();

		dialog.cancel();

		assertThat(availableModules.size(), is(3));

		assertThat(availableModules.get(0), is(PROJECT_1));
		assertThat(availableModules.get(1), is(PROJECT_2));
		assertThat(availableModules.get(2), is(PROJECT_3));
	}

	@Test
	public void getConfiguredModules() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.addAll();

		List<String> configuredModules = page.getConfiguredModules();

		dialog.cancel();

		assertThat(configuredModules.size(), is(3));

		assertThat(configuredModules.get(0), is(PROJECT_1));
		assertThat(configuredModules.get(1), is(PROJECT_2));
		assertThat(configuredModules.get(2), is(PROJECT_3));
	}
	
	@Test
	public void testSwitchingOffPublishingChanges() {
		ModifyModulesDialog dialog = server.addAndRemoveModules();
		ModifyModulesPage page = new ModifyModulesPage(dialog);
		page.add(PROJECT_1);
		page.togglePublishChanges(false);

		dialog.finish();

		List<ServerModule> configuredModules = server.getModules();
		
		assertThat(configuredModules.size(), is(1));

		assertThat(configuredModules.get(0).getLabel().getName(), is(PROJECT_1));
		assertThat(configuredModules.get(0).getLabel().getPublishState(), is(ServerPublishState.NONE));
	}
}
