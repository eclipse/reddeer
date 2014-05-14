package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.eclipse.wst.server.ui.editor.ServerEditor;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServerModule;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerPublishState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewEnums.ServerState;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServersViewException;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.jboss.reddeer.swt.exception.SWTLayerException;
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

	private Server server1;

	@BeforeClass
	public static void createProjects(){
		importProjects();
	}
	
	@AfterClass
	public static void removeProjects(){
		PackageExplorer explorer = new PackageExplorer();
		for (Project project : explorer.getProjects()){
			project.delete(true);
		}
	}
	
	@Override
	protected void setUp(){
		super.setUp();
		createServer(SERVER_1);
		createServer(SERVER_2);

		server1 = serversView.getServer(SERVER_1);
	}
	
	@Override
	protected void tearDown() {
		try {
			new DefaultShell(ModifyModulesDialog.DIALOG_TITLE).setFocus();;
			new ModifyModulesDialog().cancel();
		} catch (SWTLayerException e){
			// ok, dialog is already closed
		}
		super.tearDown();
	}

	@Test
	public void start_stoppedServer(){
		server1.start();

		assertThat(server1.getLabel().getState(), is(ServerState.STARTED));
	}

	@Test(expected=ServersViewException.class)
	public void start_runningServer(){
		server1.start();
		server1.start();
	}

	@Test
	public void debug_stoppedServer(){
		server1.debug();

		assertThat(server1.getLabel().getState(), is(ServerState.DEBUGGING));
	}

	@Test(expected=ServersViewException.class)
	public void debug_runningServer(){
		server1.start();
		server1.debug();
	}

	@Test
	public void profile_stoppedServer(){
		server1.profile();

		assertThat(server1.getLabel().getState(), is(ServerState.PROFILING));
	}

	@Test(expected=ServersViewException.class)
	public void profile_runningServer(){
		server1.start();
		server1.profile();
	}

	@Test(expected=ServersViewException.class)
	public void restart_stoppedServer(){
		server1.restart();
	}

	@Test
	public void restart_runningServer(){
		server1.debug();
		server1.restart();

		assertThat(server1.getLabel().getState(), is(ServerState.STARTED));
	}

	@Test(expected=ServersViewException.class)
	public void restartInDebug_stoppedServer(){
		server1.restartInDebug();
	}

	@Test
	public void restartInDebug_runningServer(){
		server1.debug();
		server1.restartInDebug();

		assertThat(server1.getLabel().getState(), is(ServerState.DEBUGGING));
	}

	@Test(expected=ServersViewException.class)
	public void restartInProfile_stoppedServer(){
		server1.restartInProfile();
	}

	@Test
	public void restartInProfile_runningServer(){
		server1.profile();
		server1.restartInProfile();

		assertThat(server1.getLabel().getState(), is(ServerState.PROFILING));
	}

	@Test(expected=ServersViewException.class)
	public void stop_stoppedServer(){
		server1.stop();
	}

	@Test
	public void stop_runningServer(){
		server1.start();
		server1.stop();

		assertThat(server1.getLabel().getState(), is(ServerState.STOPPED));
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
		server1.start();
		server1.delete(false);

		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}

	@Test
	public void delete_runningServerAndStop(){
		server1.start();
		server1.delete(true);

		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}

	@Test
	public void delete_stoppedServer(){
		server1.delete(false);

		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}

	@Test
	public void delete_stoppedServerAndStop(){
		server1.delete(true);

		List<Server> servers = serversView.getServers();
		assertThat(servers.size(), is(1));
		assertThat(servers.get(0).getLabel().getName(), is(SERVER_2));
	}

	@Test
	public void openServer(){
		ServerEditor editor = server1.open();

		assertEquals(server1.getLabel().getName(), editor.getTitle());;
	}

	@Test
	public void addAndRemoveModule(){
		server1.addAndRemoveModules();

		assertThat(ModifyModulesDialog.DIALOG_TITLE, is(new DefaultShell().getText()));
	}

	@Test
	public void getModules(){
		ModifyModulesDialog dialog = server1.addAndRemoveModules();
		ModifyModulesPage page = dialog.getFirstPage();
		page.add("server-project-2", "server-project-3");
		dialog.finish();

		server1.start();
		
		List<ServerModule> modules = server1.getModules();

		assertThat(modules.size(), is(2));
		assertThat(modules.get(0).getLabel().getName(), is(PROJECT_2));
		assertThat(modules.get(1).getLabel().getName(), is(PROJECT_3));
	}

	@Test
	public void getAvailableModules() {
		ModifyModulesDialog dialog = server1.addAndRemoveModules();
		ModifyModulesPage page = dialog.getFirstPage();
		
		List<String> availableModules = page.getAvailableModules();
		
		dialog.cancel();
		
		assertThat(availableModules.size(), is(3));
		
		assertThat(availableModules.get(0), is(PROJECT_1));
		assertThat(availableModules.get(1), is(PROJECT_2));
		assertThat(availableModules.get(2), is(PROJECT_3));
	}
	
	@Test
	public void getConfiguredModules() {
		ModifyModulesDialog dialog = server1.addAndRemoveModules();
		ModifyModulesPage page = dialog.getFirstPage();
		page.addAll();
		
		List<String> configuredModules = page.getConfiguredModules();
		
		dialog.cancel();
		
		assertThat(configuredModules.size(), is(3));
		
		assertThat(configuredModules.get(0), is(PROJECT_1));
		assertThat(configuredModules.get(1), is(PROJECT_2));
		assertThat(configuredModules.get(2), is(PROJECT_3));
	}
	
	@Test
	public void getModules_noModules(){
		List<ServerModule> modules = server1.getModules();

		assertThat(modules.size(), is(0));
	}

	@Test
	public void getModule(){
		ModifyModulesDialog dialog = server1.addAndRemoveModules();
		ModifyModulesPage page = dialog.getFirstPage();
		page.addAll();
		dialog.finish();

		ServerModule module = server1.getModule(PROJECT_2);

		assertThat(module.getLabel().getName(), is(PROJECT_2));
	}

	@Test(expected=EclipseLayerException.class)
	public void getModule_nonExisting(){
		ModifyModulesDialog dialog = server1.addAndRemoveModules();
		ModifyModulesPage page = dialog.getFirstPage();
		page.addAll();
		dialog.finish();

		server1.getModule("ABC");
	}
	
}
