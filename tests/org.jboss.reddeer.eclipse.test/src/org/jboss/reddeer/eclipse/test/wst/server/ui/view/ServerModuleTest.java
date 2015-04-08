package org.jboss.reddeer.eclipse.test.wst.server.ui.view;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.eclipse.wst.server.ui.view.Server;
import org.jboss.reddeer.eclipse.wst.server.ui.view.ServerModule;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.jboss.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesPage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Radoslav Rabara
 *
 */
public class ServerModuleTest extends ServersViewTestCase {

	private static final String SERVER = "Server ABC";

	private Server server;

	@BeforeClass
	public static void createProjects(){
		importProjects();
	}

	@AfterClass
	public static void removeProjects(){
		PackageExplorer explorer = new PackageExplorer();
		explorer.open();
		for (Project project : explorer.getProjects()){
			DeleteUtils.forceProjectDeletion(project, true);
		}
	}

	@Before
	public void setUp(){
		createServer(SERVER);

		server = serversView.getServer(SERVER);
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
}
